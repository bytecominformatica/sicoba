package br.com.clairtonluz.bytecom.config;


import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class LiquibaseEJB {
    private static final String STAGE = "development";
    private static final String CHANGELOG_FILE = "br/com/clairtonluz/bytecom/db/changelog.xml";
    private static final Logger LOGGER = Logger.getLogger(LiquibaseEJB.class.getName());

    @Resource(lookup = "java:/bytecomDS")
    private DataSource ds;

    @PostConstruct
    protected void startLiquiBean() {
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());
        try (Connection connection = ds.getConnection()) {
            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Database db = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);

            Liquibase liquiBase = new Liquibase(CHANGELOG_FILE, resourceAccessor, db);
            liquiBase.update(STAGE);
        } catch (SQLException | LiquibaseException e) {
            LOGGER.severe(e.getMessage());
        }
    }

}
