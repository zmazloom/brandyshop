package com.brandyshop.product;

import com.brandyshop.brand.BrandService;
import com.brandyshop.category.CategoryService;
import com.brandyshop.domain.data.Brand;
import com.brandyshop.domain.data.Category;
import com.brandyshop.domain.data.Product;
import com.brandyshop.domain.data.Provider;
import com.brandyshop.domain.request.ProductRequest;
import com.brandyshop.domain.srv.ProductSrv;
import com.brandyshop.domain.srv.common.ItemsWithTotal;
import com.brandyshop.domain.vo.common.Pagination;
import com.brandyshop.exception.InvalidRequestException;
import com.brandyshop.exception.ResourceConflictException;
import com.brandyshop.exception.ResourceNotFoundException;
import com.brandyshop.provider.ProviderService;
import com.brandyshop.utils.ModelUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class ProductService {

    private final BrandService brandService;
    private final ProviderService providerService;
    private final CategoryService categoryService;
    private final ProductRepository productRepository;

    public List<ProductSrv.ProductAdminGet> createProducts(@Valid Set<ProductRequest.ProductCreate> productRequests) {

        if (productRequests == null || productRequests.isEmpty()) {
            throw InvalidRequestException.getInstance("حداقل یک محصول وارد کنید.");
        }

        List<Product> products = validateProductsCreateAndGetProducts(productRequests);

        productRepository.saveAll(products);

        return ProductSrv.ProductAdminGet.from(products);
    }

    public List<ProductSrv.ProductAdminGet> updateProducts(@Valid Set<ProductRequest.ProductUpdate> productRequests) {

        if (productRequests == null || productRequests.isEmpty()) {
            throw InvalidRequestException.getInstance("حداقل یک محصول وارد کنید.");
        }

        List<Product> products = validateProductsUpdateAndGetProducts(productRequests);

        productRepository.saveAll(products);

        return ProductSrv.ProductAdminGet.from(products);
    }

    public void deleteProducts(Set<Long> productIds) {

        if (productIds == null || productIds.isEmpty()) {
            throw InvalidRequestException.getInstance("شناسه محصولات را وارد کنید.");
        }

        for (Long productId : productIds) {
            Product product = productRepository.getProductById(productId);

            if (product != null) {
                product.setRemoved(true);

                productRepository.saveAndFlush(product);
            }
        }
    }

    public ItemsWithTotal<ProductSrv.ProductUserGet> getProductsByUser(Pagination pagination) {
        List<Product> products = productRepository.getAllProducts();

        long totalSize = products.size();

        int offset = pagination.getOffset();
        products = products.subList((Math.min(offset, products.size())), (Math.min(offset + pagination.size(), products.size())));

        return ItemsWithTotal.<ProductSrv.ProductUserGet>builder()
                .items(ProductSrv.ProductUserGet.from(products))
                .total(totalSize)
                .build();
    }

    public ItemsWithTotal<ProductSrv.ProductAdminGet> getProductsByAdmin(Pagination pagination) {
        List<Product> products = productRepository.getAllProducts();

        long totalSize = products.size();

        int offset = pagination.getOffset();
        products = products.subList((Math.min(offset, products.size())), (Math.min(offset + pagination.size(), products.size())));

        return ItemsWithTotal.<ProductSrv.ProductAdminGet>builder()
                .items(ProductSrv.ProductAdminGet.from(products))
                .total(totalSize)
                .build();
    }

    public ProductSrv.ProductAdminGet getProductByAdmin(Long productId) {
        Product product = getAndCheckProduct(productId);

        return ProductSrv.ProductAdminGet.from(product);
    }

    private List<Product> validateProductsCreateAndGetProducts(Set<ProductRequest.ProductCreate> productRequests) {

        List<Product> products = new ArrayList<>();

        List<String> nameAndBrands = productRequests.stream().map(productRequest -> productRequest.getName() + "#" + productRequest.getBrandId()).collect(Collectors.toList());
        Set<String> uniqueNameAndBrands = new HashSet<>(nameAndBrands);
        if (nameAndBrands.size() != uniqueNameAndBrands.size()) {
            throw ResourceConflictException.getInstance("ترکیب نام و برند باید یکتا باشد.");
        }

        for (ProductRequest.ProductCreate productRequest : productRequests) {

            if (productRequest.getPrice() < 0) {
                throw InvalidRequestException.getInstance("هزینه محصول باید مقدار مثبت باشد.");
            }

            if (productRequest.getDiscountPrice() != null) {
                if (productRequest.getDiscountPrice() < 0) {
                    throw InvalidRequestException.getInstance("تخفیف محصول باید مقدار مثبت باشد.");
                }

                if (productRequest.getDiscountPrice() > productRequest.getPrice()) {
                    throw InvalidRequestException.getInstance("تخفیف محصول باید از هزینه آن کمتر باشد.");
                }
            }

            if (productRequest.getStock() < 0) {
                throw InvalidRequestException.getInstance("موجودی محصول باید مقدار مثبت باشد.");
            }

            if (productRepository.getProductByNameAndBrand(productRequest.getName(), productRequest.getBrandId()) != null) {
                throw ResourceConflictException.getInstance("نام " + productRequest.getName() + " و برند با شناسه " + productRequest.getBrandId() + " یکتا نیست.");
            }

            Category category = categoryService.getAndCheckCategory(productRequest.getCategoryId());

            Provider provider = providerService.getAndCheckProvider(productRequest.getProviderId());

            Brand brand = brandService.getAndCheckBrand(productRequest.getBrandId());

            products.add(Product.from(productRequest, category, provider, brand));

        }

        return products;

    }

    private List<Product> validateProductsUpdateAndGetProducts(Set<ProductRequest.ProductUpdate> productRequests) {

        List<Product> products = new ArrayList<>();

        for (ProductRequest.ProductUpdate productRequest : productRequests) {

            Product product = getAndCheckProduct(productRequest.getId());

            if (ModelUtils.isNotEmpty(productRequest.getName())) {
                product.setName(productRequest.getName());
            }

            if (productRequest.getPrice() != null) {
                if (productRequest.getPrice() < 0) {
                    throw InvalidRequestException.getInstance("هزینه محصول باید مقدار مثبت باشد.");
                }
                product.setPrice(productRequest.getPrice());
            }

            if (productRequest.getStock() != null) {
                if (productRequest.getStock() < 0) {
                    throw InvalidRequestException.getInstance("موجودی محصول باید مقدار مثبت باشد.");
                }
                product.setStock(productRequest.getStock());
            }

            if (productRequest.getDiscountPrice() != null) {
                if (productRequest.getDiscountPrice() < 0) {
                    throw InvalidRequestException.getInstance("تخفیف محصول باید مقدار مثبت باشد.");
                }
                product.setDiscountPrice(productRequest.getDiscountPrice());
            }

            if (productRequest.getImageUrl() != null) {
                product.setImageUrl(productRequest.getImageUrl());

            }

            if (productRequest.getCategoryId() != null) {
                Category category = categoryService.getAndCheckCategory(productRequest.getCategoryId());
                product.setCategory(category);
            }

            if (productRequest.getProviderId() != null) {
                Provider provider = providerService.getAndCheckProvider(productRequest.getProviderId());
                product.setProvider(provider);
            }

            if (productRequest.getBrandId() != null) {
                Brand brand = brandService.getAndCheckBrand(productRequest.getBrandId());
                product.setBrand(brand);
            }

            if (product.getDiscountPrice() != null && product.getDiscountPrice() > product.getPrice()) {
                throw InvalidRequestException.getInstance("تخفیف محصول باید از هزینه آن کمتر باشد.");
            }

            if (productRepository.getProductByNameAndBrandAndIgnore(product.getName(), product.getBrand().getId(), product.getId()) != null) {
                throw ResourceConflictException.getInstance("نام " + productRequest.getName() + " و برند با شناسه " + productRequest.getBrandId() + " یکتا نیست.");
            }

            products.add(product);

        }

        List<String> nameAndBrands = products.stream().map(product -> product.getName() + "#" + product.getBrand().getId()).collect(Collectors.toList());
        Set<String> uniqueNameAndBrands = new HashSet<>(nameAndBrands);
        if (nameAndBrands.size() != uniqueNameAndBrands.size()) {
            throw ResourceConflictException.getInstance("ترکیب نام و برند باید یکتا باشد.");
        }

        return products;

    }

    public Product getAndCheckProduct(long productId) {
        Product product = productRepository.getProductById(productId);

        if (product == null)
            throw ResourceNotFoundException.getInstance("محصول با شناسه " + productId + " پیدا نشد.");

        return product;
    }

}
