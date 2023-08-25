package com.brandyshop.product;

import com.brandyshop.domain.srv.ProductSrv;
import com.brandyshop.domain.srv.response.ResFact;
import com.brandyshop.domain.srv.response.Result;
import com.brandyshop.domain.vo.common.Pagination;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/products")
@Api(tags = {"Product"})
@SwaggerDefinition(tags = {
        @Tag(name = "Product", description = "Product APIs")
})
@Slf4j
@RequiredArgsConstructor
public class ProductUserController {

    private final ProductService productService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get products.", httpMethod = "GET")
    public ResponseEntity<Result<List<ProductSrv.ProductUserGet>>> getProducts(
            @ApiParam("page")
            @RequestParam(value = "page", required = false) Integer page,
            @ApiParam("size")
            @RequestParam(value = "size", required = false) Integer size
    ) {

        return ResponseEntity.ok(ResFact.<List<ProductSrv.ProductUserGet>>build()
                .setMessage("OK")
                .setItemsWithTotal(productService.getProductsByUser(Pagination.of(page, size)))
                .get());
    }

}
