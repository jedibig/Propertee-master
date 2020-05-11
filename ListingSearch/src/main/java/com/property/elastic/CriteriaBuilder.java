package com.property.elastic;

import com.property.dto.SearchCriteria;
import com.property.exception.InvalidModelInputException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.lang.annotation.Native;
import java.util.Arrays;
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
        queryBuilder.must(matchish("address", keyword))
//                .must(QueryBuilders.boostingQuery(matchish("address", keyword), matchish("project_name", keyword)))
                .filter(matchExactly("list_for", search.getList_for()))
                .filter(between("date_listed",search.getPostedBy(), null))
                .filter(between("price", search.getMinBudget(), search.getMaxBudget()));

        if (search.getNum_bedroom() != null && search.getNum_bedroom().length != 0){
            Arrays.stream(search.getNum_bedroom()).forEach(i -> {
                queryBuilder.filter(matchExactly("num_beds", Integer.toString(i)));
            });
        }
        List< HighlightBuilder.Field > fields = Arrays.asList( new HighlightBuilder.Field("address").matchedFields("address") );
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .postTags("</highlight>")
                .preTags("<highlight>").field("address");


        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(offset, limit))
                .withHighlightBuilder(new HighlightBuilder( highlightBuilder, queryBuilder, fields))
                .build();

        System.out.println(((NativeSearchQuery) searchQuery).toString());
        System.out.println(queryBuilder);
        System.out.println(highlightBuilder);


        return searchQuery;
            //TODO modify filter range, because it is
//        return queryBuilder;
    }
}

// http://localhost:8080/listings?list_for=SELL&keyword=House+in+California&property_type=&postedBy=2019-05-04&minBudget=&maxBudget=&num_bedroom=
//cat <<EOF >~/.s3cfg.riak_cs
//        [default]
//        access_key = MUAEQWNOB2GEPEUG6SAC
//host_base = s3.amazonaws.dev
//        host_bucket = %(bucket)s.s3.amazonaws.dev
//        proxy_host = 127.0.0.1
//        proxy_port = 8080
//        secret_key = fSY1XKW4hh4LGSJkZIdsO78Zeh6dbXy_IYpQWw==
//signature_v2 = True
//        EOF