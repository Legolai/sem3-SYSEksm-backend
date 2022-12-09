package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.FoocleSpotAvailabeDTO;
import dtos.SpotMenuDTO;
import facades.FoocleScoutFacade;
import facades.FoocleSpotFacade;
import utils.EMF_Creator;
import utils.GsonLocalDateTime;

import javax.annotation.security.PermitAll;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Path("/spot")
public class FoocleSpotResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final FoocleSpotFacade SPOT_FACADE = FoocleSpotFacade.getInstance(EMF);
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTime()).setPrettyPrinting().create();

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<FoocleSpotAvailabeDTO> list = SPOT_FACADE.getAllFoocleSpots();
        return Response.ok().entity(GSON.toJson(list)).header(MediaType.CHARSET_PARAMETER, StandardCharsets.UTF_8.name()).build();
    }

    @GET
    @Path("/{id}/menu")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMenusForSpot(@PathParam("id") long id) {
        List<SpotMenuDTO> list = SPOT_FACADE.getAllMenusForSpot(id);
        return Response.ok().entity(GSON.toJson(list)).header(MediaType.CHARSET_PARAMETER, StandardCharsets.UTF_8.name()).build();
    }
}
