package com.property.service;

import com.property.dto.ListingElastic;
import com.property.elastic.CriteriaBuilder;
import com.property.elastic.ListingRepositoryElastic;
import com.property.dto.SearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ListingSearchServiceImpl implements ListingSearchService {

    final ListingRepositoryElastic repository;

    @Override
    public Optional<List<ListingElastic>> getListingWithCriteria(String keyword, SearchCriteria criteria, int offset, int limit) {
        return repository.getListingWithCriteria(keyword, criteria, offset, limit);
    }
}
