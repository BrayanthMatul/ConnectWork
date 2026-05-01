package utils;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import models.Respuesta;

import java.io.IOException;

public class JsonUtil {
    private static final Gson gson = new Gson();

    public static void escribirJson(HttpServletResponse res, String clave, String valor) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        Respuesta respuesta = new Respuesta(clave, valor);
        res.getWriter().write(gson.toJson(respuesta));
    }
}
