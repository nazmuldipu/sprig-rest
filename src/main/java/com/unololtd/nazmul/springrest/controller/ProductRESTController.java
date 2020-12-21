package com.unololtd.nazmul.springrest.controller;

import com.unololtd.nazmul.springrest.common.MyUtil;
import com.unololtd.nazmul.springrest.entity.ProductEntity;
import com.unololtd.nazmul.springrest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class ProductRESTController {

    @Autowired
    private ProductService productService;

    @Autowired
    private EntityLinks links;

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<ProductEntity>> AllProducts(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<ProductEntity> products = productService.findAllProducts(pageable);
        PagedModel<ProductEntity> pr = assembler.toModel(products, linkTo(ProductRESTController.class).slash("/products").withSelfRel());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Link", MyUtil.createLinkHeader(pr));
        return new ResponseEntity(assembler.toModel(products, linkTo(ProductRESTController.class).slash("/products").withSelfRel()), responseHeaders, HttpStatus.OK);
    }

//    private String createLinkHeader(PagedModel<?> pr){
//        final StringBuilder linkHeader = new StringBuilder();
//        linkHeader.append(buildLinkHeader(  pr.getLinks("first").get(0).getHref(),"first"));
//        linkHeader.append(", ");
//        System.out.println(pr.getLinks());
//        linkHeader.append(buildLinkHeader( pr.getLinks("last").get(0).getHref(),"last"));
//        return linkHeader.toString();
//    }
//
//    public static String buildLinkHeader(final String uri, final String rel) {
//        return "<" + uri + ">; rel=\"" + rel + "\"";
//    }
}
