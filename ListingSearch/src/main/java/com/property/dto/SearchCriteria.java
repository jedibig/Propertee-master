package com.property.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String list_for;
    private String property_type;
    private BigDecimal minBudget;
    private BigDecimal maxBudget;
    private Integer[] num_bedroom;
    private Date postedBy;
}
