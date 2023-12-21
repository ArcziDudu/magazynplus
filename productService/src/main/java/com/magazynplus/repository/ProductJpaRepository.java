package com.magazynplus.repository;



import com.magazynplus.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByNameContainingAndUserId(String productName, Integer userId);

    List<ProductEntity> findByUserId(Integer userId, Pageable pageable);

    boolean existsByBestBeforeDateAndNameAndUserId(LocalDate bestBeforeDate, String name, Integer userId);

    ProductEntity findByNameAndBestBeforeDate(String name, LocalDate localDate);
}
