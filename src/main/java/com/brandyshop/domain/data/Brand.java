package com.brandyshop.domain.data;

import com.brandyshop.domain.request.BrandRequest;
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
@Table(name = "BRANDYSHOP_BRAND")
public class Brand {

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

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "LOGO")
    private String logo;

    @Column(name = "REMOVED", columnDefinition = "boolean default false")
    private boolean removed;


    public static Brand from(BrandRequest.BrandCreate brandBrandCreateRequest) {
        if (brandBrandCreateRequest == null)
            return null;

        return ModelUtils.getModelMapper().map(brandBrandCreateRequest, Brand.class);
    }

}
