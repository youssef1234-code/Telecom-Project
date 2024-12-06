create database TELECOM_TEAM_MAIN collate SQL_Latin1_General_CP1_CI_AS
go


create table dbo.Service_plan
(
    planID          int identity
        primary key,
    name            varchar(50) not null,
    price           int         not null,
    SMS_offered     int         not null,
    minutes_offered int         not null,
    data_offered    int         not null,
    description     varchar(50)
)
go

create table dbo.customer_profile
(
    nationalID    int         not null
        primary key,
    first_name    varchar(50) not null,
    last_name     varchar(50) not null,
    email         varchar(50),
    address       varchar(70) not null,
    date_of_birth date
)
go

create table dbo.Wallet
(
    walletID           int identity
        primary key,
    current_balance    decimal(10, 2) default 0,
    currency           varchar(50)    default 'egp',
    last_modified_date date,
    nationalID         int
        references dbo.customer_profile,
    mobileNo           char(11)
)
go

create table dbo.customer_account
(
    mobileNo     char(11) not null
        primary key,
    pass         varchar(50),
    balance      decimal(10, 1),
    account_type varchar(50)
        check ([account_type] = 'postpaid' OR [account_type] = 'prepaid' OR [account_type] = 'pay-as-you-go'),
    start_date   date     not null,
    status       varchar(50)
        check ([status] = 'active' OR [status] = 'onhold'),
    points       int default 0,
    nationalID   int
        references dbo.customer_profile
)
go

create table dbo.Benefits
(
    benefitID     int identity
        primary key,
    description   varchar(50),
    validity_date date,
    status        varchar(50)
        check ([status] = 'active' OR [status] = 'expired'),
    mobileNo      char(11)
        references dbo.customer_account
)
go

create table dbo.Cashback
(
    cashbackID  int identity,
    benefitID   int not null
        references dbo.Benefits,
    walletID    int
        references dbo.Wallet,
    amount      int,
    credit_date date,
    constraint pk_Cashback
        primary key (cashbackID, benefitID)
)
go

create table dbo.Exclusive_offer
(
    offerID          int identity,
    benefitID        int not null
        references dbo.Benefits,
    internet_offered int,
    SMS_offered      int,
    minutes_offered  int,
    constraint pk_Exclusive_offer
        primary key (offerID, benefitID)
)
go

create table dbo.Payment
(
    paymentID       int identity
        primary key,
    amount          decimal(10, 1) not null,
    date_of_payment date           not null,
    payment_method  varchar(50)
        check ([payment_method] = 'cash' OR [payment_method] = 'credit'),
    status          varchar(50)
        check ([status] = 'successful' OR [status] = 'rejected' OR [status] = 'pending'),
    mobileNo        char(11)
        references dbo.customer_account
)
go

create table dbo.Plan_Usage
(
    usageID          int identity
        primary key,
    start_date       date not null,
    end_date         date not null,
    data_consumption int,
    minutes_used     int,
    SMS_sent         int,
    mobileNo         char(11)
        references dbo.customer_account,
    planID           int
        references dbo.Service_plan
)
go

create table dbo.Points_group
(
    pointId      int identity,
    benefitID    int not null
        references dbo.Benefits,
    pointsAmount int,
    paymentId    int
        references dbo.Payment,
    constraint pk_Points_group
        primary key (pointId, benefitID)
)
go

create table dbo.Subscription
(
    mobileNo          char(11) not null
        references dbo.customer_account,
    planID            int      not null
        references dbo.Service_plan,
    subscription_date date,
    status            varchar(50)
        check ([status] = 'active' OR [status] = 'onhold'),
    constraint pk_subscription
        primary key (mobileNo, planID)
)
go

create table dbo.Technical_support_ticket
(
    ticketID          int identity,
    mobileNo          char(11) not null
        references dbo.customer_account,
    issue_description varchar(50),
    priority_level    int,
    status            varchar(50)
        check ([status] = 'Resolved' OR [status] = 'In progress' OR [status] = 'Open'),
    constraint pk_Technical_support_ticket
        primary key (ticketID, mobileNo)
)
go

create table dbo.plan_provides_benefits
(
    benefitid int not null
        references dbo.Benefits,
    planID    int not null
        references dbo.Service_plan,
    constraint pk_plan_provides_benefits
        primary key (planID, benefitid)
)
go

create table dbo.process_payment
(
    paymentID        int not null
        constraint pk_process_payment
            primary key
        references dbo.Payment,
    planID           int
        references dbo.Service_plan,
    remaining_amount as [dbo].[function_remaining_amount]([paymentID], [planID]),
    extra_amount     as [dbo].[function_extra_amount]([paymentID], [planID])
)
go

create table dbo.shop
(
    shopID   int identity
        primary key,
    name     varchar(50),
    Category varchar(50)
)
go

create table dbo.E_shop
(
    shopID int         not null
        primary key
        references dbo.shop,
    URL    varchar(50) not null,
    rating int
)
go

create table dbo.Physical_shop
(
    shopID        int not null
        primary key
        references dbo.shop,
    address       varchar(50),
    working_hours varchar(50)
)
go

create table dbo.Voucher
(
    voucherID   int identity
        primary key,
    value       int,
    expiry_date date,
    points      int,
    mobileNo    char(11)
        references dbo.customer_account,
    redeem_date date,
    shopid      int
        references dbo.shop
)
go

create table dbo.transfer_money
(
    walletID1     int not null
        references dbo.Wallet,
    walletID2     int not null
        references dbo.Wallet,
    transfer_id   int identity,
    amount        decimal(10, 2),
    transfer_date date,
    constraint pk_transfer_money
        primary key (walletID1, walletID2, transfer_id)
)
go

CREATE VIEW [AccountPayments] AS
select * from Payment p

go

CREATE VIEW [CustomerWallet] AS
select w.*,p.first_name,p.last_name from Wallet w
inner join customer_profile p
on w.nationalID = p.nationalID

go

CREATE VIEW [E_shopVouchers] AS
select e.*, v.voucherID,v.value from E_shop e
inner join Voucher v
on e.shopID = v.shopid

go

CREATE VIEW [Num_of_cashback] AS
select walletID,count(*)as 'count of transactions' from Cashback
group by walletID

go

CREATE VIEW [PhysicalStoreVouchers] AS
select p.*, v.voucherID,v.value from Physical_shop p
inner join Voucher v
on p.shopID = v.shopid

go

CREATE VIEW [allBenefits] AS
select * from Benefits
where status = 'active'
go

CREATE VIEW [allCustomerAccounts] AS
SELECT p.*,a.mobileNo,a.account_type,a.status,a.start_date,a.balance,a.points from customer_profile p
inner join customer_account a 
on p.nationalID = a.nationalID
where a.status = 'active'

go

CREATE VIEW [allResolvedTickets] AS
select * from Technical_support_ticket
where status = 'Resolved'
go

CREATE VIEW [allServicePlans] AS
select * from Service_plan
go

CREATE VIEW [allShops] AS
select * from shop 
go

CREATE FUNCTION [AccountLoginValidation]
(@mobile_num char(11), @pass varchar(50)) --Define Function Inputs
RETURNS bit -- Define Function Output
AS
Begin
declare @output bit
IF exists((Select a.mobileNo from customer_account a
where a.mobileNo = @mobile_num and a.pass = @pass ))
set @output = 1
else 
set @output = 0

return @output
END
go

CREATE PROCEDURE [Account_Highest_Voucher]
@mobile_num char(11)  

AS
declare @max int
select @max =  max(v.value) from Voucher v 
where v.mobileNo = @mobile_num 

select v.voucherID from voucher v
where v.mobileNo = @mobile_num and v.value = @max

go

CREATE PROCEDURE [Account_Payment_Points]
@mobile_num char(11)

AS
select count(p.paymentID) AS paymentCount , sum(pb.pointsAmount) AS totalPoints from Payment P
                                                                                         inner join Points_group pb
                                                                                                    on p.paymentID = pb.paymentId
where P.mobileNo = @mobile_num and (year(current_timestamp) - year(p.date_of_payment)=1 )
  and P.status = 'successful'
go

CREATE PROCEDURE [Account_Plan]
AS
SELECT
    ca.mobileNo AS mobileNo,
    ca.pass AS pass,
    ca.balance AS balance,
    ca.account_type AS account_type,
    ca.start_date AS start_date,
    ca.status AS status,
    ca.points AS points,
    ca.nationalId AS nationalId,
    sp.planID AS planId,
    sp.name AS planName,
    sp.price AS price,
    sp.SMS_offered AS SMS_offered,
    sp.minutes_offered AS minutes_offered,
    sp.data_offered AS data_offered,
    sp.description AS description
FROM
    customer_account ca
        INNER JOIN
    Subscription s ON ca.mobileNo = s.mobileNo
        INNER JOIN
    Service_plan sp ON s.planID = sp.planID;
go

CREATE FUNCTION [Account_Plan_date]
(@sub_date date, @plan_id int) --Define Function Inputs
RETURNS Table -- Define Function Output
AS
Return (Select customer_account.mobileNo, Service_plan.planID, Service_plan.name from customer_account inner join Subscription
on customer_account.mobileNo = Subscription.mobileNo inner join Service_plan on Subscription.planID = Service_plan.planID
WHERE Subscription.subscription_date = @sub_date AND Service_plan.planID = @plan_id)
go

CREATE FUNCTION [Account_SMS_Offers]
(@mobile_num char(11)) --Define Function Inputs
RETURNS Table -- Define Function Output
AS
Return(Select o.* from Exclusive_offer o inner join Benefits b 
on o.benefitID = b.benefitID 
where b.mobileNo = @mobile_num and o.SMS_offered >0 )
go

create FUNCTION [Account_Usage_Plan]
(@mobile_num char(11), @start_date date) --Define Function Inputs
    RETURNS Table -- Define Function Output
        AS
        Return (Select Plan_Usage.planID, sum(Plan_Usage.data_consumption) as 'totalData' ,sum(Plan_Usage.minutes_used) as 'totalMins',sum(Plan_Usage.SMS_sent) as 'totalSMS'
                from Plan_Usage
                where  Plan_Usage.mobileNo = @mobile_num and Plan_Usage.start_date >= @start_date
                group by Plan_Usage.planID)
go

CREATE PROCEDURE [Benefits_Account]
@mobile_num char(11), @plan_id int

AS
update Benefits
set mobileNo = null
from  Benefits B inner join plan_provides_benefits pb
on B.benefitID = pb.benefitid 
where B.mobileNo = @mobile_num and pb.planID = @plan_id
go

CREATE FUNCTION CHECK_CUSTOMER_NID_MOBILE
(
    @mobileNumber CHAR(11),
    @nationalId INT
)
    RETURNS INT
AS
BEGIN
    DECLARE @isValid INT;

    SELECT @isValid = COUNT(*)
    FROM customer_account ca
    WHERE ca.mobileNo = @mobileNumber AND ca.nationalID = @nationalId;

    RETURN @isValid;
END
go

CREATE FUNCTION [Cashback_Wallet_Customer]
(@NID int) --Define Function Inputs
    RETURNS Table -- Define Function Output
        AS
        Return(select c.cashbackID AS cashbackID,c.benefitID AS benefitID,c.walletID AS walletID,c.amount AS amount,
                      c.credit_date AS creditDate from Cashback c
                                   inner join Wallet w
                                              on c.walletID = w.walletID
               where w.nationalID = @NID)
go

CREATE FUNCTION [Consumption]
(@Plan_name varchar(50), @start_date date, @end_date date) --Define Function Inputs
    RETURNS Table -- Define Function Output
        AS
        Return(Select p.data_consumption,p.minutes_used,p.SMS_sent AS sms from Plan_Usage p
                                                                                   inner join Service_plan s on s.planID = p.planID
               where s.name = @Plan_name and p.start_date >= @start_date and p.end_date <= @end_date)
go

CREATE FUNCTION [Extra_plan_amount]
(@mobile_num char(11), @plan_name varchar(50)) --Define Function Inputs
RETURNS int -- Define Function Output
AS
Begin
declare @output int, @plan_id int, @payment_id int
Select @plan_id = s.planID, @payment_id= p.paymentID from Service_plan s inner join process_payment pp
on s.planID = pp.planID inner join payment p 
on pp.paymentID = p.paymentID
where s.name = @plan_name and p.mobileNo = @mobile_num and p.status='successful'

set @output = dbo.function_extra_amount(@payment_id,@plan_id)
return @output
END
go

CREATE PROCEDURE [Initiate_balance_payment]
@mobile_num char(11), @amount decimal(10,1), @payment_method varchar(50)

as
Insert into Payment (amount,date_of_payment,payment_method,status,mobileNo)
values(@amount,CURRENT_TIMESTAMP,@payment_method,'successful',@mobile_num)

update customer_account
set balance = balance + @amount
where mobileNo = @mobile_num

go

CREATE PROCEDURE [Initiate_plan_payment]
@mobile_num char(11), @amount decimal(10,1), @payment_method varchar(50),
@plan_id int 

AS
declare @payment_id int
Insert into Payment (amount,date_of_payment,payment_method,status,mobileNo)
values(@amount,CURRENT_TIMESTAMP,@payment_method,'successful',@mobile_num)
SELECT @payment_id = p.paymentID from Payment p    
where p.mobileNo = @mobile_num and p.amount = @amount and p.date_of_payment = CAST(CURRENT_TIMESTAMP AS DATE)
and p.payment_method = @payment_method and p.status = 'successful'
Insert into process_payment(paymentID, planID) values(@payment_id, @plan_id)
if(select remaining_amount from process_payment where planID = @plan_id and paymentID = @payment_id) = 0 
update Subscription
set status = 'active'
where planID = @plan_id and mobileNo = @mobile_num
else
update Subscription
set status = 'onhold'
where planID = @plan_id and mobileNo = @mobile_num

go

CREATE PROCEDURE [Payment_wallet_cashback]
@mobile_num char(11), @payment_id int, @benefit_id int 

AS
declare @amount int, @cash_amount int, @wallet_id int 
select @amount = p.amount  from Payment p
where p.paymentID = @payment_id and p.status = 'successful'
set @cash_amount = 0.1 * @amount
select @wallet_id = w.walletID from Wallet w
inner join customer_account a on
w.nationalID = a.nationalID 
where a.mobileNo = @mobile_num

Insert into Cashback(benefitID,walletID,amount,credit_date)
values(@benefit_id,@wallet_id,@cash_amount,current_timestamp)

update Wallet
set current_balance = current_balance + @cash_amount,
last_modified_date = current_timestamp
where walletID = @wallet_id

go

CREATE PROCEDURE [Redeem_voucher_points]
    @mobile_num char(11), @voucher_id int

AS
    declare @isSuccess int=1;
    If (Select v.points from Voucher v
        where v.voucherID = @voucher_id and v.expiry_date >CURRENT_TIMESTAMP ) <= (Select a.points from customer_account a
                                                                                   where a.mobileNo = @mobile_num)
        begin
            declare @voucher_points int
            select @voucher_points = points from Voucher
            where voucherID = @voucher_id
            update Voucher
            set mobileNo = @mobile_num , redeem_date = current_timestamp
            where voucherID = @voucher_id

            update customer_account
            set points = points - @voucher_points
            where mobileNo = @mobile_num
        end
    else
        begin
        set @isSuccess =0;
        end
    SELECT @isSuccess;
go

CREATE FUNCTION [Remaining_plan_amount]
(@mobile_num char(11), @plan_name varchar(50)) --Define Function Inputs
RETURNS int -- Define Function Output
AS
Begin
declare @output int, @plan_id int, @payment_id int
Select @plan_id = s.planID, @payment_id= p.paymentID from Service_plan s inner join process_payment pp
on s.planID = pp.planID inner join payment p 
on pp.paymentID = p.paymentID
where s.name = @plan_name and p.mobileNo = @mobile_num and p.status='successful'

set @output = dbo.function_remaining_amount(@payment_id,@plan_id)
return @output
END
go

CREATE FUNCTION [Subscribed_plans_5_Months]
(@MobileNo char(11)) --Define Function Inputs
RETURNS Table -- Define Function Output
AS
Return(Select sp.* from Service_plan sp 
inner join Subscription s 
on sp.planID = s.planID
where s.mobileNo = @MobileNo and 
s.subscription_date >= DATEADD(month,-5,CURRENT_TIMESTAMP))
go

CREATE PROCEDURE [Ticket_Account_Customer]
@NID int 

AS
select count(t.ticketID) from Technical_support_ticket t
inner join customer_account a 
on t.mobileNo = a.mobileNo
where t.status <> 'resolved' and a.nationalID = @NID
go

CREATE PROCEDURE [Top_Successful_Payments]
@mobile_num char(11)  

AS
select top 10 p.* from Payment p 
where p.mobileNo = @mobile_num
and p.status = 'successful'
order by p.amount desc
go

CREATE PROCEDURE [Total_Points_Account]
@mobile_num char(11)  

AS
declare @sum int
select @sum =  sum(pg.pointsAmount) from Points_group pg
inner join Payment p
on pg.paymentId = p.paymentID
where p.mobileNo = @mobile_num

update customer_account  
set points = @sum
where mobileNo = @mobile_num

delete from Points_group
where pointId in  (select pg.pointId from Points_group pg
inner join Payment p on pg.paymentId = p.paymentID
where p.mobileNo = @mobile_num)
go

CREATE PROCEDURE [Unsubscribed_Plans]
@mobile_num char(11)

AS
select  sp.* from  Service_plan sp 
where sp.planID not in (
select s.planID  from Subscription s
where s.mobileNo = @mobile_num)
go

CREATE FUNCTION [Usage_Plan_CurrentMonth]
(@mobile_num char(11)) --Define Function Inputs
RETURNS Table -- Define Function Output
AS
Return(select p.data_consumption, p.minutes_used, p.SMS_sent from Plan_Usage p
inner join Subscription s 
on p.planID = s.planID and p.mobileNo = s.mobileNo
where p.mobileNo = @mobile_num and s.status = 'active' 
and month(p.start_date)= month(current_timestamp) or month(p.end_date)= month(current_timestamp) and year(p.start_date)= year(current_timestamp) or year(p.end_date)= year(current_timestamp))
go

CREATE FUNCTION [Wallet_Cashback_Amount]
(@walletID int, @planID int) --Define Function Inputs
RETURNS int -- Define Function Output
AS
Begin
declare @amount int

set @amount = (Select sum(c.amount) from Cashback c 
inner join plan_provides_benefits pb 
on c.benefitID = pb.benefitid
where c.walletID = @walletID and pb.planID = @planID)

return @amount
END
go

CREATE FUNCTION [Wallet_MobileNo]
(@mobile_num char(11)) --Define Function Inputs
RETURNS bit -- Define Function Output
AS
Begin
declare @output bit
IF exists((Select w.walletID from Wallet w
where w.mobileNo = @mobile_num ))
set @output = 1
else 
set @output = 0

return @output
END
go

CREATE FUNCTION [Wallet_Transfer_Amount]
(@walletID int, @start_date date, @end_date date) --Define Function Inputs
RETURNS int -- Define Function Output
AS
Begin
declare @avg int

set @avg = (Select avg(t.amount) from transfer_money t
where t.walletID1 = @walletID and t.transfer_date between @start_date and @end_date)

return @avg
END
go

-------------------------------------------------------------------------------------
------------------------create tables Procedure--------------------------------------------
CREATE PROCEDURE [createAllTables]

AS

create table customer_profile(
nationalID int primary key,
first_name varchar(50) not null, 
last_name varchar(50) not null,
email varchar(50),
address varchar(70) not null, 
date_of_birth date
)


create table customer_account(
mobileNo char(11) primary key,
pass varchar(50),
balance decimal(10,1),
account_type varchar(50) check(account_type = 'postpaid' or account_type = 'prepaid' or account_type = 'pay-as-you-go' ),
start_date date not null,
status varchar(50) check(status = 'active' or status = 'onhold'),
points int default 0,
nationalID int,
FOREIGN KEY (nationalID) REFERENCES customer_profile (nationalID)
)

create table Service_plan(
planID int identity primary key,
name varchar(50) not null,
price int not null,
SMS_offered int not null,
minutes_offered int not null,
data_offered int not null,
description varchar(50)
)

create table Subscription(
mobileNo Char(11),
planID int,
subscription_date date,
status varchar(50) check(status='active' or status='onhold'),
constraint pk_subscription primary key (mobileNo,planID),
FOREIGN KEY (mobileNo) REFERENCES customer_account (mobileNo),
FOREIGN KEY (planID) REFERENCES Service_plan (planID)
)

create table Plan_Usage(
usageID int identity primary key,
start_date date not null,
end_date date not null,
data_consumption int,
minutes_used int ,
SMS_sent int  , 
mobileNo Char(11) , 
planID int,
FOREIGN KEY (mobileNo) REFERENCES customer_account (mobileNo),
FOREIGN KEY (planID) REFERENCES Service_plan (planID)
)


create table Payment(
paymentID int identity primary key,
amount decimal(10,1) not null,
date_of_payment date not null,
payment_method varchar(50) check(payment_method ='cash' or payment_method ='credit'),
status varchar(50) check(status ='successful' or status='rejected' or status='pending'),
mobileNo Char(11),
FOREIGN KEY (mobileNo) REFERENCES customer_account (mobileNo)
)




create table process_payment(
paymentID int,
planID int,
FOREIGN KEY (paymentID) REFERENCES Payment (paymentID),
FOREIGN KEY (planID) REFERENCES Service_plan (planID),

remaining_amount as(dbo.function_remaining_amount(paymentID, planID)),
extra_amount as (dbo.function_extra_amount(paymentID, planID)),

constraint pk_process_payment primary key (paymentID) 
)

create table Wallet
(
walletID int identity primary key,
current_balance decimal(10,2) default 0,
currency varchar(50) default 'egp',
last_modified_date date ,
nationalID int,
mobileNo char(11),
FOREIGN KEY (nationalID) REFERENCES customer_profile (nationalID)
)

create table transfer_money(
walletID1 int, 
walletID2 int, 
transfer_id int identity,
amount decimal (10,2),
transfer_date date, 
constraint pk_transfer_money primary key (walletID1,walletID2,transfer_id),
FOREIGN KEY (walletID1) REFERENCES Wallet(walletID),
FOREIGN KEY (walletID2) REFERENCES Wallet (walletID)
)

create table Benefits (
benefitID int primary key identity, 
description varchar(50),
validity_date date, 
status varchar (50) check(status='active' or status ='expired'),
mobileNo char(11), 
FOREIGN KEY (mobileNo) REFERENCES customer_account(mobileNo)
)

create table Points_group(
pointId int identity,
benefitID int, 
pointsAmount int,
paymentId int,
FOREIGN KEY (paymentId) REFERENCES Payment(paymentID),
FOREIGN KEY (benefitID) REFERENCES Benefits(benefitID),
constraint pk_Points_group primary key (pointId,benefitId)
)

create table Exclusive_offer (
offerID int identity, 
benefitID int, 
internet_offered int ,
SMS_offered int,
minutes_offered int,
FOREIGN KEY (benefitID) REFERENCES Benefits(benefitID),
constraint pk_Exclusive_offer primary key (offerID,benefitId)

)

create table Cashback(
cashbackID int identity, 
benefitID int, 
walletID int, 
amount int,
credit_date date,FOREIGN KEY (benefitID) REFERENCES Benefits(benefitID),
FOREIGN KEY (walletid) REFERENCES Wallet(walletid),
constraint pk_Cashback primary key (cashbackID,benefitId)
)

create table plan_provides_benefits(
benefitid int, 
planID int, 
FOREIGN KEY (benefitID) REFERENCES Benefits(benefitID),
FOREIGN KEY (planID) REFERENCES service_plan(planID),
constraint pk_plan_provides_benefits primary key (planID,benefitId)
)

create table shop (
shopID int identity primary key,
name varchar(50),
Category varchar(50)
)
create table Physical_shop (
shopID int primary key, 
address varchar(50),
working_hours varchar(50),
FOREIGN KEY (shopID) REFERENCES shop(shopID),
)

create table E_shop (
shopID int primary key , 
URL varchar(50) not null,
rating int,
FOREIGN KEY (shopID) REFERENCES shop(shopID),
)

create table Voucher (
voucherID int identity primary key,
value int,
expiry_date date,
points int, 
mobileNo char(11),
redeem_date date,
shopid int, 
FOREIGN KEY (shopID) REFERENCES shop(shopID),
FOREIGN KEY (mobileNo) REFERENCES customer_account(mobileNo)
)



create table Technical_support_ticket (
ticketID int identity,
mobileNo char(11), 
issue_description varchar(50),
priority_level int,
status varchar(50) check (status in ('Open','In progress','Resolved'))
FOREIGN KEY (mobileNo) REFERENCES customer_account(mobileNo),
constraint pk_Technical_support_ticket primary key (ticketID,mobileNo)
)

go

CREATE FUNCTION [function_extra_amount]
(@paymentId int, @planId int) --Define Function Inputs
RETURNS int -- Define Function Output
AS

Begin

declare @amount int

If (SELECT payment.amount FROM payment WHERE payment.paymentid=@paymentId)  > (SELECT Service_plan.price FROM Service_plan
WHERE Service_plan.planid=@planid)

Set @amount = (SELECT payment.amount FROM payment WHERE payment.paymentid=@paymentId) - (SELECT Service_plan.price FROM Service_plan WHERE Service_plan.planid=@planid)

Else
Set @amount = 0

Return @amount

END

go

CREATE FUNCTION [function_remaining_amount]
(@paymentId int, @planId int) --Define Function Inputs
RETURNS int -- Define Function Output
AS

Begin

declare @amount int

If (SELECT payment.amount FROM payment WHERE payment.paymentid=@paymentId)  < (SELECT Service_plan.price FROM Service_plan
WHERE Service_plan.planid=@planid)

Set @amount =  (SELECT Service_plan.price FROM Service_plan WHERE Service_plan.planid=@planid) - (SELECT payment.amount FROM payment
WHERE payment.paymentid=@paymentId)

Else
Set @amount = 0

Return @amount

END
go

