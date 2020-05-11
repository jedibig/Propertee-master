package com.property.elastic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.property.dto.ListingElastic;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.ResultsExtractor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListingResultExtractor implements ResultsExtractor<List<ListingElastic>> {
    @Override
    public List<ListingElastic> extract(SearchResponse response) {
        return Arrays.stream(response.getHits().getHits()).map(s -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                ListingElastic list = mapper.readValue(s.getSourceAsString(), ListingElastic.class);
                HighlightField field = s.getHighlightFields().get("address");
                if (field != null){
                    Arrays.stream(field.getFragments()) //String[]  {s1, s2,s3} s1 + s2 = s7, s7+ s3 = s8
                            .map(Text::toString) //stream String
                            .reduce((s1, s2) -> s1 + s2)
                            .ifPresent(list::setAddress);
                }

                // or

                Optional.ofNullable(s.getHighlightFields().get("address"))
                        .map(HighlightField::getFragments)
                        .flatMap(t -> Arrays.stream(t).map(Text::toString).reduce((s1, s2) -> s1 + s2))
                        .ifPresent(list::setAddress);
                return list;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }
}
