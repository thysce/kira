package com.trense.kira;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

public class OnlineServlet extends HttpServlet {

    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getOutputStream().print("{\"online\":true}");
    }

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getOutputStream().print("{\"online\":true}");
    }
}