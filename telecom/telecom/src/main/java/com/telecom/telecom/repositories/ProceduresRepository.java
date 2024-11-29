package com.telecom.telecom.repositories;

import com.telecom.telecom.dtos.projection.AccountPlanProjection;
import com.telecom.telecom.dtos.projection.PaymentPointsProjection;
import com.telecom.telecom.entities.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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




}
