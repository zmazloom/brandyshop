package com.brandyshop.product;

import com.brandyshop.domain.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("from Product b where b.name = :productName and b.brand.id = :brandId and b.removed = false")
    Product getProductByNameAndBrand(String productName, long brandId);

    @Query("from Product b where b.name = :productName and b.brand.id = :brandId and b.id <> :ignoreProductId and b.removed = false")
    Product getProductByNameAndBrandAndIgnore(String productName, long brandId, long ignoreProductId);

    @Query("from Product b where b.id = :productId and b.removed = false")
    Product getProductById(long productId);

    @Query("from Product b where b.removed = false order by b.name, b.id desc")
    List<Product> getAllProducts();

}