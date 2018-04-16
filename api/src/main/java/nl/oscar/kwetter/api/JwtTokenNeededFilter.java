package nl.oscar.kwetter.api;

import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.security.Key;
import java.util.logging.Logger;

@Provider
@JwtTokenNeeded
@Priority(Priorities.AUTHENTICATION)
@Stateless
public class JwtTokenNeededFilter implements ContainerRequestFilter {

    private static final Logger logger = Logger.getLogger(JwtTokenNeededFilter.class.getName());

    @Inject
    private SimpleKeyGenerator keyGenerator;

    @Override
    public void filter(ContainerRequestContext requestContext) {

        requestContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        requestContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");

        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {

            // Validate the token
            Key key = keyGenerator.generateKey();
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            logger.info("#### valid token : " + token);

        } catch (Exception e) {
            logger.severe("#### invalid token : " + token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
