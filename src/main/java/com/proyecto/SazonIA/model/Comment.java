package com.proyecto.SazonIA.model;

import org.springframework.data.mongodb.core.mapping.MongoId;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "Comment")
public class Comment {
    @MongoId
    @Field(targetType = FieldType.OBJECT_ID)
    @NotBlank(message = "Id is mandatory")
    @JsonProperty("id")
    private String id;
    @NotBlank(message = "Comment Id is mandatory")
    @Field(name = "commentId")
    @JsonProperty("commentId")
    private int commentId;
    @NotBlank(message = "Content is mandatory")
    @Field(name = "content")
    @JsonProperty("content")
    private String content;
    @NotBlank(message = "Author is mandatory")
    @Field(name = "author")
    @JsonProperty("author")
    private String author;
    @NotBlank(message = "Timestamp is mandatory")
    @Field(name = "timestamp")
    @JsonProperty("timestamp")
    private String timestamp;
    @Field(name = "replies")
    @JsonProperty("replies")
    private List<Comment> replies;

    public Comment() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", commentId=" + commentId +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", replies=" + replies +
                '}';
    }

}