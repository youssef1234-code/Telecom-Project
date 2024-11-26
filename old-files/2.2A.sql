use Telecom_Team_1
GO
CREATE VIEW allServicePlans
	AS
	SELECT *
	FROM Service_Plan
	;
GO
CREATE VIEW AccountPayments
	AS
	SELECT p.amount, p.date_of_payment, cp.first_name, cp.lASt_name
	FROM Payment p, customer_account ca, customer_profile cp
	WHERE p.mobileNo = ca.mobileNo AND cp.nationalID = ca.nationalID
GO
CREATE VIEW allResolvedTickets
AS
	SELECT *
	FROM Technical_Support_Ticket
	WHERE status = 'Resolved'
GO
CREATE VIEW E_shopVouchers
AS
	SELECT e.URL, v.value, v.voucherID
	FROM E_shop e, Voucher v
	WHERE e.shopID = v.shopID AND v.redeem_date IS NOT NULL
GO
CREATE VIEW Num_of_cAShback
AS
	SELECT w.walletID,Count(c.CAShbackID) AS Number_Of_CAShbacks
	FROM Wallet w, CAShback c
	WHERE w.walletID= c.walletID
	Group By w.walletID
GO
