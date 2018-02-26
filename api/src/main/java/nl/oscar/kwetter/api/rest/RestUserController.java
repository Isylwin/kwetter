package nl.oscar.kwetter.api.rest;

import com.jnape.palatable.lambda.adt.Either;
import nl.oscar.kwetter.api.ResponseUtility;
import nl.oscar.kwetter.domain.User;
import nl.oscar.kwetter.service.ServerError;
import nl.oscar.kwetter.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Produces({MediaType.APPLICATION_JSON})
@Path("user")
public class RestUserController {

    @Inject
    private UserService service;

    @GET
    public Response getAllUsers() {
        return Response.ok(service.getAllUsers()).build();
    }

    /*@GET
    @Path("/name/{name}")
    public Response getUser(@PathParam("name") String name) {
        Optional<User> optUser = service.getUser(name);

        if(optUser.isPresent()) {
            User user = optUser.get();
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("\"error\":\"No user with the name " + name + " could be found.\"")
                    .build();
        }
    }*/

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") long id) {
        Either<ServerError, User> optUser = service.getUser(id);

        return ResponseUtility.getResponseFromEither(optUser);

        /*if(optUser.isPresent()) {
            User user = optUser.get();
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("\"error\":\"No user with the id " + id + " could be found.\"")
                    .build();
        }*/
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/follow")
    public Response followUser(@PathParam("id") Long follower, @QueryParam("followee") Long followee) {
        Either<ServerError, User> result = service.followUser(follower, followee);

        return ResponseUtility.getResponseFromEither(result);
    }


}
