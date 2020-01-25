package presentation.rest.trafficMonitoringController;

import application.trafficMonitoringService.TrafficMonitoringServiceLocal;
import data.dataModel.Coordinate;
import data.dataModel.Intersection;
import data.dataModel.Street;
import presentation.rest.ResponseBuilder;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;

@RequestScoped
@Path("/otm")
public class TrafficMonitoringController implements TrafficMonitoringControllerApi {

	@EJB
	private TrafficMonitoringServiceLocal trafficMonitoringService;

	@Override
	public Response shortestPath(long source, long destination, String type, boolean ignoreInterrupted) {
		if(source != 0 && destination != 0) {
			if (type.equals("Coordinate")) {
				ArrayList<Coordinate> coords;
				if(ignoreInterrupted)
					coords = trafficMonitoringService.shortestPathCoordinateIgnoreInterrupted(source, destination);
				else
					coords = trafficMonitoringService.shortestPathCoordinate(source, destination);
				return ResponseBuilder.createOkResponse(coords);
			}
			else if (type.equals("Intersection")) {
				ArrayList<Intersection> intersections;
				if(ignoreInterrupted)
					intersections = trafficMonitoringService.shortestPathIgnoreInterrupted(source, destination);
				else
					intersections = trafficMonitoringService.shortestPath(source, destination);

				return ResponseBuilder.createOkResponse(intersections);
			}
		}
		return Response.serverError().build();
	}

	@Override
	public Response criticalNodes(UriInfo info) {
		String top = info.getQueryParameters().getFirst("top");
		String threshold = info.getQueryParameters().getFirst("threshold");
		if (top != null && Integer.parseInt(top)>0) {
			ArrayList<Intersection> resp = trafficMonitoringService.getTopCriticalNodes(Integer.parseInt(top));

			return ResponseBuilder.createOkResponse(resp);
		}
		if (threshold != null && Integer.parseInt(threshold)>0) {
			ArrayList<Intersection> resp = trafficMonitoringService.getThresholdCriticalNodes(Integer.parseInt(threshold));

			return ResponseBuilder.createOkResponse(resp);
		}
		return Response.serverError().build();
	}

	@Override
	public Response nodesFlow(long osmid) {
		Intersection resp = trafficMonitoringService.getIntersection(osmid);
		if (resp == null) {
			return ResponseBuilder.createNotFoundResponse();
		}
		return ResponseBuilder.createOkResponse(resp);
	}

	@Override
	public Response getIntersection(long osmid) {
		Intersection i = trafficMonitoringService.getIntersection(osmid);
		return Response.ok().entity(i).build();
	}

	@Override
	public Response getNearestIntersection(float latitude, float longitude) {
		Intersection i = trafficMonitoringService.getNearestIntersection(new Coordinate(longitude, latitude));
		return ResponseBuilder.createOkResponse(i);
	}

	@Override
	public Response getStreetProperties(UriInfo info) {
		String id = info.getQueryParameters().getFirst("id");
		String osmidStart = info.getQueryParameters().getFirst("osmidStart");
		String osmidDest = info.getQueryParameters().getFirst("osmidDest");

		if (id != null) {
			Street s = trafficMonitoringService.getStreet(Integer.parseInt(id));
			return Response.ok().entity(s).build();
		}
		if (osmidStart != null && osmidDest != null) {
			int key = trafficMonitoringService.getLinkKey(Long.parseLong(osmidStart), Long.parseLong(osmidDest));
			Street s = trafficMonitoringService.getStreet(key);
			return Response.ok().entity(s).build();
		}
		return Response.serverError().build();
	}

	@Override
	public Response test(boolean ejb) {
		String test;
		try {
			if (ejb){
				test = "EJB not injected";
				if (trafficMonitoringService != null) {
					test = "EJB injected";
				}
			} else {
				test = "Test-string";
			}
			return ResponseBuilder.createOkResponse(test);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
