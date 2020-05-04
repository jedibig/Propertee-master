package com.property.controller;

import com.property.dto.SearchCriteria;
import com.property.service.ListingSearchService;
import com.property.service.ListingSearchServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@Controller
@RequestMapping("/listings")
@Setter
public class ListingController {
    static Logger logger = Logger.getLogger(ListingController.class.getName());
    @Autowired
    ListingSearchService listingSearchService;

    @GetMapping
    public String searchProperty(@RequestParam("keyword") String keyword,
                                 @ModelAttribute(value = "searchCriteria") SearchCriteria criteria,
                                 @RequestParam(value = "offest", defaultValue = "0") int offset,
                                 @RequestParam(value = "limit", defaultValue = "10") int limit,
                                 BindingResult result, Model m, HttpServletRequest request) throws URISyntaxException {

        if(result.hasErrors()){
            result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).forEach(System.out::println);
            return "defaulterror";
        }

        final String nextpage = new URIBuilder(request.getRequestURI())
                        .setParameter("offset", Integer.toString(offset+1))
                        .build().toASCIIString();
        System.out.println(request.getRequestURI());
        System.out.println(nextpage);
        m.addAttribute("isempty", true);

        listingSearchService.getListingWithCriteria(keyword, criteria, offset, limit).ifPresent(l->{
            logger.info("Found " + l.size() + " listing with matching criteria");
            m.addAttribute("isempty", false);
            m.addAttribute("listings", l);
            m.addAttribute("next_page_uri", nextpage);
        });

        return "search_result";
    }

    @InitBinder("searchCriteria")
    public void allowEmptyDateBinding( WebDataBinder binder )
    {
        // Custom String Editor. tell spring to set empty values as null instead of empty string.
        binder.registerCustomEditor( String.class, new StringTrimmerEditor( true ));
        binder.registerCustomEditor( Integer.class, "num_bedroom", new CustomNumberEditor(Integer.class, true));

        //Custom Date Editor
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        binder.registerCustomEditor( Date.class, new CustomDateEditor( simpleDateFormat,true));
    }
}
