package nl.oscar.kwetter.api.rest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nl.oscar.kwetter.api.SimpleKeyGenerator;
import nl.oscar.kwetter.domain.Credentials;
import nl.oscar.kwetter.domain.User;
import nl.oscar.kwetter.service.login.LoginService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.Date;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/login")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Transactional
@Stateless
public class RestLoginController {

    @Inject
    private SimpleKeyGenerator keyGenerator;

    @Inject
    private LoginService loginService;

    @POST
    @Path("/login")
    public Response authenticateUser(Credentials credentials) {
        try {

            User user = loginService.authenticate(credentials);

            String token = issueToken(credentials.getUsername());
            user.setToken(token);

            return Response.ok(user).header(AUTHORIZATION, "Bearer " + token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private String issueToken(String login) {
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwtToken;
    }

    @POST
    @Path("/signup")
    public Response signUpUser(Credentials credentials) {
        try {

            User user = new User();
            user.setCredentials(credentials);

            loginService.signUp(user);

            return Response.ok(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
