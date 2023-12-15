package com.magazynplus.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private String Name;
    private String phone;
    private String address;
    private String nip;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;
}
