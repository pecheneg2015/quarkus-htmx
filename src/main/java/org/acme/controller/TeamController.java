package org.acme.controller;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.acme.entity.Team;
import org.acme.service.TeamService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/teams")
public class TeamController {
    @Inject
    TeamService teamService;
    @Inject
    @Location("team/main.html")
    Template main;

    @Inject
    @Location("team/modal.html")
    Template modal;

    @Inject
    @Location("team/updateModal.html")
    Template updateModal;

    @Inject
    @Location("team/item.html")
    Template item;

    @GET
    public Response get() {
        URI uri = UriBuilder.fromPath("/").build();
        return Response.temporaryRedirect(uri).build();
    }

    @GET
    @Path("/{code}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@PathParam("code") String code) {
        List<Team> items = teamService.getTeamsByCode(code);
        return main.data("data", items).data("code",code);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteCountryItem(@PathParam("id") String id) {
        teamService.removeTeamById(id);
        return "";
    }

    @GET
    @Path("/{code}/add/form")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getCreateModal(@PathParam("code") String code) {
        return modal.data("id", "id".concat(UUID.randomUUID().toString())).data("code",code);
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance createItem(@FormParam("id") String id,@FormParam("countryCode") String countryCode,@FormParam("name") String name,@FormParam("foundationYear") String foundationYear) {
        Team team = new Team(id,name,Integer.parseInt(foundationYear),countryCode);
        teamService.addTeam(team);
        return item.data("item", team);
    }

    @GET
    @Path("/{id}/update/form")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getUpdateModal(@PathParam("id") String id) {
        Team team = teamService.getTeamById(id);
        return updateModal.data("item",team);
    }
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance updateItem(@FormParam("id") String id,@FormParam("countryCode") String countryCode,@FormParam("name") String name,@FormParam("foundationYear") String foundationYear) {
        Team team = new Team(id,name,Integer.parseInt(foundationYear),countryCode);
        teamService.updateTeam(team);
        return item.data("item", team);
    }
}
