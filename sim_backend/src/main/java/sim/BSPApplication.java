package sim;


import sim.config.Swagger2Config;
import javax.servlet.MultipartConfigElement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;



@SpringBootApplication
//@EnableAutoConfiguration(exclude = { JdbcTemplateAutoConfiguration.class,
//    SecurityAutoConfiguration.class,
//        DataSourceAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class,
//        JerseyServerMetricsAutoConfiguration.class})
@EnableConfigurationProperties(Swagger2Config.class)
//@EnableScheduling
//@ComponentScan("sim")
public class BSPApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BSPApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BSPApplication.class);
    }

    
    @Bean(name = "commonsMultipartResolver")
    public MultipartResolver multipartResolver() {
            return new StandardServletMultipartResolver();
    }
    
    @Bean
    public MultipartConfigElement multipartConfigElement() {
            MultipartConfigFactory factory = new MultipartConfigFactory();

            factory.setMaxFileSize(DataSize.ofBytes(1000000000));
            factory.setMaxRequestSize(DataSize.ofBytes(1000000000));

            return factory.createMultipartConfig();
    }
    
}
