package security;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dtos.AccountTokenDTO;
import dtos.FoocleScoutDTO;
import facades.FoocleScoutFacade;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import errorhandling.API_Exception;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import security.errorhandling.AuthenticationException;
import errorhandling.GenericExceptionMapper;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.SecurityContext;

import utils.EMF_Creator;

@Path("login")
public class LoginEndpoint {

    public static final int TOKEN_EXPIRE_TIME = 1000 * 60 * 30; //30 min
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final FoocleScoutFacade SCOUT_FACADE = FoocleScoutFacade.getFoocleScoutFacade(EMF);

    @Context
    SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/scout")
    public Response scoutLogin(String jsonString) throws AuthenticationException, API_Exception {
        String email;
        String password;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            email = json.get("email").getAsString();
            password = json.get("password").getAsString();
        } catch (Exception e) {
           throw new API_Exception("Malformed JSON Suplied",400,e);
        }

        try {
            FoocleScoutDTO scout = SCOUT_FACADE.getVeryfiedScout(email, password);
            String token = createToken(new AccountTokenDTO(scout.getAccountId(), scout.getEmail(), scout.getFirstname(), scout.getLastname()), Permission.FOOCLESCOUT);
            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("email", email);
            responseJson.addProperty("token", token);
            return Response.ok(new Gson().toJson(responseJson)).build();

        } catch (JOSEException | AuthenticationException ex) {
            if (ex instanceof AuthenticationException) {
                throw (AuthenticationException) ex;
            }
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new AuthenticationException("Invalid username or password! Please try again");
    }
    private String createToken(AccountTokenDTO account, Permission permission) throws JOSEException {
        String issuer = "semesterstartcode-dat3";

        JWSSigner signer = new MACSigner(SharedSecret.getSharedKey());
        Date date = new Date();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(account.getEmail())
                .claim("pms", permission)
                .claim("fname", account.getFirstname())
                .claim("lname", account.getLastname())
                .claim("email", account.getEmail())
                .claim("issuer", issuer)
                .issueTime(date)
                .expirationTime(new Date(date.getTime() + TOKEN_EXPIRE_TIME))
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    @HEAD
    @Path("validate")
    @PermitAll
    public Response validateToken() {
        return Response.ok().build();
    }
}
