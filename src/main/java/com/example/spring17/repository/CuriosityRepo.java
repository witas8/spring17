package com.example.spring17.repository;

import com.example.spring17.model.curiosity.entity.Curiosity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CuriosityRepo extends JpaRepository<Curiosity, Long> {

    @Query("SELECT c FROM Curiosity c WHERE c.accepted = true")
    List<Curiosity> findAccepted();

    @Query("SELECT c FROM Curiosity c WHERE c.accepted != true")
    List<Curiosity> findNotAccepted();

    @Query("SELECT c FROM Curiosity c ORDER BY Likes DESC")
    List<Curiosity> filerByLikes();

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Curiosity c WHERE c.question = ?1")
    Boolean checkQuestionUniqueness(String question);


    //JOIN to get another property from external relational entity then only ID
    @Query("SELECT c FROM Curiosity c JOIN c.user WHERE c.question like %:partOfQuestion%")
    Optional<List<Curiosity>> findByQuestionContaining(String partOfQuestion); // limit Pageable page

}