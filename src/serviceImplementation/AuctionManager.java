package serviceImplementation;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class AuctionManager {


	public List<Product> getAllProducts() throws Exception{

		Product product = new Product();
		List<Product> productlist = new ArrayList<>();

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		client.setReadTimeout(200000000);
		WebResource service = client.resource(UriBuilder.fromUri("http://advertisementservice-149821.appspot.com/rest/ad/info/all").build());

		Document doc = convertStringToDocument(service.accept(MediaType.APPLICATION_XML).get(String.class));
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("product");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);

			System.out.println("\nCurrent Element :" + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				product = new Product(eElement.getElementsByTagName("custID").item(0).getTextContent()
						,eElement.getElementsByTagName("productID").item(0).getTextContent()
						,eElement.getElementsByTagName("name").item(0).getTextContent()
						,eElement.getElementsByTagName("brand").item(0).getTextContent()
						,eElement.getElementsByTagName("itemcondition").item(0).getTextContent()
						,eElement.getElementsByTagName("status").item(0).getTextContent()
						,Integer.parseInt(eElement.getElementsByTagName("startingPrice").item(0).getTextContent())
						,eElement.getElementsByTagName("startDate").item(0).getTextContent()
						,eElement.getElementsByTagName("sellDate").item(0).getTextContent()
						,eElement.getElementsByTagName("winnerID").item(0).getTextContent()
						,eElement.getElementsByTagName("description").item(0).getTextContent()
						,Integer.parseInt(eElement.getElementsByTagName("sellPrice").item(0).getTextContent()));

				productlist.add(product);

			}
		}

		return productlist;
	}

	public static Document convertStringToDocument(String xmlSource) 
			throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new InputSource(new StringReader(xmlSource)));
	}

	public String getHighestBidder(String productID) throws Exception{
		
		String[] winner = new String[3];

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		client.setReadTimeout(200000000);
		WebResource service = client.resource(UriBuilder.fromUri("http://biddingservice-150217.appspot.com/rest/bid/info/"+productID).build());

		Document doc = convertStringToDocument(service.accept(MediaType.APPLICATION_XML).get(String.class));
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("bid");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);

			System.out.println("\nCurrent Element :" + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				
				//there are no bids for this product
				if(eElement.getElementsByTagName("price").item(0).getTextContent().equals("0")){
					winner[0] = "-1";
					winner[1] = eElement.getElementsByTagName("productID").item(0).getTextContent();
					winner[2] = "-1";
				}
				else{
					
					
					winner[0] = eElement.getElementsByTagName("custID").item(0).getTextContent();
					winner[1] = eElement.getElementsByTagName("productID").item(0).getTextContent();
					winner[2] = eElement.getElementsByTagName("price").item(0).getTextContent();
				}
			}
		}

		notifyAdvertisement(winner);
		return "found it";

	}

	public void notifyAdvertisement(String[] input) throws Exception{

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		client.setReadTimeout(200000000);
		
		if(input[0].equals("-1")){
			WebResource service = client.resource(UriBuilder.fromUri("http://advertisementservice-149821.appspot.com/rest/ad/start/"+input[1]).build());

			service.accept(MediaType.TEXT_PLAIN).get(String.class);
		}
		else{
			WebResource service = client.resource(UriBuilder.fromUri("http://advertisementservice-149821.appspot.com/rest/ad/update").build());
			Product winner = new Product();

			winner.setWinnerID(input[0]);
			winner.setProductID(input[1]);
			winner.setSellPrice(Integer.parseInt(input[2]));

			service.type(MediaType.APPLICATION_XML).post(String.class,winner);
		}
	}

	public void checkProducts() throws Exception {
		Product product = new Product();
		List<Product> productlist = new ArrayList<>();

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		client.setReadTimeout(200000000);
		WebResource service = client.resource(UriBuilder.fromUri("http://advertisementservice-149821.appspot.com/rest/ad/ended").build());

		Document doc = convertStringToDocument(service.accept(MediaType.APPLICATION_XML).get(String.class));
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("product");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);

			System.out.println("\nCurrent Element :" + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				product = new Product(eElement.getElementsByTagName("custID").item(0).getTextContent()
						,eElement.getElementsByTagName("productID").item(0).getTextContent()
						,eElement.getElementsByTagName("name").item(0).getTextContent()
						,eElement.getElementsByTagName("brand").item(0).getTextContent()
						,eElement.getElementsByTagName("itemcondition").item(0).getTextContent()
						,eElement.getElementsByTagName("status").item(0).getTextContent()
						,Integer.parseInt(eElement.getElementsByTagName("startingPrice").item(0).getTextContent())
						,eElement.getElementsByTagName("startDate").item(0).getTextContent()
						,eElement.getElementsByTagName("sellDate").item(0).getTextContent()
						,eElement.getElementsByTagName("winnerID").item(0).getTextContent()
						,eElement.getElementsByTagName("description").item(0).getTextContent()
						,Integer.parseInt(eElement.getElementsByTagName("sellPrice").item(0).getTextContent()));

				productlist.add(product);
			}
		}
		
		for (int i = 0; i < productlist.size(); i++) {
			getHighestBidder(productlist.get(i).getProductID());
		}
		
	}

}
