package com.brandyshop.domain.data;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BRANDYSHOP_ORDER")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CREATED")
    @CreationTimestamp
    private Date created;

    @Column(name = "UPDATED")
    @UpdateTimestamp
    private Date updated;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Cascade({org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.MERGE})
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", orphanRemoval = true)
    @Builder.Default
    private Set<OrderProductQuantity> productQuantities = new HashSet<>();

}
