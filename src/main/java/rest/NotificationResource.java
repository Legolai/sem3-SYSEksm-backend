package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jwt.SignedJWT;
import dtos.NotificationDTO;
import entities.Account;
import entities.Notification;
import facades.FoocleBusinessFacade;
import facades.FoocleScoutFacade;
import facades.FoocleSpotFacade;
import facades.NotificationFacade;
import security.Permission;
import utils.EMF_Creator;
import utils.GsonLocalDateTime;

import javax.annotation.security.PermitAll;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

@Path("/notification")
public class NotificationResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final NotificationFacade NOTIFICATION_FACADE = NotificationFacade.getInstance(EMF);
    public static final FoocleBusinessFacade BUSINESS_FACADE = FoocleBusinessFacade.getFoocleBusinessFacade(EMF);
    public static final FoocleScoutFacade SCOUT_FACADE = FoocleScoutFacade.getFoocleScoutFacade(EMF);
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTime()).setPrettyPrinting().create();

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@HeaderParam("x-access-token") String token) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        String id = signedJWT.getJWTClaimsSet().getClaim("ID").toString();
        Permission permission = Permission.valueOf(signedJWT.getJWTClaimsSet().getClaim("pms").toString());
        long accountId;
        if (permission == Permission.FOOCLESCOUT) accountId = SCOUT_FACADE.getAccountId(Long.parseLong(id));
        else accountId = BUSINESS_FACADE.getAccountId(Long.parseLong(id));

        List<NotificationDTO> list = NOTIFICATION_FACADE.getNotificationsForAccount(accountId);
        return Response.ok().entity(list).header(MediaType.CHARSET_PARAMETER, StandardCharsets.UTF_8.name()).build();
    }
}