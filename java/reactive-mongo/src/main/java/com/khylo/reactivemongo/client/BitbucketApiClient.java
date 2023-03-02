package com.khylo.reactivemongo.client;


import com.khylo.reactivemongo.client.dto.Project;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

public class BitbucketApiClient {

    private final WebClient webClient;

    public BitbucketApiClient() {
        this.webClient = WebClient.builder()
            .baseUrl("https://api.bitbucket.org/2.0/")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public Flux<Project> getPublicProjects() {
        return webClient.get()
            .uri("/repositories")
            .retrieve()
            .bodyToFlux(Project.class);
            //.filter(project -> project.getIs_private() == false);
    }

    public Mono<Void> enableAnonymousAccessForProject(String projectKey) {
        return webClient.put()
            .uri("/repositories/{projectKey}/access", projectKey)
            .bodyValue(Collections.singletonMap("is_private", false))
            .retrieve()
            .bodyToMono(Void.class);
    }
}
