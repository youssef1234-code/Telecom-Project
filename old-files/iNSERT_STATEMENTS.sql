-- Inserting sample data into Customer_profile table
INSERT INTO Customer_profile (nationalID, first_name, last_name, email, address, date_of_birth)
VALUES 
(1001, 'John', 'Doe', 'john.doe@example.com', '123 Elm St', '1985-06-15'),
(1002, 'Jane', 'Smith', 'jane.smith@example.com', '456 Oak St', '1990-11-22');

-- Inserting sample data into Customer_Account table
INSERT INTO Customer_Account (mobileNo, pass, balance, account_type, start_date, status, point, nationalID)
VALUES 
('01012345678', 'password123', 100.50, 'Post Paid', '2024-01-01', 'active', 0, 1001),
('01098765432', 'password456', 50.75, 'Prepaid', '2024-02-01', 'active', 10, 1002);


-- Inserting sample data into Service_Plan table
INSERT INTO Service_Plan (SMS_offered, minutes_offered, data_offered, name, price, description)
VALUES 
(500, 300, 5000, 'Basic Plan', 100, '500 SMS, 300 minutes, 5GB Data'),
(1000, 500, 10000, 'Premium Plan', 200, '1000 SMS, 500 minutes, 10GB Data');

-- Inserting sample data into Subscription table
INSERT INTO Subscription (mobileNo, planID, subscription_date, status)
VALUES 
('01012345678', 1, '2024-01-01', 'active'),
('01098765432', 2, '2024-02-01', 'onhold');

-- Inserting sample data into Plan_Usage table
INSERT INTO Plan_Usage (start_date, end_date, data_consumption, minutes_used, SMS_sent, mobileNo, planID)
VALUES 
('2024-01-01', '2024-01-31', 3000, 200, 300, '01012345678', 1),
('2024-02-01', '2024-02-29', 5000, 400, 700, '01098765432', 2);

-- Inserting sample data into Payment table
INSERT INTO Payment (amount, date_of_payment, payment_method, status, mobileNo)
VALUES 
(150.00, '2024-01-10', 'credit', 'successful', '01012345678'),
(75.00, '2024-02-15', 'cash', 'pending', '01098765432');

-- Inserting sample data into Process_Payment table
INSERT INTO Process_Payment (paymentID, planID, remaining_balance, extra_amount)
VALUES 
(1, 1, 50.50, 10.00),
(2, 2, 0.00, 5.00);

-- Inserting sample data into Wallet table
INSERT INTO Wallet (current_balance, currency, last_modified_date, nationalID, mobileNo)
VALUES 
(500.00, 'USD', '2024-01-01', 1001, '01012345678'),
(200.00, 'USD', '2024-02-01', 1002, '01098765432');

-- Inserting sample data into Transfer_money table
INSERT INTO Transfer_money (walletID1, walletID2, amount, transfer_date)
VALUES 
(1, 2, 50.00, '2024-02-10'),
(2, 1, 25.00, '2024-02-15');

-- Inserting sample data into Benefits table
INSERT INTO Benefits (description, validity_date, status, mobileNo)
VALUES 
('Free SMS', '2024-12-31', 'active', '01012345678'),
('Extra Data', '2024-06-30', 'expired', '01098765432');

-- Inserting sample data into Points_Group table
INSERT INTO Points_Group (benefitID, pointsAmount, PaymentID)
VALUES 
(1, 100, 1),
(2, 50, 2);

-- Inserting sample data into Exclusive_Offer table
INSERT INTO Exclusive_Offer (benefitID, internet_offered, SMS_offered, minutes_offered)
VALUES 
(1, 5000, 200, 100),
(2, 10000, 500, 300);

-- Inserting sample data into Cashback table
INSERT INTO Cashback (benefitID, walletID, amount, credit_date)
VALUES 
(1, 1, 10, '2024-01-05'),
(2, 2, 5, '2024-02-10');

-- Inserting sample data into Plan_Provides_Benefits table
INSERT INTO Plan_Provides_Benefits (benefitID, planID)
VALUES 
(1, 1),
(2, 2);

-- Inserting sample data into Shop table
INSERT INTO Shop (name, category)
VALUES 
('Shop1', 'Electronics'),
('Shop2', 'Clothing');

-- Inserting sample data into Physical_Shop table
INSERT INTO Physical_Shop (shopID, address, working_hours)
VALUES 
(1, '789 Maple St', '9AM - 6PM'),
(2, '101 Pine St', '10AM - 7PM');

-- Inserting sample data into E_shop table
INSERT INTO E_shop (shopID, URL, rating)
VALUES 
(1, 'www.shop1.com', 4),
(2, 'www.shop2.com', 5);

-- Inserting sample data into Voucher table
INSERT INTO Voucher (value, expiry_date, points, mobileNo, shopID, redeem_date)
VALUES 
(50, '2024-12-31', 100, '01012345678', 1, '2024-01-01'),
(100, '2024-06-30', 200, '01098765432', 2, '2024-02-10');

-- Inserting sample data into Technical_Support_Ticket table
INSERT INTO Technical_Support_Ticket (mobileNo, Issue_description, priority_level, status)
VALUES 
('01012345678', 'Connection issue', 1, 'Open'),
('01098765432', 'Billing inquiry', 2, 'In Progress');
