package com.magazynplus.webClientService;

import com.magazynplus.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Component
public class UserInfoFetcherImpl implements UserInfoFetcher {
    private final WebClient.Builder webClientBuilder;

    @Override
    public UserResponse fetchUserInfo(String jwtToken, String username) {
        return webClientBuilder.build().get()
                .uri("http://api-gateway/api/user/info/" + username)
                .header("Authorization", "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();
    }
}
