CREATE DATABASE Telecom_Team_1;

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

-- Trigger to calculate remaining_balance and extra_amount
--CREATE TRIGGER trg_Process_Payment_Update ON Process_Payment
--AFTER INSERT, UPDATE
--AS
--BEGIN
    --UPDATE pp
    --SET 
        --pp.remaining_balance = CASE 
            --WHEN p.amount < sp.price THEN sp.price - p.amount 
--        END,
        --pp.extra_amount = CASE 
          --  WHEN p.amount > sp.price THEN p.amount - sp.price 
        --END
    --FROM 
        --Process_Payment pp
    --JOIN 
        --Payment p ON pp.paymentID = p.paymentID
    --JOIN 
      --  Service_Plan sp ON pp.planID = sp.planID
    --WHERE 
      --  pp.paymentID IN (SELECT paymentID FROM inserted);
--END;
CREATE VIEW Process_Payment_View AS
SELECT 
    pp.paymentID,
    pp.planID,
    CASE 
        WHEN p.amount < sp.price THEN sp.price - p.amount 
        ELSE 0 
    END AS remaining_balance,
    CASE 
        WHEN p.amount > sp.price THEN p.amount - sp.price 
        ELSE 0 
    END AS extra_amount
FROM 
    Process_Payment pp
JOIN 
    Payment p ON pp.paymentID = p.paymentID
JOIN 
    Service_Plan sp ON pp.planID = sp.planID;

GO
