package br.edu.ifsul.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
/**
 *
 * @author Prof. Me. Jorge Luis Boeira Bavaresco
 * @email jorge.bavaresco@passofundo.ifsul.edu.br
 * @organization IFSUL - Campus Passo Fundo
 */
public class JWTUtil {

    private static final String key = "SECRET_TOKEN";

    public static final String TOKEN_HEADER = "Authorization";

    public static String create(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, key)
                .setExpiration(new Date( new Date().getTime() + 600000 ) ) //tempo de 10 minutos de validade
                .setIssuer("AUTORIZADOR")
                .compact();
    }

    public static Jws<Claims> decode(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }
}
