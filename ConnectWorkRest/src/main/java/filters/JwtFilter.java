package filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.JwtUtil;
import java.io.IOException;

@WebFilter(urlPatterns = { "/api/*" })
public class JwtFilter implements Filter {

    // Rutas públicas que NO requieren validación de token
    private static final String[] PUBLIC_URLS = {
            "/api/login",
            "/api/registro"
    };

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        // Permitir OPTIONS y rutas públicas
        if ("OPTIONS".equalsIgnoreCase(method) || isPublicUrl(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Permitir POST a /api/perfil sin token (registro de nuevos usuarios)
        if ("POST".equalsIgnoreCase(method) && path.contains("/api/perfil")) {
            chain.doFilter(request, response);
            return;
        }

        // Para todas las demás rutas, validar token
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"error\": \"Token no proporcionado\"}");
            return;
        }

        // Extraer el token sin el prefijo "Bearer "
        String token = authHeader.substring(7);

        // Validar el token
        if (!JwtUtil.validarToken(token)) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"error\": \"Token inválido o expirado\"}");
            return;
        }

        // Si el token es válido, extraer el username y agregarlo a la request
        String username = JwtUtil.extraerSubject(token);
        httpRequest.setAttribute("username", username);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    /**
     * Verifica si la URL es pública y no requiere autenticación
     */
    private boolean isPublicUrl(String path) {
        for (String publicUrl : PUBLIC_URLS) {
            if (path.contains(publicUrl)) {
                return true;
            }
        }
        return false;
    }
}
