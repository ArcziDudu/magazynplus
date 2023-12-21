package com.magazynplus.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

import javax.swing.plaf.ListUI;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USER_")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String email;

    @Column
    private String firstname;

    @Column
    private String lastname;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @JsonManagedReference
    private List<ProductEntity> products;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<SupplierEntity> suppliers;
}
