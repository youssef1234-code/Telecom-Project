package com.telecom.telecom.repositories;

import com.telecom.telecom.dtos.projection.*;
import com.telecom.telecom.entities.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ViewsRepository extends JpaRepository<CustomerAccount, Long> {

    @Query(value = "SELECT * FROM dbo.allCustomerAccounts",nativeQuery = true)
    List<ProfileAccountProjection> getAllProfileAccounts();

    @Query(value = "SELECT * FROM dbo.PhysicalStoreVouchers",nativeQuery = true)
    List<PhysicalStoreVoucherProjection> getStoreVouchers();

    @Query(value = "SELECT * FROM dbo.allResolvedTickets",nativeQuery = true)
    List<ResolvedTicketsProjection> getResolvedTickets();

    @Query(value = "SELECT * FROM dbo.CustomerWallet", nativeQuery = true)
    List<CustomerWalletProjection> getCustomerWallet();

    @Query(value = "SELECT * FROM dbo.E_shopVouchers", nativeQuery = true)
    List<E_shopVouchersProjection> getE_shopVouchers();

    @Query(value = "SELECT * FROM dbo.AccountPayments", nativeQuery = true)
    List<AccountPaymentsProjection> getAccountPayments();

    @Query(value = "SELECT * FROM dbo.Service_plan", nativeQuery = true)
    List<ServicePlanProjection> getServicePlans();


    @Query(value = "Select * FROM dbo.allBenefits", nativeQuery = true)
    List<AllBenefitProjection> getAllBenefits();

    @Query(value = "SELECT * FROM dbo.Num_of_cashback", nativeQuery = true)
    List<NumOfCashbackProjection> getNumOfCashback();


}
