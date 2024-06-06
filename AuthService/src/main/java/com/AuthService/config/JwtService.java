package com.AuthService.config;

import com.AuthService.entity.Role;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Service
public class JwtService {

    final private SecretKey key;
    final private JwtParser parser;

    public JwtService() {
        key = Keys.hmacShaKeyFor("Keys.hmacShaKeyFor(\"1234567890\".getBytes(StandardCharsets.UTF_8));".getBytes(StandardCharsets.UTF_8));
        parser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String generate(String userName, Long id, Set<Role> roles) {
       Map<String, Object> claim = new HashMap<>();
        String roleName = String.valueOf(roles.stream()
                .map(Role::getRole)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User has no roles")));

        claim.put("role", roleName);
        claim.put("userId", id);
        JwtBuilder builder = Jwts.builder()
                .addClaims(claim)
                .setSubject(userName)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)))
                .signWith(key);

        return builder.compact();

    }

    public void getUserIdFromCliams(String token) {
        Claims claims = parser
                .parseClaimsJws(token)
                .getBody();

        System.out.println("Claims -> " + claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    public String getUserID(String token) {
        return parser
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean isValid(String token, String userName) {
        Claims claims = parser
                .parseClaimsJws(token)
                .getBody();

        boolean unexpired = claims.getExpiration().after(Date.from(Instant.now()));

        return unexpired && userName.equalsIgnoreCase(claims.getSubject());
    }

}