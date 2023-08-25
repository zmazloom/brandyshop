package com.brandyshop.category;

import com.brandyshop.domain.data.Category;
import com.brandyshop.domain.request.CategoryRequest;
import com.brandyshop.domain.srv.CategorySrv;
import com.brandyshop.domain.srv.common.ItemsWithTotal;
import com.brandyshop.domain.vo.common.Pagination;
import com.brandyshop.exception.ResourceConflictException;
import com.brandyshop.exception.ResourceNotFoundException;
import com.brandyshop.utils.ModelUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategorySrv createCategory(CategoryRequest.CategoryCreate categoryRequest) {
        validateCategoryCreate(categoryRequest);

        Category category = Category.from(categoryRequest);

        categoryRepository.saveAndFlush(category);

        return CategorySrv.from(category);
    }

    public CategorySrv updateCategory(Long categoryId, CategoryRequest.CategoryUpdate categoryRequest) {
        Category category = getAndCheckCategory(categoryId);

        validateAndFillCategoryUpdate(category, categoryRequest);

        categoryRepository.saveAndFlush(category);

        return CategorySrv.from(category);
    }

    public void deleteCategory(Long categoryId) {
        Category category = getAndCheckCategory(categoryId);

        category.setRemoved(true);

        categoryRepository.saveAndFlush(category);
    }

    public ItemsWithTotal<CategorySrv> getCategories(Pagination pagination) {
        List<Category> categories = categoryRepository.getAllCategories();

        long totalSize = categories.size();

        int offset = pagination.getOffset();
        categories = categories.subList((Math.min(offset, categories.size())), (Math.min(offset + pagination.size(), categories.size())));

        return ItemsWithTotal.<CategorySrv>builder()
                .items(CategorySrv.from(categories))
                .total(totalSize)
                .build();
    }

    public CategorySrv getCategory(Long categoryId) {
        Category category = getAndCheckCategory(categoryId);

        return CategorySrv.from(category);
    }

    private void validateCategoryCreate(CategoryRequest.CategoryCreate categoryRequest) {
        if (categoryRepository.getCategoryByName(categoryRequest.getName()) != null)
            throw ResourceConflictException.getInstance("نام دسته بندی تکراری است.");
    }

    private void validateAndFillCategoryUpdate(Category category, CategoryRequest.CategoryUpdate categoryRequest) {
        if (ModelUtils.isNotEmpty(categoryRequest.getName())) {
            if (categoryRepository.getCategoryByName(category.getId(), categoryRequest.getName()) != null)
                throw ResourceConflictException.getInstance("نام دسته بندی تکراری است.");

            category.setName(categoryRequest.getName());
        }

        if (categoryRequest.getDisplayName() != null) {
            category.setDisplayName(categoryRequest.getDisplayName());
        }
    }

    public Category getAndCheckCategory(long categoryId) {
        Category category = categoryRepository.getCategoryById(categoryId);

        if (category == null)
            throw ResourceNotFoundException.getInstance("دسته بندی با شناسه " + categoryId + " پیدا نشد.");

        return category;
    }

}
