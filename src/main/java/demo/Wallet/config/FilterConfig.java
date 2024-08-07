package demo.Wallet.config;

import demo.Wallet.filter.CustomAuthFilter;
import demo.Wallet.filter.Slf4jMDCFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CustomAuthFilter> customAuthFilter() {
        FilterRegistrationBean<CustomAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomAuthFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1); // Ensuring it's the first filter
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<Slf4jMDCFilter> slf4jMDCFilter() {
        FilterRegistrationBean<Slf4jMDCFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new Slf4jMDCFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(2); // Setting order for MDC filter
        return registrationBean;
    }
}
