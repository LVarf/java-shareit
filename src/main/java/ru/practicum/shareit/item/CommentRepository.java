package ru.practicum.shareit.item;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select * " +
            "from comments c " +
            "where c.item_id = ?1 " +
            "order by created desc", nativeQuery = true)
    Optional<List<Comment>> findAllByItemId(Long itemId);
}
