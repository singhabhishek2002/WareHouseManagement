package com.warehouse.WareHouseManagement.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final String SECERET = "!@#$FDGSDFGSGSGSGSHSHSHSSHGFFDSGSFGSSGHSDFSDFSFSFSFSDFSFSFSF";

    public String generateToken(String username){
         Map<String,Object> claims=new HashMap<>();
         return Jwts.builder()
                 .setClaims(claims)
                 .setSubject(username)
                 .setIssuedAt(new Date(System.currentTimeMillis()))
                 .setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))

                 .signWith(getSignKey(),SignatureAlgorithm.HS256)
                 .compact();
     }

    private Key getSignKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECERET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims=extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }
      public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
      }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName= extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}

//public String generateToken(String userName) {
//        Map<String, Object> claims = new HashMap<>();
//        System.out.println("Generating token for user: " + userName);
//        try {
//            String token = Jwts.builder()
//                    .setClaims(claims)
//                    .setSubject(userName)
//                    .setIssuedAt(new Date(System.currentTimeMillis()))
//                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
//                    .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
//            System.out.println("Token generated successfully: " + token);
//            return token;
//        } catch (Exception e) {
//            System.err.println("Error generating token: " + e.getMessage());
//            e.printStackTrace();
//            return null;
//        }
//    }

