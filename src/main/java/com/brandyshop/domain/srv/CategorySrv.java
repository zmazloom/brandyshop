package com.brandyshop.domain.srv;

import com.brandyshop.domain.data.Category;
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
public class CategorySrv {

    private Long id;
    private Long created;
    private Long updated;
    private String name;
    private String displayName;

    public static List<CategorySrv> from(List<Category> categories) {
        if (categories == null)
            return new ArrayList<>();

        return categories.stream()
                .filter(Objects::nonNull)
                .map(CategorySrv::from)
                .collect(Collectors.toList());
    }

    public static CategorySrv from(Category category) {
        if (category == null)
            return null;

        CategorySrv categorySrv = ModelUtils.getModelMapper().map(category, CategorySrv.class);

        if (ModelUtils.isEmpty(category.getDisplayName())) {
            categorySrv.setDisplayName(category.getName());
        }

        return categorySrv;
    }

}
