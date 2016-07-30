import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.json.JSONObject;

import com.neovisionaries.ws.client.*;


public class bot {
	
	public static void main(String[] args) throws Exception {
		bot http = new bot();

		//System.out.println("Enviando Get para obtener wss");
		//http.sendGet();
		//System.out.println("Intentemos conectarnos a API RTM ");
		ByteArrayOutputStream temp = new ByteArrayOutputStream();
		PrintStream sysin = new PrintStream(temp);
		PrintStream old = System.out;
		System.setOut(sysin);
		
		http.rtmConnection(sendGet(), temp, old);
}
	
	public static String sendGet() throws Exception {
		final String USER_AGENT = "Mozilla/5.0";

		final String channel = "U033ZD3LR";
		//zettamix U033ZD3LR - anime_lovers G0F0QGSN5 zettabot D1GDGLD16
		final String message = "shi";
		final boolean asUser = true;
		//final String coreURL = "https://slack.com/api/chat.postMessage?token="+tokenBot+"&channel="+channel+"&text="+message+"&as_user="+asUser;
		final String tokenBot = "xoxb-50461497911-kB6Lk2JG8m6zKWdpbZyFVgYB";
		final String startURL = "https://slack.com/api/rtm.start?token="+tokenBot+"&pretty=1";
		
		
		URL obj = new URL(startURL + tokenBot);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'GET' request to URL : " + startURL);
		//System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		
		JSONObject json = new JSONObject(response.toString());
		String wss = json.getString("url");
		//System.out.println(wss);
		return wss;

	}
	
	public void rtmConnection(String response, ByteArrayOutputStream temp, PrintStream old) throws Exception{
		JSONObject sendMsj = new JSONObject();
		sendMsj.accumulate("id", "1")
		.accumulate("type", "message")
		.accumulate("channel", "D1GDGLD16")
		.accumulate("text", "jojo");
		
		WebSocket ws = connect(response);
		//System.out.println(sendMsj.toString());
		//System.out.flush();
	    //System.setOut(old);
		//System.out.println(":D" + temp.toString() + ":D");
		while(!temp.toString().equals("")){
		String jeje = temp.toString();
		if(temp.toString().equals("Exito")){
			ws.sendText(sendMsj.toString());
			System.out.flush();
		    System.setOut(old);
			System.out.println("Msj sended :D");
		}
		}
		
	}
	
	private static WebSocket connect(String response) throws IOException, WebSocketException
    {
		
        return new WebSocketFactory()
            .createSocket(response)
            .addListener(new WebSocketAdapter() {
                // A text message arrived from the server.
                public void onTextMessage(WebSocket websocket, String message) {
                	JSONObject filter = new JSONObject(message);
                	//System.out.println(message);
                	//System.out.println(filter.getString("channel")+"__"+filter.getString("type"));                	
                	if(filter.getString("type").equals("message") && filter.getString("channel").equals("D1GDGLD16")){
                    System.out.println("Exito");
                	}
                }
            })
            .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
            .connect();
    }

}
