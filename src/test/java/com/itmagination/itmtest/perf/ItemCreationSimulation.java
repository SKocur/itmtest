package com.itmagination.itmtest.perf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itmagination.itmtest.controller.requests.CreateItemRequest;
import com.itmagination.itmtest.item.ItemType;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.UUID;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class ItemCreationSimulation extends Simulation {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    private ScenarioBuilder scn = scenario("Test creation of an item")
            .exec(
                    http("POST /api/v1/items")
                            .post("/api/v1/items")
                            .header("Authorization", "Bearer AUTH_KEY_admin")
                            .body(
                                    StringBody(
                                            objectMapper.writeValueAsString(
                                                    new CreateItemRequest(
                                                            UUID.randomUUID().toString(),
                                                            UUID.randomUUID().toString(),
                                                            ItemType.BOOK
                                                    )
                                            )
                                    )
                            ).check(HttpDsl.status().is(201))
            );

    public ItemCreationSimulation() throws JsonProcessingException {
        setUp(
                scn.injectOpen(rampUsers(2000).during(60))
        ).protocols(httpProtocol);
    }
}
