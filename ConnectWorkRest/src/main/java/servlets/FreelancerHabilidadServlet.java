package servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.Gson;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.FreelancerHabilidadServicio;
import utils.JsonUtil;
import utils.PermissionUtil;

@WebServlet(urlPatterns = { "/api/freelancer-habilidad" })
public class FreelancerHabilidadServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final FreelancerHabilidadServicio freelancerHabilidadServicio = new FreelancerHabilidadServicio();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            if (!PermissionUtil.verificarAutenticacion(req, res)) {
                return;
            }

            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json");
            gson.toJson(freelancerHabilidadServicio.obtenerTodo(), res.getWriter());
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.escribirJson(res, "error",
                    "Error al obtener las habilidades de los freelancers: " + e.getMessage());
        }
    }

}
