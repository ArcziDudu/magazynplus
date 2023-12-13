package com.magazynplus.repository;



import com.magazynplus.dto.ProductResponse;
import com.magazynplus.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

}
