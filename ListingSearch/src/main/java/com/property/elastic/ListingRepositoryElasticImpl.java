package com.property.elastic;

import com.property.dto.ListingElastic;
import com.property.dto.SearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ListingRepositoryElasticImpl implements ListingRepositoryWithCriteria {

    @Override
    public Optional<List<ListingElastic>> getListingWithCriteria(String keyword, SearchCriteria criteria, Pageable page) {

        return Optional.empty();
    }

}
