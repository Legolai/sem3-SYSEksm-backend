package security;

import entities.Account;

import java.security.Principal;

public class AccountPrincipal implements Principal {

    private String email;

    private String fname;
    private String lname;
    private Permission permission;

    /* Create a AccountPrincipal, given the Entity class User*/
    public AccountPrincipal(Account accont, Permission permission) {
        this.email = accont.getEmail();
        this.fname = accont.getFirstname();
        this.lname = accont.getLastname();
        this.permission = permission;
    }

    public AccountPrincipal(String email, String firstname, String lastname, Permission permission) {
        super();
        this.email = email;
        this.fname = firstname;
        this.lname = lastname;
        this.permission = permission;

    }

    @Override
    public String getName() {
        return email;
    }

    public boolean hasPermission(Permission permission) {
        boolean isAllowed = permission == this.permission;
        if (permission == Permission.FOOCLEBUSINESS && this.permission == Permission.BUSINESSADMIN) isAllowed = true;

        return isAllowed;
    }
}