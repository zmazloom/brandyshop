package com.brandyshop.domain.data;

import com.brandyshop.domain.request.CategoryRequest;
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
@Table(name = "BRANDYSHOP_CATEGORY")
public class Category {

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

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "REMOVED", columnDefinition = "boolean default false")
    private boolean removed;


    public static Category from(CategoryRequest.CategoryCreate categoryRequest) {
        if (categoryRequest == null)
            return null;

        return ModelUtils.getModelMapper().map(categoryRequest, Category.class);
    }

}
