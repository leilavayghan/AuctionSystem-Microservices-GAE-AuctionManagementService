package serviceImplementation;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class timertest
{
    public static void main(String[] args)
    {
    	Product product = new Product();
		List<Product> productlist = new ArrayList<>();

				product = new Product("","1","","","","",0,"","","","",0);
				productlist.add(product);
				
				product = new Product("","2","","","","",0,"","","","",0);
				productlist.add(product);
				
				product = new Product("","3","","","","",0,"","","","",0);
				productlist.add(product);
				
				product = new Product("","4","","","","",0,"","","","",0);
				productlist.add(product);
				
		//System.out.println(productlist.get(1).getProductID());
		
		for (int i = 0; i < productlist.size(); i++) {
			System.out.println(productlist.get(i).getProductID());
		}

		
    }
}