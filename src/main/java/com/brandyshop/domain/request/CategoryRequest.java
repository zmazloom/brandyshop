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
public class CategoryRequest {

    @ApiModelProperty(value = "Category display name.")
    private String displayName;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("CategoryCreateRequest")
    @EqualsAndHashCode(callSuper = true)
    public static class CategoryCreate extends CategoryRequest {
        @NotBlank(message = "نام دسته بندی اجباری است.")
        @ApiModelProperty(value = "Category name. It must be unique.", required = true)
        private String name;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("CategoryUpdateRequest")
    @EqualsAndHashCode(callSuper = true)
    public static class CategoryUpdate extends CategoryRequest {
        @ApiModelProperty(value = "Category name.")
        private String name;
    }

}