package cake;

import static java.lang.System.out;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class LastModifiedTest
{
	public static void main(String[] args) throws Exception {
		String urlToRead = "http://gaia.cs.umass.edu/wireshark-labs/HTTP-wireshark-file2.html";
		URL url = new URL(urlToRead);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		Date cacheLastModified = new Date(conn.getLastModified()), requestDate = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss zzz");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		out.println("Cache-Last-Modified : " + dateFormat.format(cacheLastModified) + "\n" + "Request-Date        : " + dateFormat.format(requestDate));
		
		out.println(requestDate.before(cacheLastModified));
		
		// check the dates to determine code to return (this happens regardless of hit or miss)
        // if request date is before the cache lookup date, then the cache stuff is newer than the client's stuff
        //          return a 200
		out.println((requestDate.before(cacheLastModified)) ? 200 : 304);
	}
}
