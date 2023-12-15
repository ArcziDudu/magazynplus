package com.magazynplus.repository;


import com.magazynplus.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Integer> {
}
