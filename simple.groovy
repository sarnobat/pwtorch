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

private static String getWadeKellerHotline() throws CloneNotSupportedException, IOException,
		FeedException {
	SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(
			"http://www.pwtorch.com/pwtorchvipaudio.xml")));
	SyndFeed wkHotlineFeed = (SyndFeed) feed.clone();
	for (Object o : feed.getEntries()) {
		SyndEntry entry = (SyndEntry) o;
		if (!entry.getTitle().contains("otline")) {
			wkHotlineFeed.getEntries().remove(o);
		}
	}
	ByteArrayOutputStream bao = new ByteArrayOutputStream();
	OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bao);
	new SyndFeedOutput().output(wkHotlineFeed, outputStreamWriter);
	outputStreamWriter.close();
	return new String(bao.toByteArray());
}


System.out.println(getWadeKellerHotline());