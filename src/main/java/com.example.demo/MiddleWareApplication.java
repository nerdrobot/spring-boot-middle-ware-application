package com.example.demo;

import com.example.demo.model.ResponseStockSymbolResult;
import com.example.demo.model.StockSymbolResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class MiddleWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiddleWareApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


}


@RestController
@RequestMapping(value = "/hello-world")
class HelloWorldController {

    @GetMapping
    public String getDefaultResponse() {
        return "Hello World!";
    }
}

@RestController
@RequestMapping(value = "/stocks")
@RequiredArgsConstructor
class MiddleWareController {

    private final FinHubService finHubService;


    @GetMapping
    public ResponseStockSymbolResult getResponse() {
        return finHubService.getStockSymbolResult("apple");
    }

}

@Configuration
class StartupConfiguration {
	@Value("${finhub.api.url}")
	private String finhubApiUrl;

    @Value("${finhub.api.key}")
    private String finhubApiKey;

    public String getFinhubApiUrl() {
        return finhubApiUrl;
    }
    public String getFinhubApiKey() {
        return finhubApiKey;
    }
}

@Service
@RequiredArgsConstructor
class FinHubService {

    private final StartupConfiguration startupConfiguration;
    private final RestTemplate restTemplate;

    public ResponseStockSymbolResult getStockSymbolResult(String symbol) {
        String url = startupConfiguration.getFinhubApiUrl() + "/search?q=" + symbol + "&token=" + startupConfiguration.getFinhubApiKey();

        ResponseStockSymbolResult response = restTemplate.getForObject(url, ResponseStockSymbolResult.class);

        return response;
    }

}



