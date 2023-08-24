package com.brandyshop.provider;

import com.brandyshop.domain.data.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    @Query("from Provider b where b.id = :providerId and b.removed = false")
    Provider getProviderById(long providerId);

    @Query("from Provider b where b.removed = false order by b.id desc")
    List<Provider> getAllProviders();

}