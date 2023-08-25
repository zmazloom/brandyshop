package com.brandyshop.order;

import com.brandyshop.domain.request.OrderRequest;
import com.brandyshop.domain.srv.OrderSrv;
import com.brandyshop.domain.srv.response.ResFact;
import com.brandyshop.domain.srv.response.Result;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@CrossOrigin("*")
@RestController
@RequestMapping("/orders")
@Api(tags = {"Order"})
@SwaggerDefinition(tags = {
        @Tag(name = "Order", description = "Order APIs")
})
@Slf4j
@RequiredArgsConstructor
public class OrderUserController {

    private final OrderService orderService;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create order.", httpMethod = "POST")
    public ResponseEntity<Result<OrderSrv.OrderUserGet>> createOrder(
            @ApiParam("orderRequest")
            @RequestBody @NotNull @Validated OrderRequest.OrderCreate orderRequest
    ) {

        return ResponseEntity.ok(ResFact.<OrderSrv.OrderUserGet>build()
                .setMessage("OK")
                .setResult(orderService.createOrder(orderRequest))
                .setTotal(1)
                .get());
    }

}
