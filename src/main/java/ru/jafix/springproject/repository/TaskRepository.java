package ru.jafix.springproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.jafix.springproject.model.Task;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByOwnerIdOrderByName(UUID ownerId);

    @Query("select t from Task t where t.score >= :score")
    List<Task> findByScoreMoreThan(@Param("score") Integer score);

    @Query(value = "select * from tasks where score >= :score", nativeQuery = true)
    List<Task> findByScoreMoreThanNative(@Param("score") Integer score);

    @Modifying
    @Query("UPDATE Task t SET t.completed = true where t.score < 10")
    int completeLowScoreTasks();

    @Modifying
    @Query("UPDATE Task t SET t.score = t.score + :score")
    int increaseScore(@Param("score") int score);
}
