package webservices;

import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ue")
public class UniteEnsRestApi {

    UniteEnseignementBusiness helper = new UniteEnseignementBusiness();

    // GET /ue/list
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListUe() {
        return Response
                .status(200)
                .entity(helper.getListeUE())
                .build();
    }

    // GET /ue/code/{code}
    @Path("/code/{code}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUeByCode(@PathParam("code") int code) {
        UniteEnseignement ue = helper.getUEByCode(code);
        if (ue == null) {
            return Response
                    .status(404)
                    .entity("Unité d'enseignement introuvable")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
        return Response
                .status(200)
                .entity(ue)
                .build();
    }

    // GET /ue/domaine/{domaine}
    @Path("/domaine/{domaine}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUeByDomaine(@PathParam("domaine") String domaine) {
        return Response
                .status(200)
                .entity(helper.getUEByDomaine(domaine))
                .build();
    }

    // GET /ue/semestre/{semestre}
    @Path("/semestre/{semestre}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUeBySemestre(@PathParam("semestre") int semestre) {
        return Response
                .status(200)
                .entity(helper.getUEBySemestre(semestre))
                .build();
    }

    // POST /ue/add
    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUe(UniteEnseignement ue) {
        return Response
                .status(200)
                .entity(helper.addUniteEnseignement(ue))
                .build();
    }

    // PUT /ue/update/{code}
    @Path("/update/{code}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateUe(@PathParam("code") int code, UniteEnseignement ue) {
        boolean updated = helper.updateUniteEnseignement(code, ue);
        if (updated) {
            return Response
                    .status(200)
                    .entity(updated)
                    .build();
        }
        return Response
                .status(404)
                .entity(updated)
                .build();
    }

    // DELETE /ue/delete/{code}
    @Path("/delete/{code}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUe(@PathParam("code") int code) {
        boolean deleted = helper.deleteUniteEnseignement(code);
        if (deleted) {
            return Response
                    .status(200)
                    .entity(deleted)
                    .build();
        }

        return Response
                .status(404)
                .entity(deleted)
                .build();
    }
}