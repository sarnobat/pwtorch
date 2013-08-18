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

// TODO: Add Pat McNeill and Flashback and livecast, Parks
@Path("helloworld")
public class HelloWorldResource {


	@Path("hello")
	@GET
	@Produces("text/plain")
	public String getHello() {
		return "Hello World";
	}

	@Path("wkh")
	@GET
	@Produces("text/plain")
	public Response wkh() {
		return  Response.ok(getFeed("http://www.pwtorch.com/pwtorchvipaudio.xml", "otline", "lashback")).build();
	}

	@Path("int")
	@GET
	@Produces("text/plain")
	public Response interview() {
		return  Response.ok(getFeed("http://www.pwtorch.com/pwtorchvipaudio.xml", "Livecast Interview", "foobar")).build();
	}

	@Path("jc")
	@GET
	@Produces("text/plain")
	public Response jamesCaldwell() {
		return  Response.ok(getFeed("http://www.pwtorch.com/pwtorchvipaudio.xml", "Caldwell", "foobar")).build();
	}

	@Path("bm")
	@GET
	@Produces("text/plain")
	public Response bm() {
		return  Response.ok(getFeed("http://www.pwtorch.com/pwtorchvipaudio.xml", "Bruce Mitchell Audio", "foobar")).build();
	}

	
        @Path("greg")
        @GET
        @Produces("text/plain")
        public Response greg() {
                return  Response.ok(getFeed("http://www.pwtorch.com/pwtorchvipaudio.xml", "arks", "aldwell")).build();
        }

        @Path("flashback")
        @GET
        @Produces("text/plain")
        public Response flashback() {
                return  Response.ok(getFeed("http://www.pwtorch.com/pwtorchvipaudio.xml", "lashback", "foobar")).build();
        }

        @Path("livecast")
        @GET
        @Produces("text/plain")
        public Response livecast() {
                return  Response.ok(getFeed("http://www.pwtorch.com/pwtorchvipaudio.xml", "ivecast", "foobar")).build();
        }

        @Path("pat")
        @GET
        @Produces("text/plain")
        public Response patMcneill() {
                return  Response.ok(getFeed("http://www.pwtorch.com/pwtorchvipaudio.xml", "eill", "foobar")).build();
        }

        @Path("powell")
        @GET
        @Produces("text/plain")
        public Response jamesPowell() {
                return  Response.ok(getFeed("http://www.pwtorch.com/pwtorchvipaudio.xml", "owell", "foobar")).build();
        }

	@Path("test")
	@GET
	@Produces("text/plain")
	public Response test() {
		return  Response.ok(getFeed("http://www.pwtorch.com/pwtorchvipaudio.xml", "otline", "lashback")).build();
	}
	
	private String getFeed(String url, String include, String exclude) throws CloneNotSupportedException, IOException,
		FeedException {
		SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(
				url)));
		SyndFeed wkHotlineFeed = (SyndFeed) feed.clone();
		for (Object o : feed.getEntries()) {
			SyndEntry entry = (SyndEntry) o;
			if (entry.getTitle().contains(include)) {
				if (!entry.getTitle().contains(exclude)) {
					continue;
				}
			}
			wkHotlineFeed.getEntries().remove(o);
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
