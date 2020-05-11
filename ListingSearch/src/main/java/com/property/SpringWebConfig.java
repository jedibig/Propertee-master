package com.property;

import com.property.dto.SearchCriteria;
import com.property.service.ListingSearchService;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

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

        ListingSearchService service = ctx.getBean(ListingSearchService.class);

        SearchCriteria criteria = new SearchCriteria();
        criteria.setList_for("SELL");

        service.getListingWithCriteria("california",criteria, 0, 10)
                .ifPresent(l -> l.forEach(System.out::println));

        ctx.registerShutdownHook();
        System.exit(1);
    }

    @Bean
    public Client client() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("client.transport.sniff", true)
                .put("path.home", "/usr/share/elasticsearch/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin")
                .put("cluster.name", "docker-cluster").build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(new InetSocketAddress("localhost", 9300)));
        return client;
    }

//    @Bean
//    public ElasticsearchTemplate elasticsearchTemplate(Client client) throws UnknownHostException {
//        return new ElasticsearchTemplate(client);
//    }
}
