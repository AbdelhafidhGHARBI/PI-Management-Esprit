package tn.esprit.api_gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {
    @GetMapping("/test")
    public Mono<String> home() {
        return Mono.just("API Gateway is running!");
    }
}
