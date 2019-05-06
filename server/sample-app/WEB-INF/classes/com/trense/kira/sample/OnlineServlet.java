package com.trense.kira.sample;

import javax.servlet.http.*;
import javax.servlet.*;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class OnlineServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getOutputStream().write("{\"online\":true}".getBytes(StandardCharsets.UTF_8));
	}
	
}