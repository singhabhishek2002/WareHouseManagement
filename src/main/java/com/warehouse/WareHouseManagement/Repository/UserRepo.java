package com.warehouse.WareHouseManagement.Repository;

import com.warehouse.WareHouseManagement.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users,Integer>{
    public Users findByname(String username);
}
