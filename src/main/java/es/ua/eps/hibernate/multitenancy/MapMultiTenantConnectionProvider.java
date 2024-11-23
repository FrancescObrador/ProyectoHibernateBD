package es.ua.eps.hibernate.multitenancy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.internal.util.PropertiesHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.spi.ServiceRegistryImplementor;

public class MapMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider<String> {

    private Map<String, ConnectionProvider> connectionProviderMap = new HashMap<String, ConnectionProvider>();
    private ServiceRegistry serviceRegistry;

    public MapMultiTenantConnectionProvider() {

        // Create a new ServiceRegistry
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        this.serviceRegistry = registryBuilder.build();

        initConnectionProviderForTenant("P05user1");
        initConnectionProviderForTenant("P05user2");
    }

    private void initConnectionProviderForTenant(String tenantId) {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream(
                    String.format("/multitenancy-%s.properties", tenantId)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();

        connectionProvider.injectServices((ServiceRegistryImplementor) serviceRegistry);
        connectionProvider.configure(PropertiesHelper.map(properties));

        this.connectionProviderMap.put(tenantId, connectionProvider);
    }

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        // Return the first connection provider if no default is set
        return connectionProviderMap.values().iterator().next();
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
        return connectionProviderMap.get(tenantIdentifier);
    }
}