package com.example.demo.utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.services.LoginService;

@Component  // This makes JwtUtil a Spring-managed bean
public class JwtUtil {
	
	@Value("${jwt.secret}")
    private String SECRET_KEY;
	
	    // Method to validate and parse token from request
	    public Claims getClaimsFromRequest(HttpServletRequest request) {
	        String header = request.getHeader("Authorization");
	        System.out.println(header);
	        if (header == null || !header.startsWith("Bearer ")) {
	            throw new JwtException("Invalid Authorization header.");
	        }
	        
	        String token = header.substring(7); // Remove "Bearer "
	        System.out.println(token);
	        return parseToken(token);
	    }

	    // Method to parse token and get claims
	    private Claims parseToken(String token) {
	    	 // Use the SECRET_KEY from LoginService
	     
	       System.out.println(SECRET_KEY);
	        Claims claims = null;
	 
	        try {
	            claims = Jwts.parserBuilder()
	                    .setSigningKey(SECRET_KEY)
	                    .build()
	                    .parseClaimsJws(token)
	                    .getBody();
	           
	            // Use claims as needed
	        } catch (JwtException e) {
	            System.out.println("JWT Exception: " + e.getMessage());
	        } catch (Exception e) {
	            System.out.println("General Exception: " + e.getMessage());
	        }
	        return claims;

	    }

	    // Checks if token is expired
	    public boolean isTokenValid(String token) {
	        Claims claims = parseToken(token);
	        return claims.getExpiration().after(new Date());
	    }
	 
}
