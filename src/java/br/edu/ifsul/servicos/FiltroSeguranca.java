/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.servicos;

import br.edu.ifsul.jwt.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureException;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Prof. Me. Jorge Luis Boeira Bavaresco
 * @email jorge.bavaresco@passofundo.ifsul.edu.br
 * @organization IFSUL - Campus Passo Fundo
 */
@WebFilter(urlPatterns = "/servicos/*")
public class FiltroSeguranca implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        System.out.println("RequestURI: " + req.getRequestURI());
        System.out.println("RequestURL: " + req.getRequestURL());
        /**
         * Este IF é necessário para liberar o acesso a descrição do serviço e ao método para gerar o 
         * token, pois é necessário o acesso a eles sem autenticação
         */
        if (req.getRequestURI().contains("/servicos/login/efetuar") || req.getRequestURI().contains("application.wadl")) {
            System.out.println("Processou o filtro....");
            chain.doFilter(request, response);
            return;
        }
        /**
         * Captura o token enviado na requisição
         */
          
        String token = req.getHeader(JWTUtil.TOKEN_HEADER);

        /**
         * Se for vazio ou nulo retorna 401
         */
        if (token == null || token.trim().isEmpty()) {
            res.setStatus(401);
            return;
        }

        try {
            /**
             * Caso consiga decodificar o token termina a requisição
             */
            Jws<Claims> parser = JWTUtil.decode(token);
            System.out.println("User request: " + parser.getBody().getSubject());
            chain.doFilter(request, response);
        } catch (SignatureException e) {
            res.setStatus(401);
        }
    }

    @Override
    public void destroy() {

    }

}
