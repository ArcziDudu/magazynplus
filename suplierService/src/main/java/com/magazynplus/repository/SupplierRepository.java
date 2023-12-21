package com.magazynplus.repository;


import com.magazynplus.dto.SupplierResponse;
import com.magazynplus.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Integer> {
   List<SupplierEntity> findAllByUserId(int i);
}
