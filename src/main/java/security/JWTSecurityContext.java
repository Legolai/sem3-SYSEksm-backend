package security;
import java.security.Principal;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;
public class JWTSecurityContext implements SecurityContext {
   AccountPrincipal accountPrincipal;
   ContainerRequestContext request;

   public JWTSecurityContext(AccountPrincipal accountPrincipal, ContainerRequestContext request) {
       this.accountPrincipal = accountPrincipal;
       this.request = request;
   }
   @Override
   public boolean isUserInRole(String permission) {
       return accountPrincipal.hasPermission(Permission.valueOf(permission));
   }
   @Override
   public boolean isSecure() {
       return request.getUriInfo().getBaseUri().getScheme().equals("https");
   }
   @Override
   public Principal getUserPrincipal() {
       return accountPrincipal;
   }
   @Override
   public String getAuthenticationScheme() {
       return "JWT"; //Only for INFO
   }
}