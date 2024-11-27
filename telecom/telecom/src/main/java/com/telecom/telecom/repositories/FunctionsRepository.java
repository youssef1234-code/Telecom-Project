package com.telecom.telecom.repositories;

import com.telecom.telecom.dtos.AccountPlanDto;
import com.telecom.telecom.dtos.ExclusiveOfferDto;
import com.telecom.telecom.dtos.PlanUsageSum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FunctionsRepository{

    @Query(value = "SELECT * FROM dbo.Account_Plan_date(:sub_date, :plan_id)", nativeQuery = true)
    List<AccountPlanDto> getAccountPlanDate(@Param("sub_date") LocalDate subscriptionDate, @Param("plan_id") Integer planId);

    @Query(value = "SELECT * FROM dbo.Account_SMS_Offers(:mobile_num)", nativeQuery = true)
    List<ExclusiveOfferDto> getAccountOfferedSMS(@Param("mobile_num") String mobileNum);

    @Query(value = "SELECT * FROM dbo.Account_Usage_Plan(:mobile_num,:start_date)", nativeQuery = true)
    List<PlanUsageSum> getPlanConsumptionsFromDate(@Param("mobile_num") String mobileNum, @Param("start_date") LocalDate startDate);


}
