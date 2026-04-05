package clients;

import core.RequestSpecFactory;
import io.restassured.specification.RequestSpecification;

public class BaseClient {

    protected final RequestSpecification spec;

    public BaseClient() {
        this.spec = RequestSpecFactory.baseSpec();
    }

    protected RequestSpecification withAuth(String token) {
        if (token == null || token.isEmpty()) {
            return spec;
        }
        return spec.header("Authorization", "Bearer " + token);
    }
}
