package org.iotacontrolcenter.ui.proxy.http;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.iotacontrolcenter.dto.*;

/**
 * Service for ICCR public ReST API
 **/
@Path("/iccr/rs")
public interface IccrService {

    @GET
    @Path("/app/config")
    @Produces(MediaType.APPLICATION_JSON)
    Response getConfigProperties();

    @GET
    @Path("/app/config/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getConfigProperty(@DefaultValue("") @PathParam("key") String key);

    @PUT
    @Path("/app/config")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateConfigProperties(IccrPropertyListDto properties);

    @PUT
    @Path("/app/config/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateConfigProperty(@DefaultValue("") @PathParam("key") String key, IccrPropertyDto prop);

    @POST
    @Path("/iota/cmd/{action}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response doIotaAction(@DefaultValue("") @PathParam("action") String action);

    @GET
    @Path("/iota/neighbors")
    @Produces(MediaType.APPLICATION_JSON)
    Response getIotaNeighbors();

    @GET
    @Path("/iota/nodeinfo")
    @Produces(MediaType.APPLICATION_JSON)
    Response getIotaNodeInfo();
}
