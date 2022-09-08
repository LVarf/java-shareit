package ru.practicum.shareit.comment.dto;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;

@Entity
@Data
@Table(name = "comments")
public class CommentDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "text", nullable = false)
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    private Item itemId;
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
}
