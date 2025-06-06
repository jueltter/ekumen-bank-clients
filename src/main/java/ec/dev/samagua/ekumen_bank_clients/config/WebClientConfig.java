package ec.dev.samagua.ekumen_bank_clients.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class WebClientConfig {
    @Value("${ekumen-bank-accounts.url}")
    private String baseUrl;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        log.debug("WebClientConfig: baseUrl = {}", baseUrl);
        return builder.baseUrl(baseUrl).build();
    }
}
