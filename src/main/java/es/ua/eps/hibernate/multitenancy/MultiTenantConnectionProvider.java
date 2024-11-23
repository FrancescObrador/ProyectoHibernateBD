package es.ua.eps.hibernate.multitenancy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.internal.util.PropertiesHelper;

public class MultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider<String> {

    private Map<String, ConnectionProvider> connectionProviderMap = new HashMap<String, ConnectionProvider>();

    public MultiTenantConnectionProvider() throws IOException {
        //Databases added (they could be read from a configuration file)
        //initConnectionProviderForTenant("default");

        initConnectionProviderForTenant("P05user1");
        initConnectionProviderForTenant("P05user2");

    }

    private void initConnectionProviderForTenant(String tenantId) throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream(
                String.format("/multitenancy-%s.properties", tenantId)));

        DriverManagerConnectionProviderImpl connectionProvider =
                new DriverManagerConnectionProviderImpl();
        connectionProvider.configure( PropertiesHelper.map(properties) );


        this.connectionProviderMap.put(tenantId, connectionProvider);
    }

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return connectionProviderMap.get("default");
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(
            String tenantIdentifier) {
        return connectionProviderMap.get(tenantIdentifier);
    }
}
