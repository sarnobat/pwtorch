import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;

import javax.ws.rs.core.Response
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.sun.net.httpserver.HttpServer;


@Path("helloworld")
public class HelloWorldResource {


	@Path("hello")
	@GET
	@Produces("text/plain")
	public String getHello() {
		return "Hello World";
	}


	@Path("test")
	@GET
	@Produces("text/plain")
	public Response test() {
		return  Response.ok(getWadeKellerHotline()).build();
	}
	
	private String getWadeKellerHotline() throws CloneNotSupportedException, IOException,
		FeedException {
		SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(
				"http://www.pwtorch.com/pwtorchvipaudio.xml")));
		SyndFeed wkHotlineFeed = (SyndFeed) feed.clone();
		for (Object o : feed.getEntries()) {
			SyndEntry entry = (SyndEntry) o;
			if (!entry.getTitle().contains("otline")) {
				wkHotlineFeed.getEntries().remove(o);
			} else {
				println(entry.getTitle());
			}
		}
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bao);
		new SyndFeedOutput().output(wkHotlineFeed, outputStreamWriter);
		outputStreamWriter.close();
		return new String(bao.toByteArray());
	}

}


String BASE_URI = "http://localhost:4444/";

ResourceConfig rc = new ResourceConfig(HelloWorldResource.class);
URI endpoint = new URI(BASE_URI);

HttpServer server = JdkHttpServerFactory.createHttpServer(endpoint, rc);
System.out.println("console v2.0 : Press Enter to stop the server. ");
System.in.read();
server.stop(0);