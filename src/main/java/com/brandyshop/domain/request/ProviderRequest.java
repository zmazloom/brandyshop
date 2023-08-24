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
public class ProviderRequest {

    @ApiModelProperty(value = "Provider description.")
    private String description;

    @ApiModelProperty(value = "Provider link address.")
    private String link;

    @ApiModelProperty(value = "Provider rate between 0 to 10.")
    private Integer rate;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("ProviderCreateRequest")
    @EqualsAndHashCode(callSuper = true)
    public static class ProviderCreate extends ProviderRequest {
        @NotBlank(message = "نام تامین کننده اجباری است.")
        @ApiModelProperty(value = "Provider name.", required = true)
        private String name;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("ProviderUpdateRequest")
    @EqualsAndHashCode(callSuper = true)
    public static class ProviderUpdate extends ProviderRequest {
        @ApiModelProperty(value = "Provider name.")
        private String name;
    }

}