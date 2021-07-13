package cn.tedu.knows.auth.filter;


import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter {
    public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";

    public static final String OPTIONS = "OPTIONS";

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (isCorsRequest(httpRequest)) {
            httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpResponse.setHeader("Access-Control-Allow-Methods",
                    "POST, GET, PUT, DELETE");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            // response.setIntHeader("Access-Control-Max-Age", 1728000);
            httpResponse
                    .setHeader(
                            "Access-Control-Allow-Headers",
                            "Origin, X-Requested-With, Content-Type, Accept, Accept-Encoding, Authorization");
            if (isPreFlightRequest(httpRequest)) {
                return;
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

    public boolean isCorsRequest(HttpServletRequest request) {
        return (request.getHeader(HttpHeaders.ORIGIN) != null);
    }

    /**
     * Returns {@code true} if the request is a valid CORS pre-flight one.
     */
    public boolean isPreFlightRequest(HttpServletRequest request) {
        return (isCorsRequest(request) && OPTIONS.equals(request.getMethod()) && request
                .getHeader(ACCESS_CONTROL_REQUEST_METHOD) != null);
    }
}
