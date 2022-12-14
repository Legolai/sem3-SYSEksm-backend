package rest;

import com.google.gson.*;
import dtos.FoocleScoutDTO;
import dtos.MadeScoutRequestDto;
import dtos.NotificationDTO;
import dtos.ScoutRequestDTO;
import entities.Notification;
import errorhandling.API_Exception;
import errorhandling.GenericExceptionMapper;
import facades.FoocleBusinessFacade;
import facades.FoocleScoutFacade;
import facades.NotificationFacade;
import facades.ScoutRequestFacade;
import security.Permission;
import utils.EMF_Creator;
import utils.GsonLocalDateTime;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/scout")
public class FoocleScoutResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final FoocleScoutFacade SCOUT_FACADE = FoocleScoutFacade.getFoocleScoutFacade(EMF);
    public static final ScoutRequestFacade SCOUT_REQUEST_FACADE = ScoutRequestFacade.getScoutRequestFacade(EMF);
    public static final NotificationFacade NOTIFICATION_FACADE = NotificationFacade.getInstance(EMF);
    public static final FoocleBusinessFacade BUSINESS_FACADE = FoocleBusinessFacade.getFoocleBusinessFacade(EMF);
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTime()).setPrettyPrinting().create();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewScout(String content) throws API_Exception {
        String email, phoneNumber;
        String firstname, lastname, password;
        try {
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            email = json.get("email").getAsString();
            phoneNumber = json.get("phoneNumber").getAsString();
            firstname = json.get("firstname").getAsString();
            lastname = json.get("lastname").getAsString();
            password = json.get("password").getAsString();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Supplied",400,e);
        }

        try {
            FoocleScoutDTO scout = SCOUT_FACADE.createScout(email, phoneNumber, firstname, lastname, password);
            return Response.ok(GSON.toJson(scout)).build();

        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to create a new FoocleScout account!");
    }

    @POST
    @Path("/request")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewRequest(String content) throws API_Exception {
        long spotmenuId, fooclescoutId, businessAccountID;

        try {
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            spotmenuId = json.get("spotmenuID").getAsLong();
            fooclescoutId = json.get("fooclescoutID").getAsLong();
            businessAccountID = json.get("businessAccountID").getAsLong();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Supplied", 400, e);
        }

        try {
            ScoutRequestDTO scoutRequestDTO = SCOUT_REQUEST_FACADE.createScoutRequest(spotmenuId, fooclescoutId);
            if (scoutRequestDTO.getId() != null) {
                long accountID = BUSINESS_FACADE.getAccountId(businessAccountID);
                NOTIFICATION_FACADE.createNotification(accountID, "Hey a new request has been made!", "Contact scout");
            }

            return Response.ok(GSON.toJson(scoutRequestDTO)).build();

        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to create a new FoocleScout account!");

    }

    @GET
    @Path("{id}/request")
    @RolesAllowed(Permission.Types.FOOCLESCOUT)
    public Response getRequests(@PathParam("id") long id){
        List<MadeScoutRequestDto> requestDTOList = SCOUT_REQUEST_FACADE.getAllForScout(id);
        return Response.ok().entity(GSON.toJson(requestDTOList)).header(MediaType.CHARSET_PARAMETER, StandardCharsets.UTF_8.name()).build();
    }
}
