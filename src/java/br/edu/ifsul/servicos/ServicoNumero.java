/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.servicos;

import br.edu.ifsul.jwt.JWTUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Prof. Me. Jorge Luis Boeira Bavaresco
 * @email jorge.bavaresco@passofundo.ifsul.edu.br
 * @organization IFSUL - Campus Passo Fundo
 */
@Stateless
@Path("numero")
public class ServicoNumero {

    Gson gson = new Gson();

    @GET
    @Path("gerar")
    @Produces({"application/json"})
    public Response gerar(@HeaderParam("Authorization") String token) {
        if (token == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        System.out.println("Token recebido: " + token);
        try {
            Jws<Claims> claims = JWTUtil.decode(token);
            System.out.println("Você está autenticado como usuário "
                    + claims.getBody().getSubject() + " autenticado por "
                    + claims.getBody().getIssuer());
            Double numero = Math.random();
            return Response.ok(gson.toJson(numero)).build();
        } catch (ExpiredJwtException ex) {
            throw new WebApplicationException("Tempo do token expirado.", Response.Status.FORBIDDEN);
        } catch (Exception e) {
            throw new WebApplicationException("Token inválido.", Response.Status.FORBIDDEN);
        }
    }

}
