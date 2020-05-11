package com.property.elastic;

import com.property.dto.ListingElastic;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface ListingRepositoryElastic extends ElasticsearchRepository<ListingElastic, Long>, ListingRepositoryWithCriteria {

}
