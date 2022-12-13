package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.*;
import errorhandling.API_Exception;
import errorhandling.GenericExceptionMapper;
import facades.FoocleBusinessFacade;
import facades.FoocleScoutFacade;
import facades.NotificationFacade;
import security.Permission;
import utils.EMF_Creator;
import utils.GsonLocalDateTime;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/business")
public class FoocleBusinessResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final FoocleBusinessFacade BUSINESS_FACADE = FoocleBusinessFacade.getFoocleBusinessFacade(EMF);
    public static final FoocleScoutFacade SCOUT_FACADE = FoocleScoutFacade.getFoocleScoutFacade(EMF);
    public static final NotificationFacade NOTIFICATION_FACADE = NotificationFacade.getInstance(EMF);
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTime()).setPrettyPrinting().create();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewBusiness(String content) throws API_Exception {
        String cvr, name, businessEmail, businessPhoneNumber, description;
        String address, city, zipCode, country;
        String firstname, lastname, businessAccountEmail, phoneNumber, password;

        try {
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            cvr = json.get("cvr").getAsString();
            name = json.get("name").getAsString();
            description = json.get("description").getAsString();
            businessEmail = json.get("businessEmail").getAsString();
            businessPhoneNumber = json.get("businessPhone").getAsString();

            address = json.get("address").getAsString();
            city = json.get("city").getAsString();
            zipCode = json.get("zipCode").getAsString();
            country = json.get("country").getAsString();

            firstname = json.get("firstname").getAsString();
            lastname = json.get("lastname").getAsString();
            businessAccountEmail = json.get("email").getAsString();
            password = json.get("password").getAsString();

            phoneNumber = json.get("phone").getAsString();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Supplied",400,e);
        }

        try {
            FoocleBusinessDTO business = BUSINESS_FACADE.createBusiness(cvr, name, businessEmail, businessPhoneNumber, description, address, city, zipCode, country, businessAccountEmail, phoneNumber, firstname, lastname, password);
            return Response.ok(GSON.toJson(business)).build();

        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to create a new FoocleBusiness!");
    }


    @GET
    @Path("/{id}/request")
    @RolesAllowed({Permission.Types.BUSINESSACCOUNT, Permission.Types.BUSINESSADMIN})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRequestsForBusiness(@PathParam("id") long id) {
        List<ScoutRequestMenuDTO> list = BUSINESS_FACADE.getAllRequests(id);
        return Response.ok().entity(GSON.toJson(list)).header(MediaType.CHARSET_PARAMETER, StandardCharsets.UTF_8.name()).build();
    }
    @GET
    @Path("/{id}/relevantRequest")
    @RolesAllowed({Permission.Types.BUSINESSACCOUNT, Permission.Types.BUSINESSADMIN})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRelevantRequestsForBusiness(@PathParam("id") long id) {
        List<ScoutRequestMenuDTO> list = BUSINESS_FACADE.getAllRelevantRequests(id);
        return Response.ok().entity(GSON.toJson(list)).header(MediaType.CHARSET_PARAMETER, StandardCharsets.UTF_8.name()).build();
    }
    @PUT
    @Path("/request")
    @RolesAllowed({Permission.Types.BUSINESSACCOUNT, Permission.Types.BUSINESSADMIN})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRequestStatus(String content) throws API_Exception {
        long id, scoutID;
        String status;

        try {
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            id = json.get("id").getAsLong();
            scoutID = json.get("scoutID").getAsLong();
            status = json.get("status").getAsString();

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Supplied",400,e);
        }

        try {
            BUSINESS_FACADE.updateRequestStatus(id, status);
            NOTIFICATION_FACADE.createNotification(SCOUT_FACADE.getAccountId(scoutID), String.format("Request nr. %s have been: %s",id,status), "");
            return Response.ok().build();

        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to update ScoutRequest(s)!");
    }



}
