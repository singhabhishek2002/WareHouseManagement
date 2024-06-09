package com.warehouse.WareHouseManagement.Service;


import com.warehouse.WareHouseManagement.Entity.Product;
import com.warehouse.WareHouseManagement.Entity.Users;
import com.warehouse.WareHouseManagement.Repository.ProductRepo;
import com.warehouse.WareHouseManagement.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {

    @Autowired
    private UserRepo UserRepository;

    @Autowired
    private ProductRepo productRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String addUser(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserRepository.save(user);
        return "User added successfully";
    }


    public List<Users> getAllUser(){
        return UserRepository.findAll();
    }
    public ResponseEntity<Object> getUser(Integer id){
        Optional<Users> optionalUser = UserRepository.findById(id);
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok().body(optionalUser);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    public String addProduct(Product product) {
        productRespository.save(product);
        return "product added successfully";
    }
}