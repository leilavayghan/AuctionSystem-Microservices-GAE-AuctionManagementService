package serviceImplementation;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

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

public class testclient {

	public static Document convertStringToDocument(String xmlSource) 
			throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new InputSource(new StringReader(xmlSource)));
	}
	public static void test(String custID) throws Exception{

		String first = null;
		String last = null;
		String email = null;
		
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(UriBuilder.fromUri("http://regauthservice.appspot.com/rest/RegAuth/register/info/"+custID).build());

		Document doc = convertStringToDocument(service.accept(MediaType.APPLICATION_XML).get(String.class));
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("user");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				first = eElement.getElementsByTagName("firstname").item(0).getTextContent();
				last = eElement.getElementsByTagName("lastname").item(0).getTextContent();
				email = eElement.getElementsByTagName("email").item(0).getTextContent();

			}
		}
		
		WebResource service2 = client.resource(UriBuilder.fromUri("http://send-email-152320.appspot.com/?email="+email+"&name=newname").build());
		service2.get(String.class);
	}
	public static void main(String[] args) throws Exception {
		test("1");

	}

}
