import java.io.*;
import com.neovisionaries.ws.client.*;

public class webSocketTest {
	
	/**
     * The echo server on websocket.org.
     */
    private static final String SERVER = "ws://echo.websocket.org";

    /**
     * The timeout value in milliseconds for socket connection.
     */
    private static final int TIMEOUT = 5000;


    /**
     * The entry point of this command line application.
     */
    public static void main(String[] args) throws Exception
    {
        // Connect to the echo server.
        WebSocket ws = connect();

        // The standard input via BufferedReader.
        BufferedReader in = getInput();

        // A text read from the standard input.
        String text;

        // Read lines until "exit" is entered.
        while ((text = in.readLine()) != null)
        {
            // If the input string is "exit".
            if (text.equals("exit"))
            {
                // Finish this application.
                break;
            }

            // Send the text to the server.
            ws.sendText(text);
        }

        // Close the web socket.
        ws.disconnect();
    }


    /**
     * Connect to the server.
     */
    private static WebSocket connect() throws IOException, WebSocketException
    {
        return new WebSocketFactory()
            .setConnectionTimeout(TIMEOUT)
            .createSocket(SERVER)
            .addListener(new WebSocketAdapter() {
                // A text message arrived from the server.
                public void onTextMessage(WebSocket websocket, String message) {
                    System.out.println(message);
                }
            })
            .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
            .connect();
    }


    /**
     * Wrap the standard input with BufferedReader.
     */
    private static BufferedReader getInput() throws IOException
    {
        return new BufferedReader(new InputStreamReader(System.in));
    }

}
