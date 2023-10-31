package ru.volga_it.simbir_go.features.account.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.volga_it.simbir_go.features.account.security.props.JwtProperties;
import ru.volga_it.simbir_go.features.account.services.blockedTokenKey.UserBlockedTokenKeyService;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final JwtUserDetailsService userDetailsService;
    private final UserBlockedTokenKeyService userBlockedTokenKeyService;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(Long userId, String username, Boolean isAdmin) {
        String userTokenKey = UUID.randomUUID().toString();

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        claims.put("is_admin", isAdmin);
        claims.put("user_token_key", userTokenKey);
        Instant validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.DAYS);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        if(claims.getBody().getExpiration().before(new Date())) return false;

        Long userId = Long.parseLong(claims.getBody().get("id").toString());
        String userTokenKey = claims.getBody().get("user_token_key").toString();

        return !userBlockedTokenKeyService.tokenIsBlocked(userId, userTokenKey);
    }

    private String getId(final String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id")
                .toString();
    }

    private String getUsername(final String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private String getUserTokenKey(final String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("user_token_key")
                .toString();
    }

    public Authentication getAuthorization(String token) {
        String username = getUsername(token);
        String userTokenKey = getUserTokenKey(token);
        UserDetails userDetails = userDetailsService.getUserDetailsByUsername(userTokenKey, username);
        return new UsernamePasswordAuthenticationToken(userDetails,
                "",
                userDetails.getAuthorities());
    }
}
