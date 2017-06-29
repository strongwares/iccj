package org.iotacontrolcenter.ui.proxy.http;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.iotacontrolcenter.dto.IccrIotaNeighborsPropertyDto;
import org.iotacontrolcenter.dto.IccrPropertyDto;
import org.iotacontrolcenter.dto.IccrPropertyListDto;

/**
 * Service for ICCR public ReST API
 **/
@Path("/iccr/rs")
public interface IccrService {

    @GET
    @Path("/app/eventlog")
    @Produces(MediaType.APPLICATION_JSON)
    Response getIccrEventLog();

    @DELETE
    @Path("/app/eventlog")
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteIccrEventLog();

    @GET
    @Path("/app/config")
    @Produces(MediaType.APPLICATION_JSON)
    Response getConfigProperties();

    @GET
    @Path("/app/config/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getConfigProperty(@DefaultValue("") @PathParam("key") String key);

    @GET
    @Path("/app/config/iota/nbrs")
    @Produces(MediaType.APPLICATION_JSON)
    Response getIotaNbrsConfig();

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

    @PUT
    @Path("/app/config/iota/nbrs")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateIotaNbrsConfig(IccrIotaNeighborsPropertyDto nbrs);

    @POST
    @Path("/iota/cmd/{action}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response doIotaAction(@DefaultValue("") @PathParam("action") String action, IccrPropertyListDto actionProps);

    @GET
    @Path("/iota/neighbors")
    @Produces(MediaType.APPLICATION_JSON)
    Response getIotaNeighbors();

    @GET
    @Path("/iota/nodeinfo")
    @Produces(MediaType.APPLICATION_JSON)
    Response getIotaNodeInfo();

    @GET
    @Path("/iota/log")
    @Produces(MediaType.APPLICATION_JSON)
    Response getIotaLog(@QueryParam("fileDirection") String fileDirection,
                        @QueryParam("numLines") Long numLines,
                        @QueryParam("lastFileLength") Long lastFileLength,
                        @QueryParam("lastFilePosition") Long lastFilePosition);

    @POST
    @Path("/iccr/cmd/{action}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response doIccrAction(@DefaultValue("") @PathParam("action") String action, IccrPropertyListDto actionProps);

}
