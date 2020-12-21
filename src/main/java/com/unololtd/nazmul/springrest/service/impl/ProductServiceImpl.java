package com.unololtd.nazmul.springrest.service.impl;

import com.unololtd.nazmul.springrest.entity.ProductEntity;
import com.unololtd.nazmul.springrest.repository.ProductRepository;
import com.unololtd.nazmul.springrest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl  implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    @Override
    public Page<ProductEntity> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
