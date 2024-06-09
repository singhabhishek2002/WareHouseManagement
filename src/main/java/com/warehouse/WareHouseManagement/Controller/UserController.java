package com.warehouse.WareHouseManagement.Controller;
import com.warehouse.WareHouseManagement.Entity.AuthRequest;
import com.warehouse.WareHouseManagement.Entity.JwtResponse;
import com.warehouse.WareHouseManagement.Entity.Product;
import com.warehouse.WareHouseManagement.Entity.Users;
import com.warehouse.WareHouseManagement.Repository.ProductRepo;
import com.warehouse.WareHouseManagement.Service.JwtUtil;
import com.warehouse.WareHouseManagement.Service.UserInfoService;
import com.warehouse.WareHouseManagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/auth/user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtService;

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Spring Security tutorials !!";
    }

    @PostMapping("/register")
    public String addUser(@RequestBody Users user) {
        return userService.addUser(user);
    }


//      @PostMapping("/login")
//    public String loginn(@RequestBody AuthRequest authRequest) {
//
//        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getPassword(),authRequest.getUserName()));
//        if(authentication.isAuthenticated()){
//            return jwtService.generateToken(authRequest.getUserName());
//        }
//        else{
//            throw new UsernameNotFoundException("username not found");
//        }
//   }

@PostMapping("/login")
public ResponseEntity<JwtResponse> login(@RequestBody AuthRequest authRequest) {

        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getPassword(),authRequest.getUserName()));
        if(authentication.isAuthenticated()){
            String token= jwtService.generateToken(authRequest.getUserName());
            JwtResponse jwtResponse=JwtResponse.builder()
                    .jwtToken(token)
                    .build();
            return  new ResponseEntity<>(jwtResponse,HttpStatus.OK);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found with username: " + authRequest.getUserName());
        }
}

    @GetMapping("/getUsers")
 //   @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PreAuthorize("hasRole('USER')")
    public List<Users> getAllUsers() {
        return userService.getAllUser();
    }

    @GetMapping("/getUsers/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Integer id) {
        return userService.getUser(id);
    }


    @PostMapping("/product/post")
    @PreAuthorize("hasRole('ADMIN')")
    public String addProduct(@RequestBody Product product) {
        return userService.addProduct(product);

    }

    @GetMapping("/product/get")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Iterable<Product>> getAllProduct() {
        Iterable<Product> p = productRepo.findAll();
        if (p.iterator().hasNext()) {
            return ResponseEntity.ok().body(p);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
