package com.telecom.telecom.repositories;

import com.telecom.telecom.dtos.projection.PhysicalStoreVoucherProjection;
import com.telecom.telecom.dtos.projection.ProfileAccountProjection;
import com.telecom.telecom.dtos.projection.ResolvedTicketsProjection;
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

}
