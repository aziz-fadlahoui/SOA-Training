package ressources;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import entities.Credentials;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// NOTE: the Postman screenshot uses "authentification" (typo). Your URL must match this value.
@Path("authentication")
public class AuthenticationEndPoint {

    @Context
    private UriInfo uriInfo;

    // Solution 1: FormParam  (Postman: Body -> x-www-form-urlencoded)
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password) {
        return doAuthenticate(username, password);
    }

    // Solution 2: JSON Credentials  (Postman: Body -> raw -> JSON)
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials cred) {
        return doAuthenticate(cred.getUsername(), cred.getPassword());
    }

    private Response doAuthenticate(String username, String password) {
        try {
            authenticate(username, password);
            String token = issueToken(username);
            return Response.ok(token).build();
        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        // TODO (left to the student): check against a database, LDAP, file...
        // Throw an Exception if the credentials are invalid.
        // Minimal example so the lab works (replace with real check):
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new Exception("Invalid credentials");
        }
        System.out.println("Authenticating user...");
    }

    private String issueToken(String username) {
        // Same key as in AuthenticationFilter (same string, same "DES")
        String keyString = "simplekey";
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");

        String jwtToken = Jwts.builder()
                .setSubject(username)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        System.out.println("the returned token is : " + jwtToken);
        return jwtToken;
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}