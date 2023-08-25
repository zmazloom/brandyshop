package com.brandyshop.domain.srv;

import com.brandyshop.domain.data.OrderProductQuantity;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantitySrv {

    private Integer quantity;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("ProductQuantityAdminGetSrv")
    @EqualsAndHashCode(callSuper = true)
    public static class ProductQuantityAdminGet extends ProductQuantitySrv {

        private ProductSrv.ProductAdminGet product;

        public static List<ProductQuantityAdminGet> from(Set<OrderProductQuantity> productQuantities) {
            if (productQuantities == null)
                return new ArrayList<>();

            return productQuantities.stream()
                    .filter(Objects::nonNull)
                    .map(ProductQuantitySrv.ProductQuantityAdminGet::from)
                    .collect(Collectors.toList());
        }

        public static ProductQuantityAdminGet from(OrderProductQuantity productQuantity) {
            if (productQuantity == null)
                return null;

            return ProductQuantityAdminGet.builder()
                    .quantity(productQuantity.getQuantity())
                    .product(ProductSrv.ProductAdminGet.from(productQuantity.getProduct()))
                    .build();
        }
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("ProductQuantityUserGetSrv")
    @EqualsAndHashCode(callSuper = true)
    public static class ProductQuantityUserGet extends ProductQuantitySrv {

        private ProductSrv.ProductUserGet product;

        public static List<ProductQuantityUserGet> from(Set<OrderProductQuantity> productQuantities) {
            if (productQuantities == null)
                return new ArrayList<>();

            return productQuantities.stream()
                    .filter(Objects::nonNull)
                    .map(ProductQuantitySrv.ProductQuantityUserGet::from)
                    .collect(Collectors.toList());
        }

        public static ProductQuantityUserGet from(OrderProductQuantity productQuantity) {
            if (productQuantity == null)
                return null;

            return ProductQuantityUserGet.builder()
                    .quantity(productQuantity.getQuantity())
                    .product(ProductSrv.ProductUserGet.from(productQuantity.getProduct()))
                    .build();
        }
    }

}
