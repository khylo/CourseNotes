package com.khylo.reactivemongo.client;

public class GithubBaseClient {
    public static final String BASE_URL = "https://api.github.com";
    public static final String GET_REPOS = "/users/{user}/repos"; // See https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28#list-repositories-for-a-user
    public static final String GET_COMMITS = "/repos/{user}/{repo}/commits"; // >curl -H "Accept: application/vnd.github+json" -H "Authorization: Bearer <token>>"  -H "X-GitHub-Api-Version: 2022-11-28"    https://api.github.com/repos/<user>/<repo>/commits
    public static final String API_VERSION_HDR = "X-GitHub-Api-Version";
    public static final String API_VERSION_VAL = "2022-11-28";
    public static final String ACCEPT_HEADER = "Accept";
    public static final String GITHUB_JSON = "application/vnd.github+json";
    public static final String GITHUB_TOKEN = "GITHUB_TOKEN";
    public static final String PROP_FILE = "application-local.properties";
    public static String AUTH_BEARER = "Authorization";
    public final String BEARER_TOKEN;

    public GithubBaseClient() {
        BEARER_TOKEN = System.getProperty("GITHUB_TOKEN");
    }
}
