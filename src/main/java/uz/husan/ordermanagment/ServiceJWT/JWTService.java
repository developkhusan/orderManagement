package uz.husan.ordermanagment.ServiceJWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTService {
    @Value("${jwt.secret}")
    String secretKey;
    @Value("${jwt.expire-time}")
    Long expiration;
    public String generateToken(String username) {
        Date expireDate = new Date(new Date().getTime() + expiration);
        String jwt = Jwts
                .builder()
                .subject(username)
                .expiration(expireDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        return jwt;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String getEmailFromToken(String authorization) {
        Claims payload = getClaims(authorization);
        return payload.getSubject();
    }

    public boolean isValid(String authorization) {
        try {
            Claims claims = getClaims(authorization);
            Date expiration = claims.getExpiration();
            return  (new Date().toInstant().isBefore(expiration.toInstant()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    private Claims getClaims(String authorization) {
        Claims payload = Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(authorization)
                .getPayload();
        return payload;
    }
}
