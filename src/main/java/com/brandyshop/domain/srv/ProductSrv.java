package com.brandyshop.domain.srv;

import com.brandyshop.domain.data.Product;
import com.brandyshop.utils.ModelUtils;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSrv {

    private Long id;
    private Long created;
    private Long updated;
    private String name;
    private Long price;
    private Long discountPrice;
    private Integer stock;
    private String imageUrl;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("ProductAdminGetSrv")
    @EqualsAndHashCode(callSuper = true)
    public static class ProductAdminGet extends ProductSrv {
        private CategorySrv category;
        private ProviderSrv provider;
        private BrandSrv brand;

        public static List<ProductAdminGet> from(List<Product> products) {
            if (products == null)
                return new ArrayList<>();

            return products.stream()
                    .filter(Objects::nonNull)
                    .map(ProductSrv.ProductAdminGet::from)
                    .collect(Collectors.toList());
        }

        public static ProductAdminGet from(Product product) {
            if (product == null)
                return null;

            ProductAdminGet productAdminGet = ModelUtils.getModelMapper().map(product, ProductAdminGet.class);

            productAdminGet.setCategory(CategorySrv.from(product.getCategory()));
            productAdminGet.setProvider(ProviderSrv.from(product.getProvider()));
            productAdminGet.setBrand(BrandSrv.from(product.getBrand()));

            return productAdminGet;
        }
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("ProductUserGetSrv")
    @EqualsAndHashCode(callSuper = true)
    public static class ProductUserGet extends ProductSrv {
        private String category;
        private Long provider;
        private String brand;

        public static List<ProductUserGet> from(List<Product> products) {
            if (products == null)
                return new ArrayList<>();

            return products.stream()
                    .filter(Objects::nonNull)
                    .map(ProductSrv.ProductUserGet::from)
                    .collect(Collectors.toList());
        }

        public static ProductUserGet from(Product product) {
            if (product == null)
                return null;

            return ProductUserGet.builder()
                    .id(product.getId())
                    .created(product.getCreated().getTime())
                    .updated(product.getUpdated().getTime())
                    .name(product.getName())
                    .price(product.getPrice())
                    .discountPrice(product.getDiscountPrice())
                    .stock(product.getStock())
                    .imageUrl(product.getImageUrl())
                    .category(product.getCategory().getName())
                    .provider(product.getProvider().getId())
                    .brand(product.getBrand().getName())
                    .build();
        }
    }

}
