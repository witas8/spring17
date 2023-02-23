package com.example.spring17.model.curiosity.entity;

import com.example.spring17.model.user.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
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
    @Column(unique=true)
    private String question;
    private String answer;
    private Boolean accepted;
    private Integer likes;
    private OffsetDateTime date; //createDate

    @PrePersist
    void prePersist() {
        this.date = OffsetDateTime.now();
    }
}

