package demo.Wallet.config;

import demo.Wallet.filter.Slf4jMDCFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public FilterRegistrationBean<Slf4jMDCFilter> servletRegistrationBean() {
        FilterRegistrationBean<Slf4jMDCFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new Slf4jMDCFilter());
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }
}
