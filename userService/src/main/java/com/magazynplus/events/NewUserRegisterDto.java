package com.magazynplus.events;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NewUserRegisterDto {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
}
