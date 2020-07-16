package underdogs.devbie.auth.jwt;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import underdogs.devbie.auth.dto.UserTokenDto;
import underdogs.devbie.auth.exception.InvalidAuthenticationException;
import underdogs.devbie.auth.exception.ExpiredTokenException;

@Component
public class JwtTokenProvider {

    private final String secretKey;
    private final long validityInMilliseconds;

    public JwtTokenProvider(
        @Value("${security.jwt.token.secret-key}") String secretKey,
        @Value("${security.jwt.token.expire-length}") long validityInMilliseconds
    ) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(UserTokenDto userTokenDto) {
        String subject = String.valueOf(userTokenDto.getId());
        Claims claims = Jwts.claims().setSubject(subject);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String extractValidSubject(String token) {
        validateToken(token);

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    private void validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            validateExpiredTime(claims);
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidAuthenticationException();
        }
    }

    private void validateExpiredTime(Jws<Claims> claims) {
        if (claims.getBody().getExpiration().before(new Date())) {
            throw new ExpiredTokenException();
        }
    }
}

