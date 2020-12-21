package com.unololtd.nazmul.springrest.repository;

import com.unololtd.nazmul.springrest.entity.ProductEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<ProductEntity,Integer> {
}
