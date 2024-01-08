package com.magazynplus.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
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
    @CsvBindByName(column = "name")
    private String name;

    @JsonProperty("phone")
    @CsvBindByName(column = "phoneNumber")
    private String phoneNumber;

    @CsvBindByName(column = "email")
    private String email;

    @JsonProperty("address")
    @CsvBindByName(column = "address")
    private String address;

    @CsvBindByName(column = "postalCode")
    private String postalCode;

    @CsvBindByName(column = "nip")
    @JsonProperty("nip")
    private String nip;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
