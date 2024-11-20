CREATE PROCEDURE Account_Plan AS
BEGIN
	SELECT C.*, SP.*
	FROM Customer_Account C, Service_Plan SP, Subscription S
	WHERE C.mobileNo = S.mobileNo and SP.planID = S.planID
END;

GO

CREATE FUNCTION Account_Plan_Date (@Subscription_Date DATE, @Plan_ID int)
RETURNS TABLE
AS
RETURN
(
    SELECT C.mobileNo, S.planID, SP.name
    FROM Customer_Account C, Subscription S, Service_Plan SP
    WHERE C.mobileNo = S.mobileNo and S.planID = SP.planID and S.planID = @Plan_ID and S.subscription_date = @Subscription_Date
);

GO

CREATE FUNCTION Account_Usage_Plan (@MobileNo char(11), @From_Date date)
RETURNS TABLE
AS
RETURN
(
    SELECT U.planID, U.data_consumption, U.minutes_used, U.SMS_sent
    FROM  Plan_Usage U
    WHERE U.mobileNo = @MobileNo and @From_Date >= U.start_date
);

GO

CREATE PROCEDURE Benefits_Account (@MobileNo char(11), @PlanID int) AS
BEGIN
    DELETE FROM Benefits
    WHERE Benefits.mobileNo IN (
                                SELECT C.mobileNo
                                FROM Customer_Account C, Benefits B, Plan_Provides_Benefits P
                                WHERE C.mobileNo = @MobileNo AND C.mobileNo = B.mobileNo and B.benefitID = P.benefitID and P.planID = @PlanID
                                )
    
END

GO

CREATE FUNCTION Account_SMS_Offers (@MobileNo char(11))
RETURNS TABLE
AS
RETURN
(
    SELECT E.*
    FROM  Exclusive_Offer E, Benefits B, Customer_Account C
    WHERE C.mobileNo = B.mobileNo and E.benefitID = B.benefitID and C.mobileNo = @MobileNo
);

GO

CREATE PROCEDURE Account_Payment_Points (@MobileNo char(11)) AS
BEGIN
    SELECT count(*) AS PaymentCount, sum(PG.pointsAmount) AS TotalPoints
    FROM Payment P, Points_Group PG
    WHERE P.mobileNo = @MobileNo and P.status = 'Successful' and P.date_of_payment > DATEADD(YEAR, -1, GETDATE()) and PG.PaymentID = P.paymentID

END

GO

CREATE FUNCTION Wallet_Cashback_Amount (@WalletID int, @PlanID int)
RETURNS FLOAT
AS
BEGIN
   DECLARE @Cashback INT;

    SELECT @Cashback = C.amount
    FROM Cashback C, Plan_Provides_Benefits P
    WHERE C.benefitID = P.benefitID and C.walletID = @WalletID and P.planID = @PlanID

    Return @Cashback

END;

GO

CREATE FUNCTION Wallet_Transfer_Amount (@WalletID int, @StartDate date, @EndDate date)
RETURNS FLOAT
AS
BEGIN
   DECLARE @AverageTransaction FLOAT;
   DECLARE @AverageWalletToWallet FLOAT;

    SELECT @AverageTransaction = AVG(P.amount)
    FROM Payment P, Wallet W
    WHERE W.walletID = @WalletID and P.mobileNo = W.mobileNo and P.date_of_payment BETWEEN @StartDate AND @EndDate

    SELECT @AverageWalletToWallet = AVG(T.amount)
    FROM Transfer_money T
    WHERE @WalletID = T.walletID1 and T.transfer_date BETWEEN @StartDate and @EndDate

    Return @AverageTransaction + @AverageWalletToWallet

END;


GO

CREATE FUNCTION Wallet_MobileNo (@MobileNo char(11))
RETURNS BIT
AS
BEGIN
    DECLARE @IsLinked BIT

    IF EXISTS (    SELECT C.mobileNo
                    FROM Customer_Account C, Wallet W
                    WHERE C.mobileNo = W.mobileNo and C.mobileNo = @MobileNo
   ) SET @IsLinked = '1'
   ELSE SET @IsLinked = '0'

Return @IsLinked

END

GO

CREATE PROCEDURE Total_Points_Account(@MobileNo char(11)) AS
BEGIN
    DECLARE @TotalPointsReceived int
    DECLARE @TotalPointsSpent int

    SELECT @TotalPointsReceived = SUM(PG.pointsAmount)
    FROM Customer_Account C, Payment P, Points_Group PG
    WHERE C.mobileNo = P.mobileNo and P.paymentID = PG.PaymentID and C.mobileNo = @MobileNo

    SELECT @TotalPointsSpent = SUM(V.points)
    FROM Customer_Account C, Voucher V
    WHERE C.mobileNo = V.mobileNo and V.redeem_date IS NOT NULL and C.mobileNo = @MobileNo

    UPDATE Customer_Account 
    SET point = @TotalPointsReceived - @TotalPointsSpent
    WHERE mobileNo = @MobileNo

END


