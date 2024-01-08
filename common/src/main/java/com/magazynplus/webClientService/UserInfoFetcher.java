package com.magazynplus.webClientService;

import com.magazynplus.dto.UserResponse;

public interface UserInfoFetcher {
    UserResponse fetchUserInfo(String jwtToken, String username);
}
