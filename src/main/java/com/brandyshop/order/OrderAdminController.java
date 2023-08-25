package com.brandyshop.order;

import com.brandyshop.domain.srv.OrderSrv;
import com.brandyshop.domain.srv.response.ResFact;
import com.brandyshop.domain.srv.response.Result;
import com.brandyshop.domain.vo.common.Pagination;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/orders")
@Api(tags = {"Order"})
@SwaggerDefinition(tags = {
        @Tag(name = "Order", description = "Order APIs")
})
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN')")
public class OrderAdminController {

    private final OrderService orderService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get orders.", httpMethod = "GET")
    public ResponseEntity<Result<List<OrderSrv.OrderAdminGet>>> getOrders(
            @ApiParam("page")
            @RequestParam(value = "page", required = false) Integer page,
            @ApiParam("size")
            @RequestParam(value = "size", required = false) Integer size
    ) {

        return ResponseEntity.ok(ResFact.<List<OrderSrv.OrderAdminGet>>build()
                .setMessage("OK")
                .setItemsWithTotal(orderService.getOrders(Pagination.of(page, size)))
                .get());
    }

}
