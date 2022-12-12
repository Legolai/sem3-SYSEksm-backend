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
        NotificationFacade notificationFacade = NotificationFacade.getInstance(emf);
        notificationFacade.createNotification(10, "Hey a new request has been made!", "");

    }
    
    public static void main(String[] args) {
        populate();
    }
}
