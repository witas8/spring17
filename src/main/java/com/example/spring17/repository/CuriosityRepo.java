package com.example.spring17.repository;

import com.example.spring17.model.curiosity.dto.CuriosityDTO;
import com.example.spring17.model.curiosity.entity.Curiosity;
import com.example.spring17.model.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CuriosityRepo extends JpaRepository<Curiosity, Long> {

    @Query("SELECT c FROM Curiosity c WHERE c.accepted = true")
    List<Curiosity> findAccepted();

    @Query("SELECT c FROM Curiosity c WHERE c.accepted != true")
    List<Curiosity> findNotAccepted();

    @Query("SELECT c FROM Curiosity c ORDER BY Likes DESC") //:param ORDER BY Likes DESC
    List<Curiosity> filerByLikes(String param);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Curiosity c WHERE c.question = ?1")
    Boolean checkQuestionUniqueness(String question);


    //JOIN to get another property from external relational entity then only ID
    @Query("SELECT c FROM Curiosity c JOIN c.user WHERE c.question like %:partOfQuestion%")
    List<Curiosity> findByQuestionContaining(String partOfQuestion); // limit Pageable page

    Optional<Curiosity> findByQuestion(String question);

    Optional<Curiosity> findByAnswer(String answer);



}
