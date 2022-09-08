package ru.practicum.shareit.item;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface ItemRepository extends JpaRepository<Item, Long>{

    @Query(value = "select * from items i " +
            "where (lower(i.description) like concat('%', ?1, '%') " +
            "or lower(i.name) like concat('%', ?1, '%')) " +
            "and i.is_available = true", nativeQuery = true)
    List<Item> findByContainsText(String text);

    @Query(value = "select * from items i " +
            "where owner_id  = ?1", nativeQuery = true)
    List<Item> findByOwner(Long userId);

}
