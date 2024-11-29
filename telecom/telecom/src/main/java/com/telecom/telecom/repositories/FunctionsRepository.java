package com.telecom.telecom.repositories;

import com.telecom.telecom.dtos.*;
import com.telecom.telecom.dtos.projection.AccountPlanOnDateProjection;
import com.telecom.telecom.dtos.projection.PlanUsageSumProjection;
import com.telecom.telecom.entities.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FunctionsRepository extends JpaRepository<CustomerAccount, Long> {

    @Query(value = "SELECT * FROM dbo.Account_Plan_date(:sub_date, :plan_id)", nativeQuery = true)
    List<AccountPlanOnDateProjection> getAccountPlanDate(@Param("sub_date") LocalDate subscriptionDate, @Param("plan_id") Integer planId);

    @Query(value = "SELECT * FROM dbo.Account_SMS_Offers(:mobile_num)", nativeQuery = true)
    List<ExclusiveOfferDto> getAccountOfferedSMS(@Param("mobile_num") String mobileNum);

    @Query(value = "SELECT * FROM dbo.Account_Usage_Plan(:mobile_num,:start_date)", nativeQuery = true)
    List<PlanUsageSumProjection> getPlanConsumptionsFromDate(@Param("mobile_num") String mobileNum, @Param("start_date") LocalDate startDate);

    @Query(value = "SELECT dbo.AccountLoginValidation(:mobile_num, :pass)", nativeQuery = true)
    Boolean validateLogin(@Param("mobile_num")String mobileNum, @Param("pass")String password);

    @Query(value = "SELECT dbo.Wallet_Cashback_Amount(:walletID, :planID)", nativeQuery = true)
    Integer getWalletCashbackAmount(@Param("walletID") Integer walletID, @Param("planID") Integer planID);

    @Query(value = "SELECT dbo.Wallet_Transfer_Amount(:walletID, :startDate, :endDate)", nativeQuery = true)
    BigDecimal getWalletTransferAmount(@Param("walletID") Integer walletID, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT dbo.Wallet_MobileNo(:mobile_num)", nativeQuery = true)
    Boolean getMobileNo(@Param("mobile_num") String mobileNum);

    @Query(value = "SELECT * FROM dbo.Consumption(:Plan_name, :start_date, :end_date)", nativeQuery = true)
    List<ConsumptionDto> getConsumption(@Param("Plan_name") String planName, @Param("start_date") LocalDate startDate, @Param("end_date") LocalDate endDate);

    @Query(value = "SELECT * FROM dbo.Usage_Plan_CurrentMonth(:mobile_no)", nativeQuery = true)
    List<UsagePlanCurrentMonthDto> getUsagePlanCurrentMonth(@Param("mobile_no") String mobileNo);

    @Query(value = "SELECT amount FROM dbo.Cashback_Wallet_Customer(:nID)", nativeQuery = true)
    Integer getCashbackWalletCustomer(@Param("nID") Integer nID);

    @Query(value = "SELECT dbo.Remaining_plan_amount(:mobile_no, :plan_name)", nativeQuery = true)
    Integer getRemainingPlanAmount(@Param("mobile_no") String mobileNo, @Param("plan_name") String planName);

    @Query(value = " SELECT dbo.Extra_plan_amount(:mobile_no, :plan_name)", nativeQuery = true)
    Integer getExtraPlanAmount(@Param("mobile_no") String mobileNo, @Param("plan_name") String planName);

    @Query(value = "SELECT * FROM dbo.Subscribed_plans_5_Months(:mobile_no)", nativeQuery = true)
    List<SubscribedPlans5MonthsDto> getSubscribedPlans5Months(@Param("mobile_no") String mobileNo);



}
