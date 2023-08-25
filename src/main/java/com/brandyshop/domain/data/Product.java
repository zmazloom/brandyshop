package com.brandyshop.domain.data;

import com.brandyshop.domain.request.ProductRequest;
import com.brandyshop.utils.ModelUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BRANDYSHOP_PRODUCT")
public class Product {

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

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private Long price;

    @Column(name = "DISCOUNT")
    private Long discountPrice;

    @Column(name = "STOCK", nullable = false)
    private Integer stock;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROVIDER_ID", nullable = false)
    private Provider provider;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BRAND_ID", nullable = false)
    private Brand brand;

    @Column(name = "REMOVED", columnDefinition = "boolean default false")
    private boolean removed;

    public static Product from(ProductRequest.ProductCreate productRequest,
                               Category category,
                               Provider provider,
                               Brand brand) {
        if (productRequest == null)
            return null;

        Product product = ModelUtils.getModelMapper().map(productRequest, Product.class);

        product.setCategory(category);
        product.setProvider(provider);
        product.setBrand(brand);

        return product;
    }

}
