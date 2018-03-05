package nl.oscar.kwetter.api.rest;

import com.jnape.palatable.lambda.adt.Either;
import nl.oscar.kwetter.api.ResponseUtility;
import nl.oscar.kwetter.domain.Kwetter;
import nl.oscar.kwetter.service.error.ServerError;
import nl.oscar.kwetter.service.kwetter.KwetterService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Stateless
@Produces({MediaType.APPLICATION_JSON})
@Path("kwetter")
public class RestKwetterController {

    @Inject
    private KwetterService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/author/{author}")
    public Response addKwetter(@PathParam("author") long author, String text) {
        Either<ServerError, Kwetter> result = service.addKwetter(author, text);

        return ResponseUtility.getResponseFromEither(result);
    }

    @GET
    @Path("/author/{author}")
    public Response getKwettersForAuthor(@PathParam("author") long author) {
        Either<ServerError, Collection<Kwetter>> result = service.getKwettersForAuthor(author);

        return ResponseUtility.getResponseFromEither(result);
    }


}
