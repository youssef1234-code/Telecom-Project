--Exercise 2-1
CREATE DATABASE Telecom_Team_1;
GO
CREATE PROCEDURE createAllTables
AS
GO 
use Telecom_Team_1
CREATE TABLE Customer_profile(
	nationalID int,
	first_name varchar(50),
	last_name varchar(50),
	email varchar(50),
	address varchar(50),
	date_of_birth DATE,
	CONSTRAINT PK_Customer_profile PRIMARY KEY (nationalID)
);

use Telecom_Team_1
CREATE TABLE Customer_Account(
	 mobileNo char(11),
	 pass varchar(50),
	 balance decimal(10,1),
	 account_type varchar(50) CHECK (account_type IN ('Post Paid', 'Prepaid','Pay_as_you_go')),
	 start_date date,
	 status varchar(50) CHECK (status IN ('active', 'onhold')),
	 point int default 0,
	 nationalID int,
	 CONSTRAINT PK_Customer_Account PRIMARY KEY (mobileNo),
	 CONSTRAINT FK_Customer_Account_Customer_profile FOREIGN KEY (nationalID) REFERENCES Customer_profile(nationalID)
		ON UPDATE CASCADE
);

use Telecom_Team_1
CREATE TABLE Service_Plan (
	planID int primary key identity(1,1),
	SMS_offered int,
	minutes_offered int,
	data_offered int,
	name varchar(50),
	price int,
	description varchar(50)
);

use Telecom_Team_1
CREATE TABLE Subscription (
	mobileNo char(11),
	planID int,
	subscription_date date,
	status varchar(50)	CHECK (status IN ('active', 'onhold')),
	CONSTRAINT PK_Subscription PRIMARY KEY (mobileNo,planID),
	CONSTRAINT FK_Subscription_mobileNo FOREIGN KEY(mobileNo) REFERENCES Customer_Account(mobileNo) 
		ON UPDATE CASCADE,
	CONSTRAINT FK_Subscription_planID FOREIGN KEY(planID) REFERENCES Service_Plan(planID) 
		ON UPDATE CASCADE
);

use Telecom_Team_1
CREATE TABLE Plan_Usage(
	usageID int primary key identity(1,1),
	start_date date,
	end_date date,
	data_consumption int,
	minutes_used int,
	SMS_sent int,
	mobileNo char(11),
	planID int,
	CONSTRAINT FK_Plan_Usage_mobileNo FOREIGN KEY(mobileNo) REFERENCES Customer_Account(mobileNo) 
		ON UPDATE CASCADE,
	CONSTRAINT FK_Plan_Usage_planID FOREIGN KEY(planID) REFERENCES Service_Plan(planID) 
		ON UPDATE CASCADE,
);

use Telecom_Team_1
CREATE TABLE Payment(
	paymentID int primary key identity(1,1),
	amount decimal (10,1),
	date_of_payment date,
	payment_method varchar(50) CHECK (payment_method IN ('cash','credit')) ,
	status varchar(50) CHECK (status IN ('successful','pending','rejected')),
	mobileNo char(11),
	CONSTRAINT FK_Payment_mobileNo FOREIGN KEY(mobileNo) REFERENCES Customer_Account(mobileNo) 
		ON UPDATE CASCADE
);

use Telecom_Team_1
CREATE TABLE Process_Payment (
	paymentID int,
	planID int,
	remaining_balance decimal (10,1),
	extra_amount decimal (10,1),
	CONSTRAINT PK_Process_Payment PRIMARY KEY (paymentID),
	CONSTRAINT FK_Process_Payment_paymentID FOREIGN KEY(paymentID) REFERENCES Payment(paymentID)
		ON UPDATE CASCADE,
	CONSTRAINT FK_Process_Payment_planID FOREIGN KEY(planID) REFERENCES Service_Plan(planID)
		ON UPDATE CASCADE
);

use Telecom_Team_1
CREATE TABLE Wallet (
	walletID int primary key identity(1,1),
	current_balance decimal(10,2),
	currency Varchar(50),
	last_modified_date date,
	nationalID int,
	mobileNo char(11),
	CONSTRAINT FK_Wallet_planID FOREIGN KEY(nationalID) REFERENCES Customer_profile(nationalID)
		ON UPDATE CASCADE 
);

use Telecom_Team_1
CREATE TABLE Transfer_money (
	walletID1  int,
	walletID2 int,
	transfer_id int IDENTITY(1,1),
	amount decimal (10,2),
	transfer_date date,
	CONSTRAINT PK_Transfer_money PRIMARY KEY(walletID1,walletID2,transfer_id),
	CONSTRAINT FK_Transfer_money_walledID1 FOREIGN KEY(walletID1) REFERENCES Wallet(walletID)
		ON UPDATE CASCADE,
	CONSTRAINT FK_Transfer_money_walledID2 FOREIGN KEY(walletID2) REFERENCES Wallet(walletID)
);

use Telecom_Team_1
CREATE TABLE Benefits (
	benefitID int primary key identity(1,1),
	description Varchar(50),
	validity_date date,
	status Varchar(50) CHECK (status IN ('active','expired')),
	mobileNo char(11),
	CONSTRAINT FK_Benefits_mobileNo FOREIGN KEY(mobileNo) REFERENCES Customer_Account(mobileNo)
		ON UPDATE CASCADE
);

use Telecom_Team_1
CREATE TABLE Points_Group (
	pointID int identity(1,1),
	benefitID int,
	pointsAmount int,
	PaymentID int,
	CONSTRAINT PK_Points_Group PRIMARY KEY(pointID,benefitID),
	CONSTRAINT FK_Points_Group_benefitID FOREIGN KEY(benefitID) REFERENCES Benefits(benefitID)
		ON UPDATE CASCADE,
	CONSTRAINT FK_Points_Group_PaymentID FOREIGN KEY(PaymentID) REFERENCES Payment(PaymentID)
);

use Telecom_Team_1
CREATE TABLE Exclusive_Offer  (
	offerID int identity(1,1),
	benefitID int,
	internet_offered int,
	SMS_offered int,
	minutes_offered int,
	CONSTRAINT FK_Exclusive_Offer_benefitID FOREIGN KEY(benefitID) REFERENCES Benefits(benefitID)
		ON UPDATE CASCADE
);

use Telecom_Team_1
CREATE TABLE Cashback  (
	CashbackID int identity(1,1),
	benefitID int,
	walletID int,
	amount int,
	credit_date date,
	CONSTRAINT FK_Cashback_benefitID FOREIGN KEY(benefitID) REFERENCES Benefits(benefitID)
		ON UPDATE CASCADE,
	CONSTRAINT FK_Cashback_walletID FOREIGN KEY(walletID) REFERENCES Wallet(walletID)
);

use Telecom_Team_1
CREATE TABLE Plan_Provides_Benefits  (
	benefitID int,
	planID int,
	CONSTRAINT PK_Plan_Provides_Benefits PRIMARY KEY(benefitID,planID),
	CONSTRAINT FK_Plan_Provides_Benefits_benefitID FOREIGN KEY(benefitID) REFERENCES Benefits(benefitID)
		ON UPDATE CASCADE,
	CONSTRAINT FK_Plan_Provides_Benefits_planID FOREIGN KEY(planID) REFERENCES Service_Plan(planID)
		ON UPDATE CASCADE
);

use Telecom_Team_1
CREATE TABLE Shop (
	shopID int PRIMARY KEY IDENTITY(1,1),
	name varchar(50),
	category varchar(50)
);

use Telecom_Team_1
CREATE TABLE Physical_Shop (
	shopID int,
	address varchar(50),
	working_hours varchar(50),
	CONSTRAINT PK_Physical_Shop PRIMARY KEY(shopID),
	CONSTRAINT FK_Physical_Shop_shopID FOREIGN KEY(shopID) REFERENCES Shop(shopID)
		ON UPDATE CASCADE
);

use Telecom_Team_1
CREATE TABLE E_shop (
	shopID int,
	URL varchar(50),
	rating int,
	CONSTRAINT PK_E_shop PRIMARY KEY(shopID),
	CONSTRAINT FK_E_shop_shopID FOREIGN KEY(shopID) REFERENCES Shop(shopID)
		ON UPDATE CASCADE
);

use Telecom_Team_1
CREATE TABLE Voucher (
	voucherID  int PRIMARY KEY IDENTITY(1,1),
	value int,
	expiry_date date,
	points int,
	mobileNo char(11),
	shopID int,
	redeem_date date,
	CONSTRAINT FK_Voucher_shopID FOREIGN KEY(shopID) REFERENCES Shop(shopID)
		ON UPDATE CASCADE,
	CONSTRAINT FK_Voucher_mobileNo FOREIGN KEY(mobileNo) REFERENCES Customer_Account(mobileNo)
		ON UPDATE CASCADE
);


CREATE TABLE Technical_Support_Ticket (
	ticketID int identity(1,1),
	mobileNo char(11),
	Issue_description Varchar(50),
	priority_level int,
	status Varchar(50) CHECK (status IN ('Open','In Progress','Resolved')) ,
	CONSTRAINT PK_Technical_Support_Ticket PRIMARY KEY(ticketID,mobileNo),
 	CONSTRAINT FK_Technical_Support_Ticket_mobileNo FOREIGN KEY(mobileNo) REFERENCES Customer_Account(mobileNo)
		ON UPDATE CASCADE

);


GO

CREATE PROCEDURE DropAllTables
AS
BEGIN
    DROP TABLE IF EXISTS Technical_Support_Ticket;
    DROP TABLE IF EXISTS Voucher;
    DROP TABLE IF EXISTS E_shop;
    DROP TABLE IF EXISTS Physical_Shop;
    DROP TABLE IF EXISTS Shop;
    DROP TABLE IF EXISTS Plan_Provides_Benefits;
    DROP TABLE IF EXISTS Cashback;
    DROP TABLE IF EXISTS Exclusive_Offer;
    DROP TABLE IF EXISTS Points_Group;
    DROP TABLE IF EXISTS Benefits;
    DROP TABLE IF EXISTS Transfer_money;
    DROP TABLE IF EXISTS Wallet;
    DROP TABLE IF EXISTS Process_Payment;
    DROP TABLE IF EXISTS Payment;
    DROP TABLE IF EXISTS Plan_Usage;
    DROP TABLE IF EXISTS Subscription;
    DROP TABLE IF EXISTS Service_Plan;
    DROP TABLE IF EXISTS Customer_Account;
    DROP TABLE IF EXISTS Customer_profile;
END;

GO


CREATE PROCEDURE clearAllTables
AS
BEGIN
    -- Dropping tables with no dependencies on other tables first
    DELETE FROM Technical_Support_Ticket;
    DELETE FROM Voucher;
    DELETE FROM E_shop;
    DELETE FROM Physical_Shop;
    DELETE FROM Shop;
    DELETE FROM Plan_Provides_Benefits;
    DELETE FROM Cashback;
    DELETE FROM Exclusive_Offer;
    DELETE FROM Points_Group;
    DELETE FROM Benefits;
    DELETE FROM Transfer_money;
    DELETE FROM Wallet;
    DELETE FROM Process_Payment;
    DELETE FROM Payment;
    DELETE FROM Plan_Usage;
    DELETE FROM Subscription;
    DELETE FROM Service_Plan;
    DELETE FROM Customer_Account;
    DELETE FROM Customer_profile;
END;
GO

CREATE PROCEDURE dropAllProceduresFunctionsViews
AS
BEGIN
    DROP VIEW allCustomerAccounts;
	DROP VIEW allServicePlans;
	DROP VIEW allBenefits;
	DROP VIEW AccountPayments;
	DROP VIEW allShops;
	DROP VIEW allResolvedTickets;
	DROP VIEW CustomerWallet;
	DROP VIEW E_shopVouchers;
	DROP VIEW PhysicalStoreVouchers;
	DROP VIEW Num_of_cashback;
	DROP PROC Account_Plan;
	DROP FUNCTION Account_Plan_date;
	DROP FUNCTION Account_Usage_Plan;
	DROP PROCEDURE Benefits_Account;
	DROP FUNCTION Account_SMS_Offers;
	DROP PROCEDURE Account_Payment_Points;
	DROP FUNCTION Wallet_Cashback_Amount;
	DROP FUNCTION Wallet_Transfer_Amount;
	DROP FUNCTION Wallet_MobileNo;
	DROP PROCEDURE Total_Points_Account;
	DROP FUNCTION AccountLoginValidation;
	DROP FUNCTION Consumption;
	DROP PROCEDURE Unsubscribed_Plans;
	DROP FUNCTION Usage_Plan_CurrentMonth;
	DROP FUNCTION Cashback_Wallet_Customer;
	DROP PROCEDURE Ticket_Account_Customer;
	DROP PROCEDURE Account_Highest_Vouche;
	DROP FUNCTION Remaining_plan_amount;
	DROP FUNCTION Extra_plan_amount;
	DROP PROCEDURE Top_Successful_Payments;
	DROP FUNCTION Subscribed_plans_5_Months;
	DROP PROCEDURE Initiate_plan_payment;
	DROP PROCEDURE Payment_wallet_cashback;
	DROP PROCEDURE Initiate_balance_payment;
	DROP PROCEDURE Redeem_voucher_points;
END;
GO

drop VIEW allCustomerAccounts
--Exercise 2-2
use Telecom_Team_1
GO
CREATE View allCustomerAccounts as
SELECT CP.*, CA.mobileNo
From Customer_Profile CP JOIN Customer_Account CA ON CP.nationalID = CA.nationalID
WHERE CA.status = 'active';

GO
CREATE VIEW allServicePlans
	AS
	SELECT *
	FROM Service_Plan;
GO

CREATE VIEW allBenefits as
SELECT *
FROM Benefits
WHERE status = 'active';
GO

CREATE VIEW AccountPayments
	AS
	SELECT p.amount, p.date_of_payment,CA.mobileNo
	FROM Payment p, customer_account ca
	WHERE p.mobileNo = ca.mobileNo
GO

GO
CREATE VIEW allShops as
SELECT *
FROM Shop

GO 
CREATE VIEW allResolvedTickets
AS
	SELECT *
	FROM Technical_Support_Ticket
	WHERE status = 'Resolved'
GO

GO
CREATE VIEW CustomerWallet as
SELECT W.*, CP.first_name, CP.last_name
FROM Wallet W, Customer_profile CP
WHERE W.nationalID = CP.nationalID

Go
CREATE VIEW E_shopVouchers
AS
	SELECT e.URL, v.value, v.voucherID
	FROM E_shop e, Voucher v
	WHERE e.shopID = v.shopID AND v.redeem_date IS NOT NULL
GO

CREATE VIEW PhysicalStoreVouchers as
SELECT PS.*, V.voucherID, V.value
FROM Physical_Shop PS, Voucher V
where PS.shopID = V.shopID AND V.redeem_date is NOT NULL

GO

CREATE VIEW Num_of_cashback
AS
	SELECT w.walletID,Count(c.CAShbackID) AS Number_Of_Cashbacks
	FROM Wallet w, CAShback c
	WHERE w.walletID= c.walletID
	Group By w.walletID
GO

--exercise 2-3 
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
    WHERE C.mobileNo = B.mobileNo and E.benefitID = B.benefitID and C.mobileNo = @MobileNo AND E.sms>0
);

GO

CREATE PROCEDURE Account_Payment_Points (@MobileNo char(11)) AS
BEGIN
    SELECT count(*) AS  Total_Number_of_transactions, sum(PG.pointsAmount) AS Total_Amount_of_points
    FROM Payment P, Points_Group PG
    WHERE P.mobileNo = @MobileNo and P.status = 'Successful' and P.date_of_payment >= DATEADD(YEAR, -1, GETDATE()) and PG.PaymentID = P.paymentID

END

GO

CREATE FUNCTION Wallet_Cashback_Amount (@WalletID int, @PlanID int)
RETURNS INT
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
RETURNS decimal(10,2)
AS
BEGIN
  
   DECLARE @AverageWalletToWallet decimal(10,2);

    SELECT @AverageWalletToWallet = AVG(T.amount)
    FROM Transfer_money T
    WHERE @WalletID = T.walletID1 and T.transfer_date BETWEEN @StartDate and @EndDate

    Return @AverageWalletToWallet

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






