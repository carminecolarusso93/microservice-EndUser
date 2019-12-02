package application.ejb;



import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import application.interceptors.ProfileInterceptor;
import data.dataModel.Coordinate;
import data.dataModel.Intersection;
import data.dataModel.Street;
import data.databaseDriver.DriverDatabase;
import data.databaseDriver.DriverDatabaseNeo4j;
import util.ServerUtilities;

/**
 * Session Bean implementation class BigServiceController
 * <p>
 * Encapsulates the driver to access the traffic management database with common user role.
 */

//@Interceptors({ProfileInterceptor.class})
@Stateless
public class TrafficMonitoringService implements TrafficMonitoringServiceLocal, TrafficMonitoringServiceRemote{

	DriverDatabase database;
	protected String databeseURI = null;
	protected String databaseUser = null;
	protected String databasePass = null; 
    /**
     * Default constructor. 
     */
    public TrafficMonitoringService() {
		try {
			ServerUtilities serverUtilities = new ServerUtilities();
			this.databeseURI = serverUtilities.getDatabaseUri();
			this.databaseUser = serverUtilities.getDatabaseUser();
			this.databasePass = serverUtilities.getDatabasePass();
			database = new DriverDatabaseNeo4j(databeseURI, databaseUser, databasePass);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }

	
	/**
	 * Called after the EJB construction.
	 * Open the connection to the database.
	 */
	@PostConstruct
	public void connect() {
		database.openConnection();
	}
	
	/**
	 * Called before the EJB destruction.
	 * Close the connection to the database.
	 */
	@PreDestroy
	public void preDestroy() {
		database.closeConnection();
	}
    
	@Override
	public String test() {
		try {
			ServerUtilities serverUtilities = new ServerUtilities();
			return serverUtilities.getDatabaseUri();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
    
//	@Override
//	public ArrayList<Long> shortestPath(long osmidStart, long osmidDest) {
//		return database.shortestPath(osmidStart, osmidDest);
//		}
    
	@Override
	public ArrayList<Coordinate> shortestPathCoordinate(long osmidStart, long osmidDest) {
		return database.shortestPathCoordinate(osmidStart, osmidDest);
		}

	@Override
	public ArrayList<Intersection> getTopCriticalNodes(int top) {
		return database.getTopCriticalNodes(top);
	}

	@Override
	public ArrayList<Intersection> getThresholdCriticalNodes(double threshold) {
		return database.getThresholdCriticalNodes(threshold);
	}

	@Override
	public double nodeFlow(long osmid) {
		return database.nodeFlow(osmid);
	}
	
	@Override
	public Intersection getIntersection(long osmid) {
		return database.getIntersectionLight(osmid);
	}

	@Override
	public Street getStreet(int id) {
		return database.getStreet(id);
	}
	
	@Override
	public int getLinkKey(long osmidStart, long osmidDest) {
		return database.getLinkKey(osmidStart, osmidDest);
	}

	@Override
	public ArrayList<Long> shortestPath(long osmidStart, long osmidDest) {
		return database.shortestPath(osmidStart, osmidDest);
	}
}
