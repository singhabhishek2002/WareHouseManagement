package com.warehouse.WareHouseManagement.Entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthRequest {
    private String userName;
    private String password;
}
