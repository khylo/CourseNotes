package com.khylo;

import com.azure.identity.AuthorizationCodeCredential;
import com.azure.identity.AuthorizationCodeCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;

public class AzureAuthorizationCodeProvider {

    @Value("app.client.id")
    final String clientId = "YOUR_CLIENT_ID";
    @Value("app.tenant.id")
    final String tenantId = "YOUR_TENANT_ID"; // or "common" for multi-tenant apps
    @Value("app.client.secret")
    final String clientSecret = "YOUR_CLIENT_SECRET";
    final String authorizationCode = "AUTH_CODE_FROM_REDIRECT";
    final String redirectUrl = "YOUR_REDIRECT_URI";
    final List<String> scopes = Arrays.asList("User.Read");

    final AuthorizationCodeCredential credential = new AuthorizationCodeCredentialBuilder()
            .clientId(clientId).tenantId(tenantId).clientSecret(clientSecret)
            .authorizationCode(authorizationCode).redirectUrl(redirectUrl).build();

    public void auth() {

        if (null == scopes || null == credential) {
            throw new RuntimeException("Unexpected error");
        }
        final TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(
                scopes, credential);

        final GraphServiceClient<Request> graphClient = GraphServiceClient.builder()
                .authenticationProvider(authProvider).buildClient();
    }
}
