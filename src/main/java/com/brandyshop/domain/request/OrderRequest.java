package com.brandyshop.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @ApiModelProperty(value = "Customer name and last name.")
    private String name;
    @ApiModelProperty(value = "Customer phone number.")
    private String phoneNumber;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("OrderCreateRequest")
    @EqualsAndHashCode(callSuper = true)
    public static class OrderCreate extends OrderRequest {
        @ApiModelProperty(value = "List of product ids and quantity.")
        private Set<ProductQuantityRequest> productQuantities;
    }


}
