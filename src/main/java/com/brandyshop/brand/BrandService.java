package com.brandyshop.brand;

import com.brandyshop.domain.data.Brand;
import com.brandyshop.domain.request.BrandRequest;
import com.brandyshop.domain.srv.BrandSrv;
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
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandSrv createBrand(BrandRequest.BrandCreate brandBrandCreateRequest) {
        validateBrandCreate(brandBrandCreateRequest);

        Brand brand = Brand.from(brandBrandCreateRequest);

        brandRepository.saveAndFlush(brand);

        return BrandSrv.from(brand);
    }

    public BrandSrv updateBrand(Long brandId, BrandRequest.BrandUpdate brandBrandUpdateRequest) {
        Brand brand = getAndCheckBrand(brandId);

        validateAndFillBrandUpdate(brand, brandBrandUpdateRequest);

        brandRepository.saveAndFlush(brand);

        return BrandSrv.from(brand);
    }

    public void deleteBrand(Long brandId) {
        Brand brand = getAndCheckBrand(brandId);

        brand.setRemoved(true);

        brandRepository.saveAndFlush(brand);
    }

    public ItemsWithTotal<BrandSrv> getBrands(Pagination pagination) {
        List<Brand> brands = brandRepository.getAllBrands();

        long totalSize = brands.size();

        int offset = pagination.getOffset();
        brands = brands.subList((Math.min(offset, brands.size())), (Math.min(offset + pagination.size(), brands.size())));

        return ItemsWithTotal.<BrandSrv>builder()
                .items(BrandSrv.from(brands))
                .total(totalSize)
                .build();
    }

    public BrandSrv getBrand(Long brandId) {
        Brand brand = getAndCheckBrand(brandId);

        return BrandSrv.from(brand);
    }

    private void validateBrandCreate(BrandRequest.BrandCreate brandRequest) {
        if (brandRepository.getBrandByName(brandRequest.getName()) != null)
            throw ResourceConflictException.getInstance("نام برند تکراری است.");
    }

    private void validateAndFillBrandUpdate(Brand brand, BrandRequest.BrandUpdate brandRequest) {
        if (ModelUtils.isNotEmpty(brandRequest.getName())) {
            if (brandRepository.getBrandByName(brand.getId(), brandRequest.getName()) != null)
                throw ResourceConflictException.getInstance("نام برند تکراری است.");

            brand.setName(brandRequest.getName());
        }

        if (brandRequest.getDisplayName() != null) {
            brand.setDisplayName(brandRequest.getDisplayName());
        }

        if (brandRequest.getLogo() != null) {
            brand.setLogo(brandRequest.getLogo());
        }
    }

    public Brand getAndCheckBrand(long brandId) {
        Brand brand = brandRepository.getBrandById(brandId);

        if (brand == null)
            throw ResourceNotFoundException.getInstance("برند پیدا نشد.");

        return brand;
    }

}
