package org.javaloong.kongmink.petclinic.customers.web;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.javaloong.kongmink.petclinic.customers.repository.PetRepository;
import org.javaloong.kongmink.petclinic.customers.web.MockMvcTestSupport.ColumnSensingReplacementDataSetLoader;
import org.javaloong.kongmink.petclinic.customers.web.MockMvcTestSupport.DbUnitTransactionTestExecutionListener;
import org.javaloong.kongmink.petclinic.customers.web.MockMvcTestSupport.ResetSequenceDatabaseOperationLookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TestExecutionListenerChain;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.AbstractDataSetLoader;
import com.github.springtestdbunit.operation.DefaultDatabaseOperationLookup;

@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        DbUnitTransactionTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader=ColumnSensingReplacementDataSetLoader.class,
        databaseOperationLookup=ResetSequenceDatabaseOperationLookup.class)
@Rollback(false)
public abstract class MockMvcTestSupport {

    @Autowired
    protected MockMvc mockMvc;
    
    @Autowired
    protected FormattingConversionService conversionService;
    
    @Configuration
    static class WebMvcConfig implements WebMvcConfigurer {
        
        private PetRepository pets;
        
        public WebMvcConfig(PetRepository pets) {
            this.pets = pets;
        }
        
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addFormatter(new PetTypeFormatter(pets));
        }
    }
    
    public static class DbUnitTransactionTestExecutionListener extends TestExecutionListenerChain {
        
        private static final Class<?>[] CHAIN = { DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class };

        @Override
        protected Class<?>[] getChain() {
            return CHAIN;
        }
    }

    public static class ColumnSensingReplacementDataSetLoader extends AbstractDataSetLoader {
        
        @Override
        protected IDataSet createDataSet(Resource resource) throws DataSetException, IOException {
            FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
            builder.setColumnSensing(true);
            try (InputStream inputStream = resource.getInputStream()) {
                return createReplacementDataSet(builder.build(inputStream));
            }
        }

        private ReplacementDataSet createReplacementDataSet(FlatXmlDataSet dataSet) {
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet);
            replacementDataSet.addReplacementObject("[null]", null);
            replacementDataSet.addReplacementObject("[now]", new Date());
            return replacementDataSet;
        }
    }
    
    public static class ResetSequenceDatabaseOperationLookup extends DefaultDatabaseOperationLookup {

        @Override
        public DatabaseOperation get(com.github.springtestdbunit.annotation.DatabaseOperation operation) {
            return new ResetSequenceOperationDecorator(super.get(operation));
        }
        
        public class ResetSequenceOperationDecorator extends DatabaseOperation {

            private DatabaseOperation decoree;

            public ResetSequenceOperationDecorator(DatabaseOperation decoree) {
                 this.decoree = decoree;
             }

             @Override
             public void execute(IDatabaseConnection connection, IDataSet dataSet) throws DatabaseUnitException, SQLException {
                 String[] tables = dataSet.getTableNames();
                 Statement statement = connection.getConnection().createStatement();
                 for (String table : tables) {
                     try {
                         String resetSql = String.format("ALTER TABLE %s ALTER COLUMN id RESTART WITH 1", table);
                         statement.execute(resetSql);
                     }
                     // Don't care because the sequence does not appear to exist (but catch it silently)
                     catch(SQLException ex) {
                     }
                 }
                 decoree.execute(connection, dataSet);
             }
        }
    }
}
