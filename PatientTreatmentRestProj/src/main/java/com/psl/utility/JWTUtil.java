package com.psl.utility;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil implements Serializable{
	private static final long serialVersionUID = 1L;

	@Value("${jwt.secret}")
	private static String sKey="pslkey";
	
	public static String generateToken(String id, String subject){
			return Jwts.builder()
					.setId(id)
					.setSubject(subject)
					.setIssuer("ParvBhatt")
					.setIssuedAt(new  Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(15)))
					.signWith(SignatureAlgorithm.HS256, Base64.getEncoder()
					.encode(sKey.getBytes()))
					.compact();
	}
	
	public static Claims getClaims(String skey, String token) {
		return Jwts.parser()
				   .setSigningKey(Base64.getEncoder()
					.encode(skey.getBytes()))
					.parseClaimsJws(token)
					.getBody();
	}
	
	public static boolean isTokenValid(String skey, String token) {
		Date tokenExpiryDate = getClaims(skey, token).getExpiration();
		Date sysDate = new Date();
		return sysDate.before(tokenExpiryDate);
	}
}//class
