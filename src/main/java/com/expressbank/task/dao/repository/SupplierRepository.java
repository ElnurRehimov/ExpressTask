package com.expressbank.task.dao.repository;

import com.expressbank.task.dao.entity.Supplier;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findByNameAndAddress(String name, String address);
}
