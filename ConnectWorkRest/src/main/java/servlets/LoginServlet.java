package servlets;


import models.LoginRequest;
import models.LoginResponse;
import services.ServicioLogin;
import utils.JsonUtil;
import java.io.IOException;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/api/login"})
public class LoginServlet extends HttpServlet {

    private final ServicioLogin servicioLogin = new ServicioLogin();
     private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            
            LoginRequest loginRequest = gson.fromJson(request.getReader(), LoginRequest.class);
            if (loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.escribirJson(response, java.util.Collections.singletonMap("error", "Username y password son requeridos"));
                return;
            }

            LoginResponse loginResponse = servicioLogin.loginConToken(loginRequest.getUsername(), loginRequest.getPassword());
            if (loginResponse != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.escribirJson(response, loginResponse);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                JsonUtil.escribirJson(response, java.util.Collections.singletonMap("error", "Credenciales incorrectas"));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(response, java.util.Collections.singletonMap("error", "Error interno del servidor"));
        }
    }
}