package application.trafficMonitoringService;

import java.util.ArrayList;

import javax.ejb.Remote;

import data.dataModel.*;

/**
 * Remote Interface for the EJB that implements the common user Service of the
 * road network.
 *
 * @author Giovanni Codianni
 * @author Carmine Colarusso
 * @author Chiara Verdone
 */

@Remote
public interface TrafficMonitoringServiceRemote {

	/**
	 * Returns the shortest path from an Intersection to another, both specified by
	 * given vertexKeys.
	 * <p>
	 *
	 * @param osmidStart Id of the starting Intersection in the road network.
	 * @param osmidDest  Id of the destination Intersection in the road network.
	 * @return an ordered ArrayList of Coordinate that identifies Intersections that
	 *         belong to the shortest path.
	 */
	ArrayList<Coordinate> shortestPathCoordinate(long osmidStart, long osmidDest);

	/**
	 * Returns the shortest path from an Intersection to another, both specified by
	 * given vertexKeys.
	 *
	 * @param osmidStart Id of the starting Intersection in the road network.
	 * @param osmidDest  Id of the destination Intersection in the road network.
	 * @return an ordered ArrayList Intersections that belong
	 *         to the shortest path.
	 */
	ArrayList<Intersection> shortestPath(long osmidStart, long osmidDest);

	/**
	 * Returns a list of top critical intersections ordered by betweenness
	 * centrality.
	 *
	 * @param top is the number of critical Intersections to display.
	 * @return an ArrayList of osmid that identify the critical Intersections.
	 */
	ArrayList<Intersection> getTopCriticalNodes(int top);

	/**
	 * Returns a list of critical intersections that have a betweenness centrality
	 * greater than the indicated threshold.
	 *
	 * @param threshold is the value to compare.
	 * @return an arrayList of osmid that identify the critical Intersections.
	 */
	ArrayList<Intersection> getThresholdCriticalNodes(double threshold);

	/**
	 * Returns the flow in an Intersection adding the weights of Street coming out
	 * of the intersection identified by given vertexKey.
	 *
	 * @param osmid Id of the intersection in the road network.
	 * @return the flow in given Intersection.
	 */
	double nodeFlow(long osmid);

	/**
	 * Returns linkKey of the street between two different intersection identified
	 * by given vertexKeys.
	 *
	 * @param osmidStart Id of the starting intersection in the road network.
	 * @param osmidDest  Id of the destination intersection in the road network.
	 * @return id of street.
	 */
	int getLinkKey(long osmidStart, long osmidDest);

	/**
	 * Returns the Intersection with given Id.
	 *
	 * @param osmid Id of the Intersection to find.
	 * @param streets if set to true method returns also the street coming out from intersection
	 * @return The Java representation of the searched Intersection.
	 */
	Intersection getIntersection(long osmid, boolean streets);

	/**
	 * Returns the Street with given Id.
	 *
	 * @param id Id of the Street to find.
	 * @return The Java representation of the searched Street.
	 */
	Street getStreet(int id);

	Intersection getNearestIntersection(Coordinate position);

	ArrayList<Coordinate> shortestPathCoordinateIgnoreInterrupted(long osmidStart, long osmidDest);

	ArrayList<Intersection> shortestPathIgnoreInterrupted(long osmidStart, long osmidDest);
}
