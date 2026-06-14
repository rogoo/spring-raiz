package br.rosa;

import static io.gatling.javaapi.core.CoreDsl.constantConcurrentUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class MainSimulation extends Simulation {
  private HttpProtocolBuilder httpProtocol =
      http.baseUrl("http://localhost:8080/api").acceptHeader("application/json");

  private ScenarioBuilder semThreadVirtual =
      scenario("Sem Thread Virtual").exec(http("Sem Thread Virtual").get("/thread-sem/1/oiiiii"));
  private ScenarioBuilder comThreadVirtual =
      scenario("Com Thread Virtual")
          .exec(http("Com Thread Virtual").get("/thread-com/2/fiz-nada-hein"));

  {
    setUp(
            semThreadVirtual.injectClosed(constantConcurrentUsers(100).during(20)),
            comThreadVirtual.injectClosed(constantConcurrentUsers(100).during(20)))
        .protocols(httpProtocol);
  }
}
