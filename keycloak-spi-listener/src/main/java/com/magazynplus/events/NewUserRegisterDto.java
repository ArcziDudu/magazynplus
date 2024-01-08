package com.magazynplus.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUserRegisterDto {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
}
