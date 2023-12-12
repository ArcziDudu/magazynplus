package com.magazynplus.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "USER_")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ProductEntity> products;
}
