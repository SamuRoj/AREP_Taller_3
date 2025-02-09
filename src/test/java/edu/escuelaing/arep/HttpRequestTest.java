package edu.escuelaing.arep;

import edu.escuelaing.arep.http.HttpRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {
    private static HttpRequest httpRequest;

    @BeforeAll
    public static void setup() {
        httpRequest = new HttpRequest("/app/greeting", "name=Samuel&lastName=Rojas");
    }

    @Test
    public void shouldRetrieveParams() {
        assertEquals("Rojas", httpRequest.getValues("lastName"));
    }

    @Test
    public void shouldNotRetrieveParams() {
        assertEquals("Not Found.", httpRequest.getValues("age"));
    }
}
