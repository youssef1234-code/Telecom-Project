package com.telecom.telecom.repositories;

import com.telecom.telecom.dtos.projection.*;
import com.telecom.telecom.entities.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProceduresRepository extends JpaRepository<CustomerAccount, Long> {

    // A list is used for the case that there is a tie between vouchers
    @Procedure(value = "Account_Highest_Voucher")
    List<Integer> getAccountHighestVoucher(@Param("mobile_num") String mobileNum);

    @Procedure(value = "Account_Payment_Points")
    PaymentPointsProjection getAccountPaymentPoints(@Param("mobile_num") String mobileNum);

    @Procedure(value = "Account_Plan")
    List<AccountPlanProjection> getAllAccountsPlans();

    @Procedure(value = "Benefits_Account")
    void deletePlanBenefitsFromAccount(@Param("mobile_num") String mobileNum, @Param("plan_id") Integer planID);

    @Procedure(value = "Initiate_balance_payment")
    void initiateBalancePayment(@Param("mobile_num") String mobileNum, @Param("amount") BigDecimal amount, @Param("payment_method") String payment_method);

    @Procedure(value = "Initiate_plan_payment")
    void initiatePlanPayment(@Param("mobile_num") String mobileNum, @Param("amount") BigDecimal amount, @Param("payment_method") String payment_method, @Param("plan_id") Integer planID);

    @Procedure(value = "Payment_wallet_cashback")
    void paymentWalletCashback(@Param("mobile_num") String mobileNum, @Param("plan_id") Integer planID , @Param("benefit_id") Integer benefitID);

    @Procedure(value = "Redeem_voucher_points") // Contains print statement PRINT 'no enough points to redeem voucher'
    void redeemVoucherPoints(@Param("mobile_num") String mobileNum, @Param("voucher_id") Integer voucherID);

    @Procedure(value = "Ticket_Account_Customer")
    Integer getTicketAccountCustomers(@Param("NID") Integer nID);

    @Procedure(value = "Top_Successful_Payments")
    List<TopSuccessfulPaymentsProjection> topSuccessfulPayments(@Param("mobile_num") String mobileNum);

    @Procedure(value = "Total_Points_Account")
    void totalPointsAccount(@Param("mobile_num") String mobileNum);

    @Procedure(value = "Unsubscribed_Plans")
    List<UnsubscribedPlansProjection> unsubscribedPlans(@Param("mobile_num") String mobileNum);







}
