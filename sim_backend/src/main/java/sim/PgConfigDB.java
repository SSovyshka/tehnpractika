package sim;

import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.sql.DataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableTransactionManagement
@EnableWebMvc
public class PgConfigDB extends WebMvcConfigurationSupport  {

   
    @Bean(name = "dataSourceBSP")
    public DataSource getDataSourcePg() throws DataSourceLookupFailureException {

        JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();

        return jndiDataSourceLookup.getDataSource("java:/DS_SIM");
    }
    
    @Bean(name = "hibernatePropertiesPg")
    public Properties getHibernatePropertiesPg() {

        Properties properties = new Properties();

        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "false");
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.generate_statistics", "false");

        return properties;
    }

   
    @Bean(name ="entityManagerFactoryPgCallCenter")
    @PersistenceUnit(unitName = "SIM")
    public EntityManagerFactory entityManagerFactoryPg() {

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(getDataSourcePg());

        // Classpath scanning of @Component, @Service, etc annotated class        
        emf.setPackagesToScan("sim");

        // Vendor adapter        
        //HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(jpaPgVendorAdapter());

        // add Hibernate properties
        emf.setJpaProperties(getHibernatePropertiesPg());

        emf.setPersistenceUnitName("SIM");
        emf.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        emf.afterPropertiesSet();

        return emf.getObject();
    }
   
 
    @Bean(name = "jpaPgVendorAdapter")
    public JpaVendorAdapter jpaPgVendorAdapter() {
        HibernateJpaVendorAdapter jpaPgVendorAdapter = new HibernateJpaVendorAdapter();
        jpaPgVendorAdapter.setShowSql(true);
        jpaPgVendorAdapter.setGenerateDdl(true);
        jpaPgVendorAdapter.setDatabase(Database.POSTGRESQL);
        return jpaPgVendorAdapter;
    }
    
    
    
    @Bean(name="transactionManagerPg")
    public PlatformTransactionManager transactionManagerPg() {

        JpaTransactionManager tx = new JpaTransactionManager();

        tx.setEntityManagerFactory(entityManagerFactoryPg());

        tx.setDataSource(getDataSourcePg());
        
        return tx;
    }
    
    
    @Bean(name = "EntityManagerPg")
    public EntityManager EntityManagerPg() {
        return entityManagerFactoryPg().createEntityManager();
    }
}
