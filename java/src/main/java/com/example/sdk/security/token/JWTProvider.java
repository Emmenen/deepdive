package com.example.sdk.security.token;

import com.example.sdk.bean.ConfluxUser;
import com.example.sdk.security.token.ConfluxToken;
import com.owlike.genson.Genson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

/**
 * Created with IntelliJ IDEA at 2022/4/29 17:11
 * User: @Qi Long
 */
@Component
public class JWTProvider {
    private Key key;	// 私钥

    @Autowired
    private Genson genson;

    @PostConstruct
    public void init() {
        byte[] keyBytes;
        keyBytes = "E6PCceSVGOv0UNErWnCv2f1OFtmKrCCX".getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes); // 使用mac-sha算法的密钥
//        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);;

    }
    public String createToken(ConfluxToken confluxToken){
       return this.createToken(confluxToken,1000*60*60*24);
    }

    public String createToken(ConfluxToken confluxToken, long tokenValidityInMilliseconds) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + tokenValidityInMilliseconds);;
        Object principal = confluxToken.getPrincipal();

        Map<String ,Object> map = new HashMap<>();
        map.put("user",genson.serialize(principal));
        map.put("auth",confluxToken.getAuthorities());

        return Jwts.builder()
                .setClaims(map) // 添加body
                .signWith(key, SignatureAlgorithm.HS256) // 指定摘要算法
                .setExpiration(validity) // 设置有效时间
                .compact();
    }

    public ConfluxToken getAuthentication(String token) throws ExpiredJwtException {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody(); // 根据token获取body
        String userString = (String) claims.get("user");
//        if (claims.getExpiration().compareTo(new Date())<0) {
//            return null;
//        }
        ConfluxUser user = genson.deserialize(userString, ConfluxUser.class);
        ArrayList auth = (ArrayList) claims.get("auth");
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        auth.stream().forEach((x)->{
            HashMap<String,String> map = (HashMap) x;
            map.keySet().stream().map(map::get).forEach((s) -> {
                        if (!StringUtils.hasLength(s)){
                            s = "null";
                        }
                        grantedAuthorities.add(new SimpleGrantedAuthority(s));
                    }
            );
        });
        return new ConfluxToken(user,"",new ArrayList<>());
    }
}