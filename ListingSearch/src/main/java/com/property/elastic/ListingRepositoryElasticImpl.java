package com.property.elastic;

import com.property.dto.ListingElastic;
import com.property.dto.SearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@DependsOn("elasticsearchTemplate")
public class ListingRepositoryElasticImpl implements ListingRepositoryWithCriteria {

    ElasticsearchOperations template;

    @Override
    public Optional<List<ListingElastic>> getListingWithCriteria(String keyword, SearchCriteria criteria, int offset, int limit) {
        template.query(CriteriaBuilder.mapCriteria(keyword,criteria,offset,limit),new ListingResultExtractor());
        return Optional.empty();
    }

}
