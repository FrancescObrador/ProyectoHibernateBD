package es.ua.eps.hibernate.multitenancy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.DatabaseConnectionInfo;
import org.hibernate.internal.util.PropertiesHelper;


public class SchemaMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

    private ConnectionProvider connectionProvider;

    public SchemaMultiTenantConnectionProvider() throws IOException{
        Properties props = new Properties( null );
        props.load(getClass().getResourceAsStream("/multi-tenant.properties"));

        DriverManagerConnectionProviderImpl driver_conn_provider = new
                DriverManagerConnectionProviderImpl();
        driver_conn_provider.configure(PropertiesHelper.map(props));

        this.connectionProvider = driver_conn_provider;
    }

    /** Funciones obligatorias utilizadas por el módulo de multi-tenancy **/

    /*Como sólo hay una conexión posible, tanto getAnyConnectionProvider como selectConnectionProvider devuelven la misma conexion*/
    @Override
    protected ConnectionProvider getAnyConnectionProvider(){
        return connectionProvider;
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(Object o) {
        return null;
    }

    /*Función que lanza el comando de la base datos que permite activar el esquema adecuado*/
    public Connection getConnection(String tenantIdentifier) throws SQLException{
        Connection connection = super.getConnection(tenantIdentifier);
        connection.createStatement().execute(String.format(
                "ALTER USER mespla WITH DEFAULT_SCHEMA = %s;", tenantIdentifier));
        return connection;
    }

    @Override
    public DatabaseConnectionInfo getDatabaseConnectionInfo(Dialect dialect) {
        return super.getDatabaseConnectionInfo(dialect);
    }
}
