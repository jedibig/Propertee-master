package com.property.elastic;

import com.property.dto.SearchCriteria;
import com.property.exception.InvalidModelInputException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CriteriaBuilder  {

    private static QueryBuilder between(String fieldName, Object minVal, Object maxVal){
        return QueryBuilders.rangeQuery(fieldName).gte(minVal).lte(maxVal);
    }

    private static QueryBuilder equalTo(String fieldName, Number minVal, Number maxVal){
        return QueryBuilders.rangeQuery(fieldName).gte(minVal).lte(maxVal);
    }

    private static QueryBuilder matchExactly(String fieldName, String value){
        return QueryBuilders.termQuery(fieldName, value);
    }

    private static QueryBuilder match(String fieldName, String value){
        return QueryBuilders.matchQuery(fieldName, value);
    }

    private static QueryBuilder matchAll(String value, String... fieldNames){
        return QueryBuilders.multiMatchQuery(value, fieldNames);
    }

    private static QueryBuilder matchish(String fieldName, String value){
        return QueryBuilders.fuzzyQuery(fieldName, value);
    }


    public static SearchQuery mapCriteria(String keyword, SearchCriteria search, int offset, int limit){
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (search.getList_for() == null | keyword == null)
            throw new InvalidModelInputException();
        queryBuilder.must(match("address", keyword))
//                .must(QueryBuilders.boostingQuery(matchish("address", keyword), matchish("project_name", keyword)))
                .filter(matchExactly("list_for", search.getList_for()))
                .filter(between("date_listed",search.getPostedBy(), null))
                .filter(between("price", search.getMinBudget(), search.getMaxBudget()));

        if (search.getNum_bedroom() != null && search.getNum_bedroom().length != 0){
            Arrays.stream(search.getNum_bedroom()).forEach(i -> queryBuilder.filter(matchExactly("num_beds", Integer.toString(i))));
        }


        List< HighlightBuilder.Field > fields = Collections.singletonList(new HighlightBuilder.Field("address").matchedFields("address"));
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .postTags("</highlight>")
                .preTags("<highlight>").field("address");


        return new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(offset, limit))
                .withHighlightBuilder(new HighlightBuilder( highlightBuilder, queryBuilder, fields))
                .addAggregation(AggregationBuilders.filter("queryFilter", queryBuilder))
                .addAggregation(AggregationBuilders.terms("field count"))
                .build();
    }
}