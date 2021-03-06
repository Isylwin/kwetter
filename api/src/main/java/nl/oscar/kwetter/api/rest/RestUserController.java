package nl.oscar.kwetter.api.rest;

import com.jnape.palatable.lambda.adt.Either;
import nl.oscar.kwetter.api.JwtTokenNeeded;
import nl.oscar.kwetter.api.ResponseUtility;
import nl.oscar.kwetter.domain.User;
import nl.oscar.kwetter.domain.UserInformation;
import nl.oscar.kwetter.service.error.ServerError;
import nl.oscar.kwetter.service.user.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

@Stateless
@Produces({MediaType.APPLICATION_JSON})
@Path("user")
public class RestUserController {

    @Inject
    private UserService service;


    @GET
    @JwtTokenNeeded
    public Response getAllUsers(@Context UriInfo uriInfo) {
        Either<ServerError, Collection<User>> optUsers = service.getAllUsers();

        optUsers.peek(u -> u.forEach(n -> n.addLink(uriInfo.getBaseUriBuilder()
                .path(RestKwetterController.class)
                .path("author")
                .path(Long.toString(n.getId()))
                .build().toString(), "Kwetters")));

        optUsers.peek(u -> u.forEach(n -> n.addLink(uriInfo.getBaseUriBuilder()
                .path(RestUserController.class)
                .path(Long.toString(n.getId()))
                .path("followers")
                .build().toString(), "followers")));

        optUsers.peek(u -> u.forEach(n -> n.addLink(uriInfo.getBaseUriBuilder()
                .path(RestUserController.class)
                .path(Long.toString(n.getId()))
                .path("following")
                .build().toString(), "following")));

        return ResponseUtility.getResponseFromEither(optUsers);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JwtTokenNeeded
    public Response addUser(User user) {
        Either<ServerError, User> optUser = service.addUser(user);

        return ResponseUtility.getResponseFromEither(optUser);
    }

    @GET
    @Path("/{id}")
    @JwtTokenNeeded
    public Response getUser(@PathParam("id") long id, @Context UriInfo uriInfo) {
        Either<ServerError, User> optUser = service.getUser(id);

        optUser.peek(u -> u.addLink(uriInfo.getBaseUriBuilder()
                .path(RestKwetterController.class)
                .path("author")
                .path(Long.toString(u.getId()))
                .build().toString(), "Kwetters"));

        optUser.peek(u -> u.addLink(uriInfo.getBaseUriBuilder()
                .path(RestUserController.class)
                .path(Long.toString(u.getId()))
                .path("followers")
                .build().toString(), "followers"));

        optUser.peek(u -> u.addLink(uriInfo.getBaseUriBuilder()
                .path(RestUserController.class)
                .path(Long.toString(u.getId()))
                .path("following")
                .build().toString(), "following"));

        return ResponseUtility.getResponseFromEither(optUser);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @JwtTokenNeeded
    @Path("/{id}")
    public Response editUserInfo(@PathParam("id") long id, UserInformation information) {
        Either<ServerError, User> optUser = service.updateUserInfo(id, information);

        return ResponseUtility.getResponseFromEither(optUser);
    }

    @GET
    @Path("/{id}/followers")
    public Response getFollowers(@PathParam("id") long id, @Context UriInfo uriInfo) {
        Either<ServerError, Collection<User>> optUsers = service.getFollowersOfUser(id);

        optUsers.peek(u -> u.forEach(n -> n.addLink(uriInfo.getBaseUriBuilder()
                .path(RestKwetterController.class)
                .path("author")
                .path(Long.toString(n.getId()))
                .build().toString(), "Kwetters")));

        optUsers.peek(u -> u.forEach(n -> n.addLink(uriInfo.getBaseUriBuilder()
                .path(RestUserController.class)
                .path(Long.toString(n.getId()))
                .path("followers")
                .build().toString(), "followers")));

        optUsers.peek(u -> u.forEach(n -> n.addLink(uriInfo.getBaseUriBuilder()
                .path(RestUserController.class)
                .path(Long.toString(n.getId()))
                .path("following")
                .build().toString(), "following")));


        return ResponseUtility.getResponseFromEither(optUsers);
    }

    @GET
    @Path("/{id}/following")
    public Response getFollowing(@PathParam("id") long id, @Context UriInfo uriInfo) {
        Either<ServerError, Collection<User>> optUsers = service.getFollowingOfUser(id);

        optUsers.peek(u -> u.forEach(n -> n.addLink(uriInfo.getBaseUriBuilder()
                .path(RestKwetterController.class)
                .path("author")
                .path(Long.toString(n.getId()))
                .build().toString(), "Kwetters")));

        optUsers.peek(u -> u.forEach(n -> n.addLink(uriInfo.getBaseUriBuilder()
                .path(RestUserController.class)
                .path(Long.toString(n.getId()))
                .path("followers")
                .build().toString(), "followers")));

        optUsers.peek(u -> u.forEach(n -> n.addLink(uriInfo.getBaseUriBuilder()
                .path(RestUserController.class)
                .path(Long.toString(n.getId()))
                .path("following")
                .build().toString(), "following")));

        return ResponseUtility.getResponseFromEither(optUsers);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JwtTokenNeeded
    @Path("/{id}/followers")
    public Response followUser(@PathParam("id") Long follower, @QueryParam("followee") Long followee) {
        Either<ServerError, User> result = service.followUser(follower, followee);

        return ResponseUtility.getResponseFromEither(result);
    }
}
