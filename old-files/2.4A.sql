use Telecom_Team_1
GO
CREATE FUNCTION AccountLoginValidation
(@MobileNo char(11),@Password varchar(50))
RETURNS BIT
AS
BEGIN
DECLARE @Success BIT
	if exists (SELECT * FROM Customer_Account c WHERE c.mobileNo = @MobileNo AND c.pass = @Password)
	Set @Success =1
	else set @Success =0;
RETURN @Success
END
GO
CREATE FUNCTION Unsubscribed_Plans
(@MobileNo char(11))
RETURNS TABLE
AS
RETURN 
(SELECT s.name
FROM Service_Plan s, Subscription sub
WHERE s.planID <> sub.PlanID AND sub.mobileNo = @MobileNo
)
GO
CREATE FUNCTION Cashback_Wallet_Customer
(@NationalID int)
RETURNS TABLE
AS
RETURN
(
SELECT c.*
FROM Cashback c, Wallet w
WHERE c.WalletID = w.WalletID AND w.NationalID = @NationalID
)
GO
CREATE FUNCTION Account_Highest_Voucher
(@MobileNo car(11))
RETURNS INT
AS 
BEGIN
DECLARE @Voucher_id INT
SELECT MAX(v.voucherID) as maximum
FROM Voucher v
WHERE v.mobileNo = @MobileNo
set @Voucher_id = maximum
RETURN @Voucher_id
END
GO
CREATE FUNCTION Subscribed_Plans_5_Months
(@MobileNo char(11))
RETURNS TABLE
AS
RETURN
(
SELECT s.*
FROM Service_Plan s, Subscription sub
WHERE s.planID = sub.planID AND sub.mobileNo = @MobileNo AND DATEDIFF(MONTH,sub.subscription_date,GETDATE()) < 5
)
GO