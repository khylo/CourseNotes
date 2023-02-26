package com.khylo.reactivemongo.client;

import lombok.extern.log4j.Log4j2;
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
public class GithubClient {

    public static final String BASE_URL="https://api.github.com";
    public static final String GET_REPOS="/users/{user}/repos"; // See https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28#list-repositories-for-a-user
    public static final String GET_COMMITS="/repos/{user}/{repo}/commits"; // >curl -H "Accept: application/vnd.github+json" -H "Authorization: Bearer <token>>"  -H "X-GitHub-Api-Version: 2022-11-28"    https://api.github.com/repos/<user>/<repo>/commits
    public static final String API_VERSION_HDR="X-GitHub-Api-Version";
    public static final String API_VERSION_VAL="2022-11-28";
    public static final String ACCEPT_HEADER="Accept";
    public static final String GITHUB_JSON= "application/vnd.github+json";
    public static String AUTH_BEARER="Authorization";

    public static final String GITHUB_TOKEN = "GITHUB_TOKEN";
    public static final String PROP_FILE = "application-local.properties";

    public static void main(String[] args) {
        Properties prop = new Properties();
        try {
            prop.load(GithubClient.class.getClassLoader().getResourceAsStream(PROP_FILE));
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
        new GithubClient(wc).run();
        log.info("Press return to exit");
        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final WebClient wc;
    public final String BEARER_TOKEN ;

    public GithubClient(WebClient wc) {
        this.wc=wc;
        BEARER_TOKEN = System.getProperty("GITHUB_TOKEN");
    }

    public void run(){
        // Get Repos
        Mono<List> resp = wc.get().uri(GET_REPOS,"khylo").retrieve().bodyToMono(List.class);
        resp.subscribe(l->printRepoSummary(l));
    }

    private void printRepoSummary(List<Map<String, Object>> repos){
        System.out.println(repos.size()+" repos");
        repos.stream().forEach(m -> {
            String key = m.get("name").toString();
            String user = ((Map)m.get("owner")).get("login").toString();
            String commits = getCommits(user, key);
            System.out.println(String.format("  %s : %s (%d) *(%d) %s", key, m.get("html_url"), m.get("size"), m.get("stargazers_count"),commits));
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
