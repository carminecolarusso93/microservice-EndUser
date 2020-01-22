package presentation.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public interface TrafficMonitoringControllerApi {

	// recupero dei percorsi minimi tra i nodi a e b
	// GET /networkX/shortestPaths?source=a&destination=b
	@GET
	@Path("/shortestPaths")
	public Response shortestPath(@QueryParam("source") long source, @QueryParam("destination") long destination,
								 @DefaultValue("Coordinate") @QueryParam("type") String type,
								 @DefaultValue("true") @QueryParam("ignoreInterrupted") boolean ignoreInterrupted);

	// recupero degli Y nodi più critici
	// GET /networkX/criticalNodes?top=Y
	// GET /networkX/criticalNodes?threshold=Z
	@GET
	@Path("/criticalNodes")
	public Response criticalNodes(@Context UriInfo info);

	// recupero del flusso che attraverso il nodo nodeId
	// GET /networkX/nodesFlow/nodeId
	@GET
	@Path("nodesFlow/{osmid}")
	public Response nodesFlow(@PathParam("osmid") long osmid);

	@GET
	@Path("/test")
	public Response test(@QueryParam("ejb") boolean ejb);

	@GET
	@Path("/intersections")
	public Response getIntersection(@QueryParam("osmid") long osmid);

	@GET
	@Path("/intersections/nearest")
	public Response getNearestIntersection(@QueryParam("latitude") float latitude, @QueryParam("longitude") float longitude);

	@GET
	@Path("/streets")
	public Response getStreetProperties(@Context UriInfo info);

}
