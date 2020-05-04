package com.property.elastic;

import com.property.dto.SearchCriteria;
import com.property.exception.InvalidModelInputException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class CriteriaBuilder {

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


    public static QueryBuilder mapCriteria(String keyword, SearchCriteria search){
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        if (search.getList_for() == null | keyword == null)
            throw new InvalidModelInputException();

        queryBuilder.must(match("address", keyword))
//                    .should(matchish("address", keyword))
                    .should(matchAll(keyword, "description", "project_name"))
                    .filter(matchExactly("list_for", search.getList_for()))
                    .filter(between("price", search.getMinBudget(), search.getMaxBudget()))
                    .filter(between("num_beds", search.getNum_bedroom(), null))
                    .filter(between("date_listed",search.getPostedBy(), null));


        return queryBuilder;
    }
}
