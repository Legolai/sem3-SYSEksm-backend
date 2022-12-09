package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.FoocleSpotAvailabeDTO;
import dtos.FoocleSpotDTO;
import dtos.SpotMenuDTO;
import errorhandling.API_Exception;
import errorhandling.GenericExceptionMapper;
import facades.FoocleScoutFacade;
import facades.FoocleSpotFacade;
import security.Permission;
import utils.EMF_Creator;
import utils.GsonLocalDateTime;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @POST
    @RolesAllowed(Permission.Types.BUSINESSADMIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/foocleSpot")
    public Response createNewFoocleSpot(String content) throws API_Exception {
        long businessAccountID;
        String address, city, zipCode, country;

        try {
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            businessAccountID = json.get("businessAccountID").getAsLong();

            address = json.get("address").getAsString();
            if (address.equals("")) { throw new Exception(": Empty Address!"); }
            city = json.get("city").getAsString();
            if (city.equals("")) { throw new Exception(": Empty City!"); }
            zipCode = json.get("zipCode").getAsString();
            if (zipCode.equals("")) { throw new Exception(": Empty zipCode!"); }
            country = json.get("country").getAsString();
            if (country.equals("")) { throw new Exception(": Empty Country!"); }
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Supplied"+e.getMessage(),400,e);
        }

        try {
            FoocleSpotDTO spot = SPOT_FACADE.createFoocleSpot(businessAccountID, address, city, zipCode, country);
            return Response.ok(GSON.toJson(spot)).build();

        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to create a new FoocleSpot!");
    }
    @GET
    @RolesAllowed({Permission.Types.BUSINESSADMIN, Permission.Types.BUSINESSACCOUNT})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/getFoocleSpot")
    public Response getAllFoocleSpotsForCVR(@PathParam("id") long id) throws API_Exception {
        try {
            List<FoocleSpotAvailabeDTO> spots = SPOT_FACADE.getFoocleSpotsForCVR(id);
            return Response.ok().entity(GSON.toJson(spots)).header(MediaType.CHARSET_PARAMETER, StandardCharsets.UTF_8.name()).build();
        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to get FoocleSpots!");
    }


    @POST
    @RolesAllowed({Permission.Types.BUSINESSACCOUNT, Permission.Types.BUSINESSADMIN})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/spotMenu")
    public Response createNewSpotMenu(String content) throws API_Exception {
        String description, pictures, foodpreferences;
        String pickupTimeFrom, pickupTimeTo;
        long fooclespotID;

        try {
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            description = json.get("description").getAsString();
            pictures = json.get("pictures").getAsString();
            foodpreferences = json.get("foodpreferences").getAsString();

            pickupTimeFrom = json.get("pickupTimeFrom").getAsString();
            pickupTimeTo = json.get("pickupTimeTo").getAsString();
            fooclespotID = json.get("fooclespotID").getAsLong();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Supplied",400,e);
        }

        try {
            SpotMenuDTO business = SPOT_FACADE.createSpotMenu(description, pictures, foodpreferences, LocalDateTime.parse(pickupTimeFrom), LocalDateTime.parse(pickupTimeTo), fooclespotID);
            return Response.ok(GSON.toJson(business)).build();

        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to create a new FoocleSpot!");
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
