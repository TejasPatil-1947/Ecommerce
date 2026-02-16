package com.ecommerce.Service.Impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtService {

    private final static String SECRET = "HELLOIMTEJASJLDJKDLFJAJFOIJEOFJRWLQKFJOIJLKLKFJDLKFJLIJFLJSDLKFJSLKDFJlSDJFLKJDLKFJSLKDJFlSDJflk";

    private final static long VALIDITY = TimeUnit.MINUTES.toMillis(30);


    public SecretKey getSecretKey(){
        byte[] decode = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decode);
    }

    public String generateToken(UserDetails userDetails) throws Exception{
        Map<String,Object> claims = new HashMap<>();
        claims.put("role",userDetails.getAuthorities());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from((Instant.now())))
                .setExpiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(getSecretKey())
                .compact();
    }

    public String extractEmail(String jwt){
        return extractClaim(jwt,Claims::getSubject);
    }

    public Date extractExpiration(String jwt){
        return extractClaim(jwt,Claims::getExpiration);
    }

    public boolean isTokenExpired(String jwt){
        return extractExpiration(jwt).before(Date.from(Instant.now()));
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails){
        String email = extractEmail(jwt);
        return (email.equals(userDetails.getUsername())) && !isTokenExpired(jwt);
    }

     public <T> T extractClaim(String jwt, Function<Claims, T> claimResolver){
         Claims claims = extractAllClaims(jwt);
        return claimResolver.apply(claims);
     }

    public Claims extractAllClaims(String jwt){
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }


}
