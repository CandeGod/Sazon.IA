package com.proyecto.SazonIA.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "replycommentrecipe")
public class ReplyCommentRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    @JsonProperty("reply_id")
    private Integer reply_id;

    @NotBlank(message = "Content is mandatory or at least must contain one character")
    @Size(min = 1, max = 300, message = "The content must be between 1 and 300 characters")
    @Column(name = "content", nullable = false, length = 300)
    @JsonProperty("content")
    private String content;

    @Column(name = "reply_time_stamp", nullable = false)
    @JsonProperty("reply_time_stamp")
    private java.sql.Timestamp reply_time_stamp;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private CommentRecipe comment;

    public ReplyCommentRecipe() {
    }

    public Integer getReply_id() {
        return reply_id;
    }

    public void setReply_id(Integer reply_id) {
        this.reply_id = reply_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public java.sql.Timestamp getReply_time_stamp() {
        return reply_time_stamp;
    }

    public void setReply_time_stamp(java.sql.Timestamp reply_time_stamp) {
        this.reply_time_stamp = reply_time_stamp;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public CommentRecipe getComment() {
        return comment;
    }

    public void setComment(CommentRecipe comment) {
        this.comment = comment;
    }
    

    @Override
    public String toString() {
        return "ReplyCommentRecipe{" +
                "reply_id=" + reply_id +
                ", content='" + content + '\'' +
                ", time_stamp=" + reply_time_stamp +
                ", author=" + author +
                ", comment=" + comment +
                '}';
    }

}
