package com.pds.reservation.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY ="cUZLCi1ywToSRZb5SnR6UuNSZd3hrRricaCiW+IQqQnmgTV2rW/UVh+zEDPEFIEWue1y6SWDGFlhCh7DoCnd/5yL8EzAHuHtbW9ZkHkyuUTwdlqOOOzenkzitDtEYIjF8OUQW/WiHX3zcnK5naoda+Inyu1Pvz3RUWq/OfJyy8IvSwvzSibV5cNAV/l0rU61663yPk6u10APX/D0XmP+FfnBYcmzx2kVA1lrQSlXVxDYFJnFzqoRmyOE86nO/dqlqXu0Zxb5C+9Ctp83VVFpX1YF8v+n68lyCzjXYtSn/edO1wB/5GYu+GMAxA/CnV5YWv0HrofBB//nWPrq2fhE+KDGIbq++mxAOivXs+lpX5g=";
    private static final long jwtExpiration=86400000;
    public String extractMail(String token){
        return extractClaim(token, Claims::getSubject);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey(){
        byte[] KeyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(KeyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, jwtExpiration);
    }
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token ,UserDetails userDetails){
        final String mail =extractMail(token);
        return (mail.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public  boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

}
