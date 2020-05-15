package com.property.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "listings", type = "_doc")
public class ListingElastic {
    @Id
    private long listing_id;


    private String address;
    @Field(type = FieldType.Text, analyzer = "autocomplete")
    private String project_name;
    @Field(type = FieldType.Text, index = false)
    private String description;
    @Field(type = FieldType.Integer, index = false)
    private int num_beds;
    @Field(type = FieldType.Integer,index = false)
    private int num_baths;

    @Field(type = FieldType.Keyword)
    private Property_Type property_type;
    @Field(type = FieldType.Date)
    private Date date_listed;

    @Field(type = FieldType.Keyword)
    private List_For list_for;
    @Field(type = FieldType.Double)
    private BigDecimal price;
    @Field(type = FieldType.Double)
    private BigDecimal area;

    private String[] tags;



    public enum Property_Type{
        APARTMENT("Apartment"),
        HOUSE("House"),
        OTHERS("Others");

        String name;

        Property_Type(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum List_For{
        SELL(0), RENT(1);

        int numeric;

        List_For(int numeric) {
            this.numeric = numeric;
        }
    }


}
