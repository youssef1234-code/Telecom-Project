package com.telecom.telecom.repositories;

import com.telecom.telecom.dtos.AccountPlan;
import com.telecom.telecom.dtos.PaymentPoints;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProceduresRepository{

    // A list is used for the case that there is a  tie between vouchers
    @Procedure(name = "Account_Highest_Voucher")
    List<Integer> getAccountHighestVoucher(@Param("mobile_num") String mobileNum);

    @Procedure(name = "Account_Payment_Points")
    PaymentPoints getAccountPaymentPoints(@Param("mobile_num") String mobileNum);

    @Procedure(name = "Account_Plan")
    AccountPlan getAllAccountsPlans();




}
