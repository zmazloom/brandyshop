package com.brandyshop.brand;

import com.brandyshop.domain.data.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("from Brand b where b.name = :name and b.removed = false")
    Brand getBrandByName(String name);

    @Query("from Brand b where b.name = :name and b.id <> :brandId and b.removed = false")
    Brand getBrandByName(Long brandId, String name);

    @Query("from Brand b where b.id = :brandId and b.removed = false")
    Brand getBrandById(long brandId);

    @Query("from Brand b where b.removed = false order by b.id desc")
    List<Brand> getAllBrands();

}