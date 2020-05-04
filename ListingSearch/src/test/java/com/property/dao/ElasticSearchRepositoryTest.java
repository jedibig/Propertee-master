package com.property.dao;

import com.property.SpringWebConfig;
import com.property.elastic.ListingRepositoryElastic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.stream.StreamSupport;

@SpringBootTest(classes = SpringWebConfig.class)
public class ElasticSearchRepositoryTest {

//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ListingRepositoryElastic repository;

    int listing_count = 0;

    @BeforeEach
    public void loadData() throws IOException {
//        elasticsearchTemplate.deleteIndex(Listing.class);
//        elasticsearchTemplate.createIndex(Listing.class);
//        elasticsearchTemplate.putMapping(Listing.class);

//        ObjectMapper mapper = new ObjectMapper();
//        Listing[] listings = mapper.readValue(new File("listing.json"), Listing[].class);
//        List<IndexQuery> queries = Arrays.stream(listings).map(l -> {
//            IndexQuery indexQuery = new IndexQuery();
//            indexQuery.setId(Long.toString(l.getListing_id()));
//            indexQuery.setObject(l);
//            return indexQuery;
//        }).collect(Collectors.toList());
//
//        elasticsearchTemplate.bulkIndex(queries);
//
//        listing_count = listings.length;
        System.out.println("done loading data");
    }

    @Test
    public void shouldReturnNumberofTotalListing() {
        assertEquals(StreamSupport.stream(repository.findAll().spliterator(),false).count(), listing_count);
    }

}
