package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.FoocleBusinessDTO;
import dtos.FoocleScoutDTO;
import dtos.FoocleSpotDTO;
import dtos.SpotMenuDTO;
import errorhandling.API_Exception;
import errorhandling.GenericExceptionMapper;
import facades.FoocleBusinessFacade;
import facades.FoocleScoutFacade;
import security.Permission;
import utils.EMF_Creator;
import utils.GsonLocalDateTime;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/business")
public class FoocleBusinessResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final FoocleBusinessFacade BUSINESS_FACADE = FoocleBusinessFacade.getFoocleBusinessFacade(EMF);
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
            businessEmail = json.get("businessEmail").getAsString();
            description = json.get("description").getAsString();
            businessPhoneNumber = json.get("businessPhoneNumber").getAsString();

            address = json.get("address").getAsString();
            city = json.get("city").getAsString();
            zipCode = json.get("zipCode").getAsString();
            country = json.get("country").getAsString();

            firstname = json.get("firstname").getAsString();
            lastname = json.get("lastname").getAsString();
            businessAccountEmail = json.get("businessAccountEmail").getAsString();
            password = json.get("password").getAsString();

            phoneNumber = json.get("phoneNumber").getAsString();
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

    @POST
    @RolesAllowed(Permission.Types.BUSINESSADMIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/foocleSpot")
    public Response createNewFoocleSpot(String content) throws API_Exception {
        String businessAccountID, cvr;
        String address, city, zipCode, country;

        try {
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            businessAccountID = json.get("businessAccountID").getAsString();
            cvr = json.get("cvr").getAsString();

            address = json.get("address").getAsString();
            city = json.get("city").getAsString();
            zipCode = json.get("zipCode").getAsString();
            country = json.get("country").getAsString();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Supplied",400,e);
        }

        try {
            FoocleSpotDTO business = BUSINESS_FACADE.createFoocleSpot(businessAccountID, cvr, address, city, zipCode, country);
            return Response.ok(GSON.toJson(business)).build();

        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to create a new FoocleSpot!");
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
            SpotMenuDTO business = BUSINESS_FACADE.createSpotMenu(description, pictures, foodpreferences, LocalDateTime.parse(pickupTimeFrom), LocalDateTime.parse(pickupTimeTo), fooclespotID);
            return Response.ok(GSON.toJson(business)).build();

        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("Failed to create a new FoocleSpot!");
    }



}