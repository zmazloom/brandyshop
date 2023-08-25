package com.brandyshop.domain.srv;

import com.brandyshop.domain.data.Order;
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
public class OrderSrv {

    private Long id;
    private Long created;
    private Long updated;
    private String name;
    private String phoneNumber;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("OrderAdminGetSrv")
    @EqualsAndHashCode(callSuper = true)
    public static class OrderAdminGet extends OrderSrv {

        private List<ProductQuantitySrv.ProductQuantityAdminGet> productQuantities;

        public static List<OrderAdminGet> from(List<Order> orders) {
            if (orders == null)
                return new ArrayList<>();

            return orders.stream()
                    .filter(Objects::nonNull)
                    .map(OrderSrv.OrderAdminGet::from)
                    .collect(Collectors.toList());
        }

        public static OrderAdminGet from(Order order) {
            if (order == null)
                return null;

            return OrderAdminGet.builder()
                    .id(order.getId())
                    .created(order.getCreated().getTime())
                    .updated(order.getUpdated().getTime())
                    .name(order.getName())
                    .phoneNumber(order.getPhoneNumber())
                    .productQuantities(ProductQuantitySrv.ProductQuantityAdminGet.from(order.getProductQuantities()))
                    .build();
        }
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel("OrderUserGetSrv")
    @EqualsAndHashCode(callSuper = true)
    public static class OrderUserGet extends OrderSrv {

        private List<ProductQuantitySrv.ProductQuantityUserGet> productQuantities;

        public static List<OrderUserGet> from(List<Order> orders) {
            if (orders == null)
                return new ArrayList<>();

            return orders.stream()
                    .filter(Objects::nonNull)
                    .map(OrderSrv.OrderUserGet::from)
                    .collect(Collectors.toList());
        }

        public static OrderUserGet from(Order order) {
            if (order == null)
                return null;

            return OrderUserGet.builder()
                    .id(order.getId())
                    .created(order.getCreated().getTime())
                    .updated(order.getUpdated().getTime())
                    .name(order.getName())
                    .phoneNumber(order.getPhoneNumber())
                    .productQuantities(ProductQuantitySrv.ProductQuantityUserGet.from(order.getProductQuantities()))
                    .build();
        }
    }

}
