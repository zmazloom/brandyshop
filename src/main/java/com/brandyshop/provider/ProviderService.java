package com.brandyshop.provider;

import com.brandyshop.domain.data.Provider;
import com.brandyshop.domain.request.ProviderRequest;
import com.brandyshop.domain.srv.ProviderSrv;
import com.brandyshop.domain.srv.common.ItemsWithTotal;
import com.brandyshop.domain.vo.common.Pagination;
import com.brandyshop.exception.InvalidRequestException;
import com.brandyshop.exception.ResourceNotFoundException;
import com.brandyshop.utils.ModelUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;

    public ProviderSrv createProvider(ProviderRequest.ProviderCreate providerRequest) {
        validateProviderCreate(providerRequest);

        Provider provider = Provider.from(providerRequest);

        providerRepository.saveAndFlush(provider);

        return ProviderSrv.from(provider);
    }

    public ProviderSrv updateProvider(Long providerId, ProviderRequest.ProviderUpdate providerRequest) {
        Provider provider = getAndCheckProvider(providerId);

        validateAndFillProviderUpdate(provider, providerRequest);

        providerRepository.saveAndFlush(provider);

        return ProviderSrv.from(provider);
    }

    public void deleteProvider(Long providerId) {
        Provider provider = getAndCheckProvider(providerId);

        provider.setRemoved(true);

        providerRepository.saveAndFlush(provider);
    }

    public ItemsWithTotal<ProviderSrv> getProviders(Pagination pagination) {
        List<Provider> providers = providerRepository.getAllProviders();

        long totalSize = providers.size();

        int offset = pagination.getOffset();
        providers = providers.subList((Math.min(offset, providers.size())), (Math.min(offset + pagination.size(), providers.size())));

        return ItemsWithTotal.<ProviderSrv>builder()
                .items(ProviderSrv.from(providers))
                .total(totalSize)
                .build();
    }

    public ProviderSrv getProvider(Long providerId) {
        Provider provider = getAndCheckProvider(providerId);

        return ProviderSrv.from(provider);
    }

    private void validateProviderCreate(ProviderRequest.ProviderCreate providerRequest) {
        if (providerRequest.getRate() != null &&
                (providerRequest.getRate() > 10 || providerRequest.getRate() < 0)) {
            throw InvalidRequestException.getInstance("امتیاز تامین کننده باید بین ۰ تا ۱۰ باشد.");
        }
    }

    private void validateAndFillProviderUpdate(Provider provider, ProviderRequest.ProviderUpdate providerRequest) {
        if (ModelUtils.isNotEmpty(providerRequest.getName())) {
            provider.setName(providerRequest.getName());
        }

        if (providerRequest.getRate() != null) {
            if (providerRequest.getRate() > 10 || providerRequest.getRate() < 0) {
                throw InvalidRequestException.getInstance("امتیاز تامین کننده باید بین ۰ تا ۱۰ باشد.");
            }

            provider.setRate(providerRequest.getRate());
        }

        if (providerRequest.getDescription() != null) {
            provider.setDescription(providerRequest.getDescription());
        }

        if (providerRequest.getLink() != null) {
            provider.setLink(providerRequest.getLink());
        }
    }

    public Provider getAndCheckProvider(long providerId) {
        Provider provider = providerRepository.getProviderById(providerId);

        if (provider == null)
            throw ResourceNotFoundException.getInstance("تامین کننده با شناسه " + providerId + " پیدا نشد.");

        return provider;
    }

}
