package by.jorjick.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by gora on 12/28/16.
 */
@Configuration
@EnableWebMvc
/*
   Spring Boot reference 27.1.1
   If you want to take complete control of Spring MVC,
   you can add your own @Configuration annotated with @EnableWebMvc
 */
public class WebConfig extends WebMvcConfigurerAdapter {

/*    @Value("${server.nonSSL.port}")
    int nonSSLPort;

    @Value("${server.nonSSL.urlPatterns}")
    Collection<String> nonSSLUrlPatterns;*/

    @Bean
    EmbeddedServletContainerFactory servletContainerFactory() {
    	TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory() {
    		@Override
    	    protected void postProcessContext(Context context) {
    	        SecurityConstraint securityConstraint = new SecurityConstraint();
    	        securityConstraint.setUserConstraint("CONFIDENTIAL");
    	        SecurityCollection collection = new SecurityCollection();
    	        collection.addPattern("/*");
    	        securityConstraint.addCollection(collection);
    	        context.addConstraint(securityConstraint);
    	    }
    	};
    	tomcatFactory.addAdditionalTomcatConnectors(initiateHttpConnector());
        return tomcatFactory;
    }
    
    private Connector initiateHttpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        
        return connector;
      }
    
/*    private Connector createSSLConnector() {
    	Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        try {
            File keystore = new ClassPathResource("keystore").getFile();
            File truststore = new ClassPathResource("keystore").getFile();
            connector.setScheme("https");
            connector.setSecure(true);
            connector.setPort(8443);
            protocol.setSSLEnabled(true);
            protocol.setKeystoreFile(keystore.getAbsolutePath());
            protocol.setKeystorePass("changeit");
            protocol.setTruststoreFile(truststore.getAbsolutePath());
            protocol.setTruststorePass("changeit");
            protocol.setKeyAlias("apitester");
            return connector;
        }
        catch (IOException ex) {
            throw new IllegalStateException("can't access keystore: [" + "keystore"
                    + "] or truststore: [" + "keystore" + "]", ex);
        }
    }*/


    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        return resolver;
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/error").setViewName("error");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/", "classpath:/META-INF/resources/webjars/");
        /*registry.addResourceHandler("/static*//**")
                .addResourceLocations("/webjars/");*/
    }


    @Bean
    ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setCookieName("locale");
        cookieLocaleResolver.setCookieMaxAge(365*24*60*60);
        cookieLocaleResolver.setCookieSecure(true);
        return cookieLocaleResolver;
    }



    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }


    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
        return objectMapper;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
