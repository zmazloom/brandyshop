package com.brandyshop.domain.srv;

import com.brandyshop.domain.data.Provider;
import com.brandyshop.utils.ModelUtils;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderSrv {

    private Long id;
    private Long created;
    private Long updated;
    private String name;
    private String description;
    private String link;
    private Integer rate;

    public static List<ProviderSrv> from(List<Provider> providers) {
        if (providers == null)
            return new ArrayList<>();

        return providers.stream()
                .filter(Objects::nonNull)
                .map(ProviderSrv::from)
                .collect(Collectors.toList());
    }

    public static ProviderSrv from(Provider provider) {
        if (provider == null)
            return null;

        return ModelUtils.getModelMapper().map(provider, ProviderSrv.class);
    }

}