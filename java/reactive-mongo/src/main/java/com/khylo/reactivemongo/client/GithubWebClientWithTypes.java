package com.khylo.reactivemongo.client;

import com.khylo.reactivemongo.client.dto.github.Repo;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * See examples in code
 * curl -H "Accept: application/vnd.github+json" -H "Authorization: Bearer <YOUR-TOKEN>"  -H "X-GitHub-Api-Version: 2022-11-28"    https://api.github.com/users/USERNAME/repos
 */
@Log4j2
public class GithubWebClientWithTypes extends GithubBaseClient {

    public static void main(String[] args) {
        Properties prop = new Properties();
        try {
            prop.load(GithubWebClientWithTypes.class.getClassLoader().getResourceAsStream(PROP_FILE));
        } catch (IOException e) {
            log.error("Failed to load properties file "+PROP_FILE);
            throw new RuntimeException(e);
        }
        String bearerToken="Bearer "+prop.getProperty(GITHUB_TOKEN);
        WebClient wc= WebClient.builder()
            .baseUrl(BASE_URL)
            .defaultHeader(API_VERSION_HDR,API_VERSION_VAL)
            .defaultHeader(ACCEPT_HEADER, GITHUB_JSON)
            .defaultHeader(AUTH_BEARER, bearerToken)
            .build();
        new GithubWebClientWithTypes(wc).run();
        log.info("Press return to exit");
        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final WebClient wc;

    public GithubWebClientWithTypes(WebClient wc) {
        super();
        this.wc=wc;
    }

    public void run(){
        // Get Repos
        ParameterizedTypeReference<List<Repo>> repo = new ParameterizedTypeReference<>(){};
        Mono<List<Repo>> resp = wc.get().uri(GET_REPOS,"khylo").retrieve().bodyToMono(repo);
        resp.subscribe(l->printRepoSummary(l));
    }

    private void printRepoSummary(List<Repo> repos){
        System.out.println(repos.size()+" repos");
        repos.stream().forEach(m -> {
            String key = m.name();
            String user = m.owner().login();
            String commits = getCommits(user, key);
            System.out.println(String.format("  %s : %s (%d) *(%d) %s", key, m.html_url(), m.size(), m.stargazers_count(),commits));
        });
    }
    private String getCommits(String user, String repo){
        Flux<List> resp = wc.get().uri(GET_COMMITS,user, repo).retrieve().bodyToFlux(List.class);
        List l = resp.toStream().toList(); // Note this is blocking
        StringBuilder ret = new StringBuilder(String.format("[%d commits], last on ", l.size()));
        String date = ((Map)((Map)((Map)l.get(0)).get("commit")).get("committer")).get("date").toString();
        return ret.append(date).toString();
    }
}
