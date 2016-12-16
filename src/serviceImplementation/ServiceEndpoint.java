package serviceImplementation;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/manage")
public class ServiceEndpoint {

	AuctionManager manager = new AuctionManager();
	
	@GET
	@Path("/info")
	@Produces(MediaType.APPLICATION_XML)
	public List<Product> findAllProducts() throws Exception{
		
		return manager.getAllProducts();
	
	}
	
	@GET
	@Path("/timer")
	@Produces(MediaType.APPLICATION_XML)
	public void checkAd() throws Exception{
		
		manager.checkProducts();
		
	}
	
}
