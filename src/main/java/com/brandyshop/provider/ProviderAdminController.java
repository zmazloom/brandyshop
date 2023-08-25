package com.brandyshop.provider;

import com.brandyshop.domain.request.ProviderRequest;
import com.brandyshop.domain.srv.ProviderSrv;
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
@RequestMapping("/admin/providers")
@Api(tags = {"Provider"})
@SwaggerDefinition(tags = {
        @Tag(name = "Provider", description = "Provider APIs")
})
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN')")
public class ProviderAdminController {

    private final ProviderService providerService;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create provider.", httpMethod = "POST")
    public ResponseEntity<Result<ProviderSrv>> createProvider(
            @ApiParam("providerRequest")
            @RequestBody @NotNull @Validated ProviderRequest.ProviderCreate providerRequest
    ) {

        return ResponseEntity.ok(ResFact.<ProviderSrv>build()
                .setMessage("تامین کننده با موفقیت اضافه شد.")
                .setResult(providerService.createProvider(providerRequest))
                .setTotal(1)
                .get());
    }

    @PatchMapping(value = "/{providerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update provider.", httpMethod = "PATCH")
    public ResponseEntity<Result<ProviderSrv>> updateProvider(
            @ApiParam("providerId")
            @PathVariable(name = "providerId") @NotNull Long providerId,
            @ApiParam("providerRequest")
            @RequestBody @NotNull @Validated ProviderRequest.ProviderUpdate providerRequest
    ) {

        return ResponseEntity.ok(ResFact.<ProviderSrv>build()
                .setMessage("تامین کننده با موفقیت به روز رسانی شد.")
                .setResult(providerService.updateProvider(providerId, providerRequest))
                .setTotal(1)
                .get());
    }

    @DeleteMapping(value = "/{providerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete provider.", httpMethod = "DELETE")
    public ResponseEntity<Result<Boolean>> deleteProvider(
            @ApiParam("providerId")
            @PathVariable(name = "providerId") @NotNull Long providerId
    ) {

        providerService.deleteProvider(providerId);

        return ResponseEntity.ok(ResFact.<Boolean>build()
                .setMessage("تامین کننده با موفقیت حذف شد.")
                .setResult(true)
                .setTotal(1)
                .get());
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get providers.", httpMethod = "GET")
    public ResponseEntity<Result<List<ProviderSrv>>> getProviders(
            @ApiParam("page")
            @RequestParam(value = "page", required = false) Integer page,
            @ApiParam("size")
            @RequestParam(value = "size", required = false) Integer size
    ) {

        return ResponseEntity.ok(ResFact.<List<ProviderSrv>>build()
                .setMessage("OK")
                .setItemsWithTotal(providerService.getProviders(Pagination.of(page, size)))
                .get());
    }

    @GetMapping(value = "/{providerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get provider by id.", httpMethod = "GET")
    public ResponseEntity<Result<ProviderSrv>> getProvider(
            @ApiParam("providerId")
            @PathVariable(name = "providerId") @NotNull Long providerId
    ) {

        return ResponseEntity.ok(ResFact.<ProviderSrv>build()
                .setMessage("OK")
                .setResult(providerService.getProvider(providerId))
                .setTotal(1)
                .get());
    }

}
