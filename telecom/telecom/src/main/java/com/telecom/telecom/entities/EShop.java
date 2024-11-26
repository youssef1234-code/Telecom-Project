package com.telecom.telecom.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "E_shop", schema = "dbo")
public class EShop {
    @Id
    @Column(name = "shopID", nullable = false)
    private Integer shopID;  // Changed to 'shopID' to match the database column

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shopID", nullable = false)
    private Shop shop;  // Correct mapping to 'Shop' entity

    @Column(name = "URL", length = 50)
    private String url;

    @Column(name = "rating")
    private Integer rating;
}
