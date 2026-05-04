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

@WebServlet(urlPatterns = { "/api/login" })
public class LoginServlet extends HttpServlet {

    private final ServicioLogin servicioLogin = new ServicioLogin();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            LoginRequest loginRequest = gson.fromJson(request.getReader(), LoginRequest.class);
            if (loginRequest == null || loginRequest.getUsernameOrEmail() == null
                    || loginRequest.getPassword() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.escribirJson(response, "error", "Username y password son requeridos");
                return;
            }

            LoginResponse loginResponse = servicioLogin.loginConToken(loginRequest.getUsernameOrEmail(),
                    loginRequest.getPassword());
            if (loginResponse != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getWriter().write(gson.toJson(loginResponse));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                JsonUtil.escribirJson(response, "error", "Credenciales incorrectas");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(response, "error", "Error interno del servidor");
        }
    }
}