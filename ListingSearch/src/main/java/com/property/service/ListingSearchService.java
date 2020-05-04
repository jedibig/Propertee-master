package com.property.service;


import com.property.dto.ListingElastic;
import com.property.dto.SearchCriteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ListingSearchService {
     Optional<List<ListingElastic>> getListingWithCriteria(String keyword, SearchCriteria criteria, int offset, int limit);
}
