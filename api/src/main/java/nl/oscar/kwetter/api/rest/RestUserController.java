package nl.oscar.kwetter.api.rest;

import com.jnape.palatable.lambda.adt.Either;
import nl.oscar.kwetter.api.ResponseUtility;
import nl.oscar.kwetter.domain.User;
import nl.oscar.kwetter.domain.UserInformation;
import nl.oscar.kwetter.service.error.ServerError;
import nl.oscar.kwetter.service.user.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Stateless
@Produces({MediaType.APPLICATION_JSON})
@Path("user")
public class RestUserController {

    @Inject
    private UserService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        Either<ServerError, Collection<User>> optUsers = service.getAllUsers();

        return ResponseUtility.getResponseFromEither(optUsers);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        Either<ServerError, User> optUser = service.addUser(user);

        return ResponseUtility.getResponseFromEither(optUser);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getUser(@PathParam("id") long id) {
        Either<ServerError, User> optUser = service.getUser(id);

        return ResponseUtility.getResponseFromEither(optUser);
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response editUserInfo(@PathParam("id") long id, UserInformation information) {
        Either<ServerError, User> optUser = service.updateUserInfo(id, information);

        return ResponseUtility.getResponseFromEither(optUser);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/followers")
    public Response getFollowers(@PathParam("id") long id) {
        Either<ServerError, Collection<User>> optUsers = service.getFollowersOfUser(id);

        return ResponseUtility.getResponseFromEither(optUsers);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/following")
    public Response getFollowing(@PathParam("id") long id) {
        Either<ServerError, Collection<User>> optUsers = service.getFollowingOfUser(id);

        return ResponseUtility.getResponseFromEither(optUsers);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/followers")
    public Response followUser(@PathParam("id") Long follower, @QueryParam("followee") Long followee) {
        Either<ServerError, User> result = service.followUser(follower, followee);

        return ResponseUtility.getResponseFromEither(result);
    }
}
