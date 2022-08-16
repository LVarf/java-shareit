package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@EnableJpaRepositories
public interface ItemRepository extends JpaRepository<Item, Long>{

    Optional<List<Item>> findByOwner(Long userId);
}
