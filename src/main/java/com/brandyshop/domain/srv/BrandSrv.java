package com.brandyshop.domain.srv;

import com.brandyshop.domain.data.Brand;
import com.brandyshop.utils.ModelUtils;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandSrv {

    private Long id;
    private Long created;
    private Long updated;
    private String name;
    private String displayName;
    private String logo;

    public static List<BrandSrv> from(List<Brand> brands) {
        if (brands == null)
            return new ArrayList<>();

        return brands.stream()
                .filter(Objects::nonNull)
                .map(BrandSrv::from)
                .collect(Collectors.toList());
    }

    public static BrandSrv from(Brand brand) {
        if (brand == null)
            return null;

        BrandSrv brandSrv = ModelUtils.getModelMapper().map(brand, BrandSrv.class);

        if (ModelUtils.isEmpty(brand.getDisplayName())) {
            brandSrv.setDisplayName(brand.getName());
        }

        return brandSrv;
    }

}
