package pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "admin-provider", port = "1234")
public class PactDbConsumerTest {

    @Pact(consumer = "db-consumer")
    public RequestResponsePact validateUserPact(PactDslWithProvider builder) {
        System.setProperty("pact.rootDir", "./pacts");
        DslPart bodyResponse = new PactDslJsonBody()
                .integerType("id", 1)
                .stringType("name", "admin")
                .stringType("email", "admin@ee.ee")
                .booleanType("validated", true);


        return builder
                .given("get user")
                .uponReceiving("user blabla")
                .method(RequestMethod.GET.name())
                .path("/api/user/1")
                .willRespondWith()
                .status(200)
                .body(bodyResponse).toPact();
    }

    @Test
    @PactTestFor(pactMethod = "validateUserPact")
    public void testPact(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/api/user/1")
                .execute().returnResponse();

        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
        assertThat(JsonPath.read(httpResponse.getEntity().getContent(), "$.id").toString()).isEqualTo("1");
        assertThat(JsonPath.read(httpResponse.getEntity().getContent(), "$.name").toString()).isEqualTo("admin");
        assertThat(JsonPath.read(httpResponse.getEntity().getContent(), "$.email").toString()).isEqualTo("admin@ee.ee");
        assertThat(JsonPath.read(httpResponse.getEntity().getContent(), "$.validated").toString()).isEqualTo("true");
    }

}
