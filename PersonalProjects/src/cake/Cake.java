package cake;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import cake.additives.CakeFrosting;
import cake.additives.CakeLayer;
import cake.additives.CakeShape;
import cake.additives.CakeTopping;


//remove suppress warning later
@SuppressWarnings("unused")
public class Cake
{
	public static class CakeBuilder
	{
		private List<CakeLayer> cakeLayers = new ArrayList<>();
		private CakeFrosting cakeFrosting;
		private CakeShape cakeShape;
		private List<CakeTopping> cakeTopping = new ArrayList<>();

		public CakeBuilder(CakeLayer... cakeLayers) {
			this.cakeLayers.addAll(Arrays.asList(cakeLayers));
		}
	}

	public static void main(String[] args) throws Exception {
		String urlToRead = "http://gaia.cs.umass.edu/wireshark-labs/HTTP-wireshark-file2.html";
		StringBuilder result = new StringBuilder();
		URL url = new URL(urlToRead);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss zzz");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		conn.addRequestProperty("If-Modified-Since", dateFormat.format(date));
		out.println(dateFormat.format(date));
		Map<String, List<String>> rawHeaders = conn.getHeaderFields();

		out.print("\n");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		Map<String, List<String>> headers = new HashMap<>();
		String[] desiredHeaders = { "Date", "Content-Type", "Content-Length", "Last-Modified" };
		for (String desiredHeader : desiredHeaders) {
			if (rawHeaders.containsKey(desiredHeader)) {
				headers.put(desiredHeader, rawHeaders.get(desiredHeader));
			}
		}

		rd.close();
		for (Entry<String, List<String>> header : headers.entrySet()) {
			out.print(header.getKey() + " : ");
			for (String headerField : header.getValue()) {
				out.print(headerField + " ");
			}
			out.print("\n");
		}

		out.println(result.toString());
		out.println(conn.getResponseCode());
		Date date2 = new Date(conn.getLastModified());
		out.println(dateFormat.format(date2));
	}
}
