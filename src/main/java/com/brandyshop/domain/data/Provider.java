package com.brandyshop.domain.data;

import com.brandyshop.domain.request.ProviderRequest;
import com.brandyshop.utils.ModelUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BRANDYSHOP_PROVIDER")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CREATED")
    @CreationTimestamp
    private Date created;

    @Column(name = "UPDATED")
    @UpdateTimestamp
    private Date updated;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "LINK")
    private String link;

    @Column(name = "RATE")
    private Integer rate;

    @Column(name = "REMOVED", columnDefinition = "boolean default false")
    private boolean removed;


    public static Provider from(ProviderRequest.ProviderCreate providerRequest) {
        if (providerRequest == null)
            return null;

        return ModelUtils.getModelMapper().map(providerRequest, Provider.class);
    }

}
