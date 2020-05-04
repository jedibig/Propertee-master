package com.property.elastic;

import com.property.dto.ListingElastic;
import com.property.dto.SearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ListingRepositoryWithCriteria {

    Optional<List<ListingElastic>> getListingWithCriteria(String keyword, SearchCriteria criteria, Pageable page);
}
