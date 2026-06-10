package com.generation.projetorh.security;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	private String secret = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	private Duration expiration = Duration.ofMinutes(60);
	private Key signingKey;

	private Key getSigningKey() {

		if (signingKey == null) {
			byte[] keyBytes = Decoders.BASE64.decode(secret);
			signingKey = Keys.hmacShaKeyFor(keyBytes);
		}

		return signingKey;
	}

	private Claims extractAllClaims(String token) {

		return Jwts.parser()
				.verifyWith((javax.crypto.SecretKey) getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String extractUsername(String token) {

		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {

		return extractClaim(token, Claims::getExpiration);
	}

	private Boolean isTokenExpired(String token) {

		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {

		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String generateToken(String userName) {

		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}

	private String createToken(Map<String, Object> claims, String userName) {

		Instant now = Instant.now();

		return Jwts.builder()
				.claims(claims)
				.subject(userName)
				.issuedAt(Date.from(now))
				.expiration(Date.from(now.plus(expiration)))
				.signWith(getSigningKey())
				.compact();
	}
}