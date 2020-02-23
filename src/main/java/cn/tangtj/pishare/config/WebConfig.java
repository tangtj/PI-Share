package cn.tangtj.pishare.config;

import cn.tangtj.pishare.client.ClientService;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    @Bean
    public ServletListenerRegistrationBean getListener(ClientService clientService) {
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(clientService);
        return servletListenerRegistrationBean;
    }
}
