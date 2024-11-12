-- Insert Customer Profiles
INSERT INTO Customer_profile (nationalID, first_name, last_name, email, address, date_of_birth)
VALUES 
('101', 'John', 'Doe', 'john.doe@example.com', '123 Elm St, Springfield', '1985-01-15'),
('102', 'Jane', 'Smith', 'jane.smith@example.com', '456 Oak St, Springfield', '1990-03-22'),
('103', 'Alice', 'Johnson', 'alice.johnson@example.com', '789 Pine St, Springfield', '1982-07-11'),
('104', 'Bob', 'Davis', 'bob.davis@example.com', '101 Maple St, Springfield', '1975-12-30');

GO 

-- Insert Customer Accounts
INSERT INTO Customer_Account (mobileNo, pass, balance, account_type, start_date, status, point, nationalID)
VALUES 
('01012345678', 'password123', 100.50, 'Post Paid', '2020-01-01', 'active', 0, '101'),
('01023456789', 'password234', 50.75, 'Prepaid', '2021-05-05', 'onhold', 10, '102'),
('01034567890', 'password345', 150.00, 'Pay_as_you_go', '2019-09-09', 'active', 50, '103'),
('01045678901', 'password456', 75.00, 'Prepaid', '2022-08-22', 'active', 30, '104');

GO

-- Insert Service Plans
INSERT INTO Service_Plan (SMS_offered, minutes_offered, data_offered, name, price, description)
VALUES 
(1000, 500, 5000, 'Basic Plan', 20, 'Basic plan with SMS'),
(2000, 1000, 10000, 'Premium Plan', 40, 'Premium plan with more SMS'),
(1500, 800, 8000, 'Standard Plan', 30, 'Standard plan with moderate SMS');
GO

-- Insert Subscriptions
INSERT INTO Subscription (mobileNo, planID, subscription_date, status)
VALUES 
('01012345678', 13, '2020-01-01', 'active'),
('01023456789', 14, '2021-05-05', 'onhold'),
('01034567890', 15, '2019-09-09', 'active'),
('01045678901', 14, '2022-08-22', 'active');

GO

-- Insert Plan Usage
INSERT INTO Plan_Usage (start_date, end_date, data_consumption, minutes_used, SMS_sent, mobileNo, planID)
VALUES 
('2020-01-01', '2020-01-31', 1000, 200, 300, '01012345678', 13),
('2021-05-05', '2021-05-20', 500, 100, 150, '01023456789', 14),
('2019-09-09', '2019-09-30', 1200, 300, 400, '01034567890', 15),
('2022-08-22', '2022-08-31', 800, 150, 250, '01045678901', 14);

GO

-- Insert Payments
INSERT INTO Payment (amount, date_of_payment, payment_method, status, mobileNo)
VALUES 
(50.00, '2024-11-05', 'cash', 'successful', '01012345678'),
(30.00, '2024-11-04', 'credit', 'pending', '01023456789'),
(100.00, '2024-11-03', 'cash', 'successful', '01034567890'),
(20.00, '2024-11-02', 'credit', 'rejected', '01045678901');

GO



-- Insert Process Payments
INSERT INTO Process_Payment (paymentID, planID)
VALUES 
(9, 13),
(11, 15);

GO

-- Insert Wallets
INSERT INTO Wallet (current_balance, currency, last_modified_date, nationalID, mobileNo)
VALUES 
(100.50, 'USD', '2024-11-05', '101', '01012345678'),
(50.75, 'USD', '2024-11-04', '102', '01023456789'),
(150.00, 'USD', '2024-11-03', '103', '01034567890'),
(75.00, 'USD', '2024-11-02', '104', '01045678901');

GO


-- Insert Transfers (for same wallet IDs)
INSERT INTO Transfer_money (walletID1, walletID2, amount, transfer_date)
VALUES 
(9, 10, 25.00, '2024-11-05'),
(11, 12, 10.00, '2024-11-04');

GO


-- Insert Benefits
INSERT INTO Benefits (description, validity_date, status, mobileNo)
VALUES 
('Free data for 1 month', '2024-12-31', 'active', '01012345678'),
('Discount on recharge', '2024-12-31', 'active', '01023456789'),
('Free SMS for 1 month', '2024-12-31', 'expired', '01034567890'),
('Cashback offer', '2024-12-31', 'active', '01045678901');

GO

-- Insert Points Group
INSERT INTO Points_Group (benefitID, pointsAmount, PaymentID)
VALUES 
(9, 100, 9),
(10, 200, 11),
(11, 150, 12),
(12, 50, 10);

GO

-- Insert Exclusive Offers
INSERT INTO Exclusive_Offer (benefitID, internet_offered, SMS_offered, minutes_offered)
VALUES 
(9, 5000, 1000, 200),
(11, 10000, 2000, 500),
(12, 2000, 300, 100);

GO

-- Insert Cashback
INSERT INTO Cashback (benefitID, walletID, amount, credit_date)
VALUES 
(9, 9, 10, '2024-11-05'),
(11, 10, 15, '2024-11-04'),
(12, 12, 5, '2024-11-02');

GO


-- Insert Plan Provides Benefits
INSERT INTO Plan_Provides_Benefits (benefitID, planID)
VALUES 
(9, 13),
(10, 13),
(11, 14),
(12, 15);

GO

-- Insert Shops
INSERT INTO Shop (name, category)
VALUES 
('Shop A', 'Electronics'),
('Shop B', 'Clothing');

GO

INSERT INTO Shop (name, category)
VALUES 
('3AM SA3D', 'TA3MEYA'),
('ABO HUSSEIN', 'Clothing');



-- Insert Physical Shops
INSERT INTO Physical_Shop (shopID, address, working_hours)
VALUES 
(5, '123 Main St', '9AM - 6PM'),
(6, '456 High St', '10AM - 8PM');

GO

-- Insert E-shops
INSERT INTO E_shop (shopID, URL, rating)
VALUES 
(7, 'www.shopa.com', 5),
(8, 'www.shopb.com', 4);

GO

-- Insert Vouchers
INSERT INTO Voucher (value, expiry_date, points, mobileNo, shopID, redeem_date)
VALUES 
(50, '2024-12-31', 100, '01012345678', 5, '2024-11-05'),
(20, '2024-12-31', 50, '01023456789', 6, '2024-11-04');

GO

-- Insert Technical Support Tickets
INSERT INTO Technical_Support_Ticket (mobileNo, Issue_description, priority_level, status)
VALUES 
('01012345678', 'Network issue', 2, 'Open'),
('01023456789', 'Account not accessible', 1, 'In Progress'),
('01034567890', 'Plan activation failure', 3, 'Resolved'),
('01045678901', 'Incorrect billing', 2, 'Open');

GO