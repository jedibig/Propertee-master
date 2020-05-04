package com.property;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.io.IOException;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.property.elastic")
public class SpringWebConfig {
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext ctx= SpringApplication.run(SpringWebConfig.class, args);

//        ObjectMapper mapper = new ObjectMapper();
//        Listing[] listings = mapper.readValue(ResourceUtils.getFile("classpath:listing.json"), Listing[].class);
//        List<IndexQuery> queries = Arrays.stream(listings).map(l -> {
//            IndexQuery indexQuery = new IndexQuery();
//            indexQuery.setId(Long.toString(l.getListing_id()));
//            indexQuery.setObject(l);
//            return indexQuery;
//        }).collect(Collectors.toList());
//        template.bulkIndex(queries);

//        ListingRepository repo = ctx.getBean(ListingRepository.class);

//        QueryBuilder crit = CriteriaBuilder.mapCriteria("global", null);
//        System.out.println(crit);
//
//        repo.search(crit, PageRequest.of(0,10)).forEach(System.out::println);
//
//        ctx.registerShutdownHook();
//        System.exit(1);
    }

}
