package com.neurotoxin.steamclone.repository.single;

import com.neurotoxin.steamclone.entity.single.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentById(Long commentId);
}
