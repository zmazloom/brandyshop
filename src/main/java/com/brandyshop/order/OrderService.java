package com.brandyshop.order;

import com.brandyshop.domain.data.Order;
import com.brandyshop.domain.data.OrderProductQuantity;
import com.brandyshop.domain.data.Product;
import com.brandyshop.domain.request.OrderRequest;
import com.brandyshop.domain.request.ProductQuantityRequest;
import com.brandyshop.domain.srv.OrderSrv;
import com.brandyshop.domain.srv.common.ItemsWithTotal;
import com.brandyshop.domain.vo.common.Pagination;
import com.brandyshop.exception.InvalidRequestException;
import com.brandyshop.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderSrv.OrderUserGet createOrder(OrderRequest.OrderCreate orderRequest) {

        Set<OrderProductQuantity> productQuantities = new HashSet<>();

        Order order = Order.builder()
                .name(orderRequest.getName())
                .phoneNumber(orderRequest.getPhoneNumber())
                .build();

        if (orderRequest.getProductQuantities() != null) {
            List<Long> productIds = orderRequest.getProductQuantities().stream().map(ProductQuantityRequest::getProductId).collect(Collectors.toList());
            Set<Long> uniqueProductIds = new HashSet<>(productIds);

            if (productIds.size() != uniqueProductIds.size()) {
                throw InvalidRequestException.getInstance("محصول تکراری مجازی نیست.");
            }

            for (ProductQuantityRequest productQuantityRequest : orderRequest.getProductQuantities()) {

                if (productQuantityRequest.getProductId() != null) {

                    Product product = productService.getAndCheckProduct(productQuantityRequest.getProductId());

                    productQuantities.add(OrderProductQuantity.builder()
                            .quantity(productQuantityRequest.getQuantity())
                            .product(product)
                            .order(order)
                            .build());
                }
            }
        }

        order.setProductQuantities(productQuantities);

        orderRepository.saveAndFlush(order);

        return OrderSrv.OrderUserGet.from(order);
    }

    public ItemsWithTotal<OrderSrv.OrderAdminGet> getOrders(Pagination pagination) {
        List<Order> orders = orderRepository.getAllOrders();

        long totalSize = orders.size();

        int offset = pagination.getOffset();
        orders = orders.subList((Math.min(offset, orders.size())), (Math.min(offset + pagination.size(), orders.size())));

        return ItemsWithTotal.<OrderSrv.OrderAdminGet>builder()
                .items(OrderSrv.OrderAdminGet.from(orders))
                .total(totalSize)
                .build();
    }

}
