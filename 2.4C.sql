GO
CREATE FUNCTION Consumption
(@Plan_name varchar(50), 
@start_date date,
@end_date date)
RETURNS TABLE
AS
	RETURN(
		SELECT pu.data_consumption,pu.minutes_used,pu.SMS_sent 
		FROM Plan_Usage pu JOIN Service_Plan sp ON pu.planID = sp.planID
		WHERE UPPER(sp.name) = UPPER(@Plan_name) 
			AND @start_date >= pu.start_date
			AND @end_date >= pu.end_date
	);
GO

GO 
CREATE FUNCTION Usage_Plan_CurrentMonth
(@MobileNo char(11))
RETURNS TABLE
AS
	RETURN(
		SELECT pu.data_consumption,pu.minutes_used,pu.SMS_sent 
		FROM (Plan_Usage pu JOIN Service_Plan sp ON pu.planID = sp.planID) JOIN Subscription sub ON pu.planID = sub.planID
		WHERE pu.mobileNo = @MobileNo
			AND sub.mobileNo = @MobileNo
			AND sub.status = 'active'
			AND month(CURRENT_TIMESTAMP) = month(pu.start_date)
	);
GO 


GO

CREATE PROC Ticket_Account_Customer
(@NationalID int)
AS
BEGIN
	DECLARE @count int;

	SELECT @count = COUNT(*)
	FROM Technical_Support_Ticket tst JOIN Customer_Account ca on tst.mobileNo = ca.mobileNo
	WHERE NOT (tst.status = 'Resolved') AND ca.nationalID = @NationalID
	SELECT @count;
END;


GO
CREATE FUNCTION Remaining_plan_amount
(@Plan_name varchar(50), 
@MobileNo char(11))
RETURNS decimal (10,1)
AS
BEGIN
	DECLARE @amount int;

	SELECT @amount = remaining_balance
	FROM (Process_Payment pp JOIN Service_Plan sp ON pp.planID = sp.planID) JOIN Subscription sub ON sub.planID = pp.planID
	WHERE UPPER(sp.name) = UPPER(@Plan_name) AND sub.mobileNo= @MobileNo

	RETURN @amount;
END;
GO

GO
CREATE FUNCTION Extra_plan_amount
(@Plan_name varchar(50), 
@MobileNo char(11))
RETURNS decimal (10,1)
AS
BEGIN
	DECLARE @amount int;

	SELECT @amount = extra_amount
	FROM (Process_Payment pp JOIN Service_Plan sp ON pp.planID = sp.planID) JOIN Subscription sub ON sub.planID = pp.planID
	WHERE UPPER(sp.name) = UPPER(@Plan_name) AND sub.mobileNo= @MobileNo

	RETURN @amount;
END;
GO


GO 
CREATE PROC Top_Successful_Payments
(@MobileNo char(11))
AS
BEGIN
		SELECT TOP 10*
		FROM Payment PA
		WHERE PA.mobileNo = @MobileNo AND PA.status = 'successful'
END;
GO 


CREATE PROC Initiate_plan_payment
(@MobileNo char(11) ,@amount decimal(10,1),
@payment_method varchar(50),@plan_id int)
AS
BEGIN 
	DECLARE @servicePlanPrice decimal(10,2);
	DECLARE @remainingBalance decimal(10,1)=0;
	DECLARE @extra_amount decimal(10,1)=0;

	SELECT @servicePlanPrice = sp.price 
	FROM Service_Plan sp
	WHERE sp.planID = @plan_id

	IF @amount < @servicePlanPrice
	BEGIN 
		set @remainingBalance = @servicePlanPrice - @amount
	END

	IF @amount > @servicePlanPrice
	BEGIN 
		set @extra_amount =  @amount - @servicePlanPrice
	END;


	INSERT INTO Payment (amount,date_of_payment,payment_method,status,mobileNo)
	VALUES(@amount,CURRENT_TIMESTAMP,@payment_method,'successful',@MobileNo)

	INSERT INTO Process_Payment(planID,remaining_balance,extra_amount)
	VALUES(@plan_id,@remainingBalance,@extra_amount)

	INSERT INTO Subscription (mobileNo,planID,subscription_date,status)
	VALUES(@MobileNo,@plan_id,CURRENT_TIMESTAMP,'active')
END;



