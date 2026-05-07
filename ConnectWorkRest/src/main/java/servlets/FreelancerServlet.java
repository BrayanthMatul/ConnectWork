package servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.Gson;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Freelancer;
import services.FreelancerServicio;
import utils.JsonUtil;
import utils.PermissionUtil;

@WebServlet(urlPatterns = { "/api/freelancer" })
public class FreelancerServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final FreelancerServicio freelancerServicio = new FreelancerServicio();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarFreelancer(req, res)) {
                return;
            }

            Freelancer freelancer = gson.fromJson(req.getReader(), Freelancer.class);
            freelancerServicio.registrarNuevoFreelancer(freelancer);
            res.setStatus(HttpServletResponse.SC_CREATED);
            JsonUtil.escribirJson(res, "message", "Datos iniciales registrados exitosamente");
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error", "Error al registrar el freelancer: " + e.getMessage());
        }
    }
}
