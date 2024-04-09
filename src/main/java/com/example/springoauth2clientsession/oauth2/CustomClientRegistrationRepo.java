package com.example.springoauth2clientsession.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
@RequiredArgsConstructor
public class CustomClientRegistrationRepo {
    private final SocialClientRegistration socialClientRegistration;

    public InMemoryClientRegistrationRepository clientRegistrationRepository() {

        return new InMemoryClientRegistrationRepository(
                socialClientRegistration.naverClientRegistration(),
                socialClientRegistration.googleClientRegistration());
    }
}
