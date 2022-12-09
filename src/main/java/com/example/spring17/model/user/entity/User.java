package com.example.spring17.model.user.entity;

import lombok.*;
import org.springframework.lang.Nullable;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Nullable
    private Long id;

    private String firstName;
    private String lastName;
    @Column(unique=true)
    private String username;

    //@Nullable
    //@JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;
    @Column(unique=true)
    private String email;
    private String phone;

}
