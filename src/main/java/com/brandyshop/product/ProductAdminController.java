package com.brandyshop.product;

import com.brandyshop.domain.request.ProductRequest;
import com.brandyshop.domain.srv.ProductSrv;
import com.brandyshop.domain.srv.response.ResFact;
import com.brandyshop.domain.srv.response.Result;
import com.brandyshop.domain.vo.common.Pagination;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/products")
@Api(tags = {"Product"})
@SwaggerDefinition(tags = {
        @Tag(name = "Product", description = "Product APIs")
})
@Slf4j
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductService productService;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create products.", httpMethod = "POST")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Result<List<ProductSrv.ProductAdminGet>>> createProducts(
            @ApiParam("productRequests")
            @RequestBody @NotNull @Validated Set<ProductRequest.ProductCreate> productRequests
    ) {

        return ResponseEntity.ok(ResFact.<List<ProductSrv.ProductAdminGet>>build()
                .setMessage("محصولات با موفقیت اضافه شدند.")
                .setResult(productService.createProducts(productRequests))
                .setTotal(productRequests.size())
                .get());
    }

    @PatchMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update products.", httpMethod = "PATCH")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Result<List<ProductSrv.ProductAdminGet>>> updateProducts(
            @ApiParam("productRequests")
            @RequestBody @NotNull @Validated Set<ProductRequest.ProductUpdate> productRequests
    ) {

        return ResponseEntity.ok(ResFact.<List<ProductSrv.ProductAdminGet>>build()
                .setMessage("محصولات با موفقیت به روز رسانی شدند.")
                .setResult(productService.updateProducts(productRequests))
                .setTotal(productRequests.size())
                .get());
    }

    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete products.", httpMethod = "DELETE")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Result<Boolean>> deleteProducts(
            @ApiParam("productIds")
            @RequestParam @NotNull Set<Long> productIds
    ) {

        productService.deleteProducts(productIds);

        return ResponseEntity.ok(ResFact.<Boolean>build()
                .setMessage("محصولات با موفقیت حذف شدند.")
                .setResult(Boolean.TRUE)
                .setTotal(productIds.size())
                .get());
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get products.", httpMethod = "GET")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Result<List<ProductSrv.ProductAdminGet>>> getProducts(
            @ApiParam("page")
            @RequestParam(value = "page", required = false) Integer page,
            @ApiParam("size")
            @RequestParam(value = "size", required = false) Integer size
    ) {

        return ResponseEntity.ok(ResFact.<List<ProductSrv.ProductAdminGet>>build()
                .setMessage("OK")
                .setItemsWithTotal(productService.getProductsByAdmin(Pagination.of(page, size)))
                .get());
    }

    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get product by id.", httpMethod = "GET")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Result<ProductSrv.ProductAdminGet>> getProduct(
            @ApiParam("productId")
            @PathVariable(name = "productId") @NotNull Long productId
    ) {

        return ResponseEntity.ok(ResFact.<ProductSrv.ProductAdminGet>build()
                .setMessage("OK")
                .setResult(productService.getProductByAdmin(productId))
                .setTotal(1)
                .get());
    }


}
