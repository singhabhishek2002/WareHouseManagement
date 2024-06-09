package com.warehouse.WareHouseManagement.Repository;

import com.warehouse.WareHouseManagement.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Integer> {

}
