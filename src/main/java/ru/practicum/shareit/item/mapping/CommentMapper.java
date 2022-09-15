package ru.practicum.shareit.item.mapping;

import ru.practicum.shareit.item.dto.CommentDTO;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.user.UserMapper;

public class CommentMapper {
    public static final Comment mapperToComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        return comment;
    }

    public static final CommentDTO mapperToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setText(comment.getText());
        commentDTO.setId(comment.getId());
        commentDTO.setAuthor(UserMapper.mapperToUserDTO(comment.getAuthor()));
        commentDTO.setItem(ItemMapper.mapperToItemDTOForGetByItemId(comment.getItem()));
        commentDTO.setCreated(comment.getCreated());
        commentDTO.setAuthorName(comment.getAuthor().getName());
        return commentDTO;
    }
}
