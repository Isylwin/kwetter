package nl.oscar.kwetter.api.rest;

import com.jnape.palatable.lambda.adt.Either;
import nl.oscar.kwetter.api.JwtTokenNeeded;
import nl.oscar.kwetter.api.ResponseUtility;
import nl.oscar.kwetter.domain.Kwetter;
import nl.oscar.kwetter.service.error.ServerError;
import nl.oscar.kwetter.service.kwetter.KwetterService;

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
@Path("kwetter")
public class RestKwetterController {

    @Inject
    private KwetterService service;

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @JwtTokenNeeded
    @Path("/author/{author}")
    public Response addKwetter(@PathParam("author") long author, String text) {
        Either<ServerError, Kwetter> result = service.addKwetter(author, text);

        return ResponseUtility.getResponseFromEither(result);
    }

    @GET
    @Path("/")
    public Response getAllKwetters() {
        Either<ServerError, Collection<Kwetter>> result = service.getAllKwetters();

        return ResponseUtility.getResponseFromEither(result);
    }

    @GET
    @Path("/author/{author}")
    public Response getKwettersForAuthor(@PathParam("author") long author, @Context UriInfo uriInfo) {
        Either<ServerError, Collection<Kwetter>> result = service.getKwettersForAuthor(author);

        result.peek(u -> u.forEach(n -> n.addLink(uriInfo.getBaseUriBuilder()
                .path(RestUserController.class)
                .path(Long.toString(n.getAuthor()))
                .build()
                .toString(), "author")));

        return ResponseUtility.getResponseFromEither(result);
    }

    @GET
    @Path("/{id}")
    public Response getKwetter(@PathParam("id") long id, @Context UriInfo uriInfo) {
        Either<ServerError, Kwetter> result = service.getKwetter(id);

        result.peek(u -> u.addLink(uriInfo.getBaseUriBuilder()
                .path(RestUserController.class)
                .path(Long.toString(u.getAuthor()))
                .build()
                .toString(), "author"));

        return ResponseUtility.getResponseFromEither(result);
    }

    @GET
    @Path("/mention/{mention_id}")
    public Response getKwettersWithMention(@PathParam("mention_id") long mention, @Context UriInfo uriInfo) {
        Either<ServerError, Collection<Kwetter>> result = service.getKwettersWithMention(mention);

        result.peek(u -> u.forEach(n -> n.addLink(uriInfo.getBaseUriBuilder()
                .path(RestUserController.class)
                .path(Long.toString(n.getAuthor()))
                .build()
                .toString(), "author")));

        return ResponseUtility.getResponseFromEither(result);
    }

    @GET
    @Path("/topic/{topic_id : .+}")
    public Response getKwettersWithTopic(@PathParam("topic_id") String topic, @Context UriInfo uriInfo) {
        Either<ServerError, Collection<Kwetter>> result = service.getKwettersWithTopic(topic);

        result.peek(u -> u.forEach(n -> n.addLink(uriInfo.getBaseUriBuilder()
                .path(RestUserController.class)
                .path(Long.toString(n.getAuthor()))
                .build()
                .toString(), "author")));

        return ResponseUtility.getResponseFromEither(result);
    }
}
