package com.brandyshop.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @ApiModelProperty(value = "Product discount price.")
    private Long discountPrice;

    @ApiModelProperty(value = "Product image url.")
    private String imageUrl;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("ProductCreateRequest")
    @EqualsAndHashCode(callSuper = true)
    public static class ProductCreate extends ProductRequest {
        @NotBlank(message = "نام محصول اجباری است.")
        @ApiModelProperty(value = "Product name. Combination of name and brand must be unique.", required = true)
        private String name;
        @NotNull(message = "هزینه محصول اجباری است.")
        @ApiModelProperty(value = "Product price.", required = true)
        private Long price;
        @NotNull(message = "موجودی محصول اجباری است.")
        @ApiModelProperty(value = "Product stock.", required = true)
        private Integer stock;
        @NotNull(message = "شناسه دسته بندی محصول اجباری است.")
        @ApiModelProperty(value = "Product category identifier.", required = true)
        private Long categoryId;
        @NotNull(message = "شناسه دسته بندی تامین کننده اجباری است.")
        @ApiModelProperty(value = "Product provider identifier.", required = true)
        private Long providerId;
        @NotNull(message = "شناسه دسته بندی برند اجباری است.")
        @ApiModelProperty(value = "Product brand identifier.", required = true)
        private Long brandId;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("ProductUpdateRequest")
    @EqualsAndHashCode(callSuper = true)
    public static class ProductUpdate extends ProductRequest {
        @NotNull(message = "شناسه محصول اجباری است.")
        @ApiModelProperty(value = "Product identifier.", required = true)
        private Long id;
        @ApiModelProperty(value = "Product name. Combination of name and brand must be unique.")
        private String name;
        @ApiModelProperty(value = "Product price.")
        private Long price;
        @ApiModelProperty(value = "Product stock.")
        private Integer stock;
        @ApiModelProperty(value = "Product category identifier.")
        private Long categoryId;
        @ApiModelProperty(value = "Product provider identifier.")
        private Long providerId;
        @ApiModelProperty(value = "Product brand identifier.")
        private Long brandId;
    }

}
