package com.unololtd.nazmul.springrest.service;

import com.unololtd.nazmul.springrest.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    public Page<ProductEntity> findAllProducts(Pageable pageable);
}
