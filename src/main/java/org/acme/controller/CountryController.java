package org.acme.controller;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.entity.Country;
import org.acme.service.CountryService;

import java.util.List;

@Path("/country")
public class CountryController {
    @Inject
    CountryService countryService;
    @Inject
    @Location("country/main.html")
    Template main;


    @Inject
    @Location("country/modal.html")
    Template modal;

    @Inject
    @Location("country/updateModal.html")
    Template updateModal;

    @Inject
    @Location("country/item.html")
    Template item;


    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        List<Country> items = countryService.getALlCountries();
        return main.data("data", items);
    }


    @DELETE
    @Path("/{code}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteCountryItem(@PathParam("code") String code) {
        countryService.removeCountry(code);
        return "";
    }

    @GET
    @Path("/add/form")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getCreateModal() {
        return modal.instance();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance createItem(@FormParam("name") String name,@FormParam("code") String code) {
        Country country = new Country(code,name);
        countryService.addCountry(country);
        return item.data("country", country);
    }

    @GET
    @Path("/update/form/{code}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getUpdateModal(@PathParam("code") String code) {
        Country country = countryService.getCountryByCode(code);
        return updateModal.data("item",country);
    }
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance updateItem(@FormParam("name") String name,@FormParam("code") String code) {
        Country country = new Country(code,name);
        countryService.updateCountry(country);
        return item.data("country", country);
    }
}
