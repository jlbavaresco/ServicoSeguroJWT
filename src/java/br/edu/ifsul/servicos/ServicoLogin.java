/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.servicos;

import br.edu.ifsul.jwt.JWTUtil;
import br.edu.ifsul.jwt.Usuario;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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
@Path("login")
public class ServicoLogin implements Serializable {

    public ServicoLogin() {
    }

    /**
     * Método que recebe um usuário por parâmetro e verifica o nome de usuário e senha
     * @param usuario no formato {"login":"jorge", "senha":"123"}
     * @return um token com a autenticação
     */
    @POST
    @Path("efetuar")
    @Consumes({"application/json; charset=ISO-8859-1"}) 
    @Produces({"application/json; charset=ISO-8859-1"})     
    public Response login( Usuario usuario) {
        if (usuario.getLogin().equals("jorge") && usuario.getSenha().equals("123")) {
            String token = JWTUtil.create(usuario.getLogin());
            System.out.println("TOKEN Gerado: " + token);
            return Response.ok().entity(token).build();
        } else {
            throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
        }
    }
    
}
