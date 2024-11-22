package es.ua.eps.hibernate.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    static String tenantid="default";

    static public void setTenantID(String id) {
        tenantid = id;
    }

    public String resolveCurrentTenantIdentifier() {
        return tenantid;
    }

    public boolean validateExistingCurrentSessions() {
        return true;
    }

}