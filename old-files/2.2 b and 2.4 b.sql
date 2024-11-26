use Telecom_Team_1
GO
CREATE View allCustomerAccounts as
SELECT CP.*, CA.mobileNo
From Customer_Profile CP, Customer_Account CA
WHERE CP.nationalID = CA.nationalID AND CA.status = 'active';

GO
CREATE VIEW allBenefits as
SELECT *
FROM Benefits
WHERE status = 'active';

GO
CREATE VIEW allShops as
SELECT *
FROM Shop

GO
CREATE VIEW CustomerWallet as
SELECT W.*, CP.first_name, CP.last_name
FROM Wallet W, Customer_profile CP
WHERE W.nationalID = CP.nationalID

GO
CREATE VIEW PhysicalStoreVouchers as
SELECT PS.*, V.voucherID, V.value
FROM Physical_Shop PS, Voucher V
where PS.shopID = V.shopID AND V.redeem_date is NOT NULL

--------------------------------------
-----------------2.4------------------

GO
CREATE PROCEDURE Payment_wallet_cashback ---- TODO					   m)
@MobileNo varchar(11), @payment_id int, @benefit_id int
AS
BEGIN
DECLARE @amount decimal(10,2), @wallet_id int
SELECT @wallet_id = walletID
FROM Wallet
WHERE mobileNo = @MobileNo

SELECT @amount = 0.1 * amount
FROM Payment
WHERE paymentID = @payment_id

UPDATE Wallet
SET current_balance = current_balance + @amount
WHERE mobileNo = @MobileNo

INSERT INTO Cashback
VAlUES (@benefit_id, @wallet_id, @amount, getdate())

END


-- n) TODO

GO																		
CREATE PROCEDURE Initiate_balance_payment
(@MobileNo char(11), @amount decimal(10,1), @payment_method varchar(50))
AS
BEGIN
INSERT INTO Payment(amount, date_of_payment, payment_method, status, mobileNo)
VALUES(@amount, getdate(), @payment_method, 'accepted', @MobileNo)


END

-- o)

GO
CREATE PROCEDURE Redeem_voucher_points								
(@MobileNo char(11), @voucher_id int)
AS
BEGIN
DECLARE @price int;

SELECT @price = points
FROM Voucher 
WHERE voucherID = @voucher_id

UPDATE Customer_Account
SET point = point - @price
WHERE mobileNo = @MobileNo AND @price < point;

END

