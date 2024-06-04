// Copyright (c) Microsoft. All rights reserved.
package com.microsoft.openai.samples.rag.config;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.net.URISyntaxException;

@Configuration
public class AzureAuthenticationConfiguration {

    @Value("${azure.identity.client-id}")
    String clientId;

    @Profile("dev")
    @Bean
    public TokenCredential localTokenCredential() {
        return new IntelliJCredentialBuilder().build();
        // return new AzureCliCredentialBuilder().build();
    }

    @Profile("docker")
    @Bean
    public TokenCredential servicePrincipalTokenCredential() {
        return new EnvironmentCredentialBuilder().build();
    }

    @Bean
    @Profile("default")
    public TokenCredential managedIdentityTokenCredential() {
        if (this.clientId.equals("system-managed-identity"))
            return new ManagedIdentityCredentialBuilder().build();
        else
            return new ManagedIdentityCredentialBuilder().clientId(this.clientId).build();

    }
}
