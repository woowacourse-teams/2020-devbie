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
import underdogs.devbie.auth.exception.AccessTokenLoadException;
import underdogs.devbie.auth.exception.ExpiredTokenException;

@Component
public class JwtTokenProvider {
    // todo 과연 토큰프로바이저가 도메인인가?

    private final String secretKey;
    private final long validityInMilliseconds;

    public JwtTokenProvider(
        @Value("${security.jwt.token.secret-key:sample}") String secretKey,
        @Value("${security.jwt.token.expire-length:300000}") long validityInMilliseconds
    ) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(UserTokenDto userTokenDto) {
        Claims claims = Jwts.claims();
        claims.put("userId", String.valueOf(userTokenDto.getId()));
        claims.put("role", userTokenDto.getRoleType().toString());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public Claims extractValidSubject(String token) {
        validateToken(token);

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private void validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            validateExpiredTime(claims);
        } catch (JwtException | IllegalArgumentException e) {
            throw new AccessTokenLoadException();
        }
    }

    private void validateExpiredTime(Jws<Claims> claims) {
        if (claims.getBody().getExpiration().before(new Date())) {
            throw new ExpiredTokenException();
        }
    }
}

