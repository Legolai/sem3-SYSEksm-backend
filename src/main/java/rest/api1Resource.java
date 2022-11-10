package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.catDTO;
import utils.HttpUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

/**
 * @author lam@cphbusiness.dk
 */
@Path("api1")
public class api1Resource {

    @Context
    private UriInfo context;
    private String url = "https://api.thecatapi.com/v1/images/search";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCatUrl() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String res = HttpUtils.fetchData(url);
        catDTO[] catDTO = new Gson().fromJson(res, catDTO[].class);
        //catDTO catDTO = gson.fromJson(cat, catDTO.class);

        //This is what your endpoint should return
        String catPNGLink = gson.toJson(catDTO[0].getUrl());

        return catPNGLink;
    }


}