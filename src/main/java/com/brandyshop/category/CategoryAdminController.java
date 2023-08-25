package com.brandyshop.category;

import com.brandyshop.domain.request.CategoryRequest;
import com.brandyshop.domain.srv.CategorySrv;
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
@RequestMapping("/admin/categories")
@Api(tags = {"Category"})
@SwaggerDefinition(tags = {
        @Tag(name = "Category", description = "Category APIs")
})
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN')")
public class CategoryAdminController {

    private final CategoryService categoryService;

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create category.", httpMethod = "POST")
    public ResponseEntity<Result<CategorySrv>> createCategory(
            @ApiParam("categoryRequest")
            @RequestBody @NotNull @Validated CategoryRequest.CategoryCreate categoryRequest
    ) {

        return ResponseEntity.ok(ResFact.<CategorySrv>build()
                .setMessage("دسته بندی با موفقیت اضافه شد.")
                .setResult(categoryService.createCategory(categoryRequest))
                .setTotal(1)
                .get());
    }

    @PatchMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update category.", httpMethod = "PATCH")
    public ResponseEntity<Result<CategorySrv>> updateCategory(
            @ApiParam("categoryId")
            @PathVariable(name = "categoryId") @NotNull Long categoryId,
            @ApiParam("categoryRequest")
            @RequestBody @NotNull @Validated CategoryRequest.CategoryUpdate categoryRequest
    ) {

        return ResponseEntity.ok(ResFact.<CategorySrv>build()
                .setMessage("دسته بندی با موفقیت به روز رسانی شد.")
                .setResult(categoryService.updateCategory(categoryId, categoryRequest))
                .setTotal(1)
                .get());
    }

    @DeleteMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete category.", httpMethod = "DELETE")
    public ResponseEntity<Result<Boolean>> deleteCategory(
            @ApiParam("categoryId")
            @PathVariable(name = "categoryId") @NotNull Long categoryId
    ) {

        categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok(ResFact.<Boolean>build()
                .setMessage("دسته بندی با موفقیت حذف شد.")
                .setResult(true)
                .setTotal(1)
                .get());
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get categories.", httpMethod = "GET")
    public ResponseEntity<Result<List<CategorySrv>>> getCategories(
            @ApiParam("page")
            @RequestParam(value = "page", required = false) Integer page,
            @ApiParam("size")
            @RequestParam(value = "size", required = false) Integer size
    ) {

        return ResponseEntity.ok(ResFact.<List<CategorySrv>>build()
                .setMessage("OK")
                .setItemsWithTotal(categoryService.getCategories(Pagination.of(page, size)))
                .get());
    }

    @GetMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get category by id.", httpMethod = "GET")
    public ResponseEntity<Result<CategorySrv>> getCategory(
            @ApiParam("categoryId")
            @PathVariable(name = "categoryId") @NotNull Long categoryId
    ) {

        return ResponseEntity.ok(ResFact.<CategorySrv>build()
                .setMessage("OK")
                .setResult(categoryService.getCategory(categoryId))
                .setTotal(1)
                .get());
    }

}
