//package com.unololtd.nazmul.springrest.data;
//
//import com.unololtd.nazmul.springrest.entity.ProductEntity;
//import org.springframework.hateoas.RepresentationModel;
//
//import java.util.List;
//
//public class ProductData extends RepresentationModel {
//
//
//    List<ProductEntity> products;
//    private int pageNumber;
//    private int size;
//    private int totalPages;
//    private int totalElements;
//
//    public ProductData(List<ProductEntity> products, int pageNumber, int size, int totalPages, int totalElements) {
//        this.products = products;
//        this.pageNumber = pageNumber;
//        this.size = size;
//        this.totalPages = totalPages;
//        this.totalElements = totalElements;
//    }
//
//    public List<ProductEntity> getProducts() {
//        return products;
//    }
//
//    public int getPageNumber() {
//        return pageNumber;
//    }
//
//    public int getSize() {
//        return size;
//    }
//
//    public int getTotalPages() {
//        return totalPages;
//    }
//
//    public int getTotalElements() {
//        return totalElements;
//    }
//}
