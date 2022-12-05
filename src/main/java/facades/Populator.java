/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManagerFactory;

import entities.Location;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        Location location = new Location();
        location.setAddress("2 nybrovej");
        location.setCity("gentofte");
        location.setZipCode("2820");
        location.setCountry("danmark");
        FoocleSpotFacade foocleSpotFacade = FoocleSpotFacade.getInstance(emf);
        foocleSpotFacade.createFoocleSpot(1L, "1", location.getAddress(), location.getCity(), location.getZipCode(), location.getCountry());

    }
    
    public static void main(String[] args) {
        populate();
    }
}
