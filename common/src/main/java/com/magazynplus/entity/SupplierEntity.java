package com.magazynplus.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "t_supplier")
@ToString
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phoneNumber;
    private String email;
    @JsonProperty("address")
    private String address;
    private String postalCode;

    @JsonProperty("nip")
    private String nip;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
