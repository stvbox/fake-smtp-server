package ru.gootsite;

import com.dumbster.smtp.SimpleSmtpServer;
import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.gootsite.server.MailsServiceImpl;
import ru.gootsite.utils.EmailUtil;

import java.io.IOException;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    @Bean
    Gson gson() {
        Gson gson = new Gson();
        MailsServiceImpl.setGson(gson);
        return gson;
    }

    @Bean
    SimpleSmtpServer simpleSmtpServer() throws IOException {
        int port = 2525;
        SimpleSmtpServer server = SimpleSmtpServer.start(port);
        MailsServiceImpl.setSmtpServer(server);
        EmailUtil.sendTestEmail("localhost", port);
        return server;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        WebMvcConfigurer.super.addViewControllers(registry);
        registry.addViewController( "/" ).setViewName( "forward:/Mails.html" );
        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
        WebMvcConfigurer.super.addViewControllers( registry );
    }

}