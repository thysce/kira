package com.trense.kira;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.File;
import java.io.IOException;

public class ShutdownServlet extends HttpServlet {

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("/boot/shutdown shutdown triggered");
		ProcessBuilder pb = new ProcessBuilder("stop.bat");
		pb.environment().clear();
		pb.environment().put("JRE_HOME", System.getenv("JRE_HOME"));
		pb.redirectErrorStream(true);
		pb.redirectOutput(new File("logs/stop.log"));
		pb.start();
        response.getOutputStream().print("{\"shutting down\":true}");
    }
}