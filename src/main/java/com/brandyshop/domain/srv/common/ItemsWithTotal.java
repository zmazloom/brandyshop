package com.brandyshop.domain.srv.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemsWithTotal<T> {
    @Builder.Default
    private List<T> items = new ArrayList<>();
    @Builder.Default
    private Long total = 0L;
}
