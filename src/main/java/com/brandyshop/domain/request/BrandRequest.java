package com.brandyshop.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequest {

    @ApiModelProperty(value = "Brand display name.")
    private String displayName;

    @ApiModelProperty(value = "Brand logo.")
    private String logo;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("BrandCreateRequest")
    @EqualsAndHashCode(callSuper = true)
    public static class BrandCreate extends BrandRequest {
        @NotBlank(message = "نام برند اجباری است.")
        @ApiModelProperty(value = "Brand name. It must be unique.", required = true)
        private String name;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("BrandUpdateRequest")
    @EqualsAndHashCode(callSuper = true)
    public static class BrandUpdate extends BrandRequest {
        @ApiModelProperty(value = "Brand name.")
        private String name;
    }

}