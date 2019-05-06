import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.File;

public class Server {

    public static void main( String... args ) throws IOException, InterruptedException, URISyntaxException {
		System.out.println( "start at " + System.currentTimeMillis() );
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        HttpClient httpClient = clientBuilder.build();

        String port = System.getenv( "PORT" );
        String javaHome = System.getenv( "JRE_HOME" );

        String uri = "http://localhost:" + port + "/boot/online";

        System.out.println( "Using following URI to test server up: " + uri );

        final HttpGet test = new HttpGet( new URI( uri ) );
        HttpResponse response = null;
        try {
            response = httpClient.execute( test );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        if ( response == null || response.getStatusLine().getStatusCode() != 200 ) {
            System.out.println("Server not up. Starting server...");
            start( javaHome, port );
        }
        else {
            System.out.println("Server up. Opening Browser...");
            openBrowser( port );
        }
    }

    public static void start( String javaHome, String port ) throws IOException {
        ProcessBuilder pb = new ProcessBuilder( "start.bat" );
        pb.environment().put( "JRE_HOME", javaHome );
        pb.environment().put( "PORT", port + "" );
        pb.environment().put( "TIMESTAMP", System.currentTimeMillis() + "" );
        pb.redirectErrorStream( true );
        pb.redirectOutput( ProcessBuilder.Redirect.appendTo( new File( "logs/start-" + System.currentTimeMillis() + ".log" ) ) );
        Process p = pb.start();
    }

    public static void openBrowser( String port ) throws IOException {
		ProcessBuilder pb = new ProcessBuilder( "../browser/chrome.exe",
				"--user-data-dir=../browser/user",
				"--window-size=1280,720",
                "--disable-sync",
                "--disable-translate",
                "--fast",
                "--fast-start",
                "--no-first-run",
                "--disable-infobars",
                "--disable-session-crashed-bubble",
                "--app=http://localhost:" + port + "/boot/index.html"
		);
		pb.redirectErrorStream( true );
        pb.redirectOutput( ProcessBuilder.Redirect.appendTo( new File( "logs/chrome-" + System.currentTimeMillis() + ".log" ) ) );
        Process p = pb.start();
	}

}