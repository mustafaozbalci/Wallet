package demo.Wallet.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@Slf4j
public class Slf4jMDCFilter extends OncePerRequestFilter {

    private static final String MDC_UUID_TOKEN_KEY = "Slf4jMDCFilter.UUID";
    private static final String ERROR_FORMAT = "Exception occurred in filter while setting UUID for logs: {}";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            MDC.put(MDC_UUID_TOKEN_KEY, UUID.randomUUID().toString());
            chain.doFilter(request, response);
        } catch (Exception ex) {
            log.error(ERROR_FORMAT, ex.getMessage(), ex);
        } finally {
            MDC.remove(MDC_UUID_TOKEN_KEY);
        }
    }

    @Override
    protected boolean isAsyncDispatch(HttpServletRequest request) {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}
