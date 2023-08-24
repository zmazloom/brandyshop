package com.brandyshop.brand;

import com.brandyshop.domain.request.BrandRequest;
import com.brandyshop.domain.srv.BrandSrv;
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

@CrossOrigin("*")
@RestController
@RequestMapping("/brands")
@Api(tags = {"Brand"})
@SwaggerDefinition(tags = {
        @Tag(name = "Brand", description = "Brand APIs")
})
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN')")
public class BrandController {

    private final BrandService brandService;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create brand.", httpMethod = "POST")
    public ResponseEntity<Result<BrandSrv>> createBrand(
            @ApiParam("brandRequest")
            @RequestBody @NotNull @Validated BrandRequest.BrandCreate brandRequest
    ) {

        return ResponseEntity.ok(ResFact.<BrandSrv>build()
                .setMessage("برند با موفقیت اضافه شد.")
                .setResult(brandService.createBrand(brandRequest))
                .setTotal(1)
                .get());
    }

    @PatchMapping(value = "/{brandId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update brand.", httpMethod = "PATCH")
    public ResponseEntity<Result<BrandSrv>> updateBrand(
            @ApiParam("brandId")
            @PathVariable(name = "brandId") @NotNull Long brandId,
            @ApiParam("brandRequest")
            @RequestBody @NotNull @Validated BrandRequest.BrandUpdate brandRequest
    ) {

        return ResponseEntity.ok(ResFact.<BrandSrv>build()
                .setMessage("برند با موفقیت به روز رسانی شد.")
                .setResult(brandService.updateBrand(brandId, brandRequest))
                .setTotal(1)
                .get());
    }

    @DeleteMapping(value = "/{brandId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete brand.", httpMethod = "DELETE")
    public ResponseEntity<Result<Boolean>> deleteBrand(
            @ApiParam("brandId")
            @PathVariable(name = "brandId") @NotNull Long brandId
    ) {

        brandService.deleteBrand(brandId);

        return ResponseEntity.ok(ResFact.<Boolean>build()
                .setMessage("برند با موفقیت حذف شد.")
                .setResult(true)
                .setTotal(1)
                .get());
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get brands.", httpMethod = "GET")
    public ResponseEntity<Result<List<BrandSrv>>> getBrands(
            @ApiParam("page")
            @RequestParam(value = "page", required = false) Integer page,
            @ApiParam("size")
            @RequestParam(value = "size", required = false) Integer size
    ) {

        return ResponseEntity.ok(ResFact.<List<BrandSrv>>build()
                .setMessage("OK")
                .setItemsWithTotal(brandService.getBrands(Pagination.of(page, size)))
                .get());
    }

    @GetMapping(value = "/{brandId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get brand by id.", httpMethod = "GET")
    public ResponseEntity<Result<BrandSrv>> getBrand(
            @ApiParam("brandId")
            @PathVariable(name = "brandId") @NotNull Long brandId
    ) {

        return ResponseEntity.ok(ResFact.<BrandSrv>build()
                .setMessage("OK")
                .setResult(brandService.getBrand(brandId))
                .setTotal(1)
                .get());
    }

}
