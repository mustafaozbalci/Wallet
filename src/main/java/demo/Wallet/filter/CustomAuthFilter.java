package demo.Wallet.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CustomAuthFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String payerUsername = request.getHeader("payerUsername");
        String payerPassword = request.getHeader("payerPassword");

        if (payerUsername == null || payerPassword == null) {
            logger.warn("Payer username or password is missing in the headers");
        } else {
            logger.info("Received payerUsername: {}", payerUsername);
        }

        filterChain.doFilter(request, response);
    }
}
