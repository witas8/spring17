package com.example.spring17.model.curiosity.entity;

import com.example.spring17.model.user.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "curiosity")
public class Curiosity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)//cascade = CascadeType.MERGE) //All
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Categories category;
    private String question;
    private String answer;
    private boolean accepted;
    private int likes;

}
