package com.se.scrumflow.entity;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "backlogs")
@Data
public class Backlog {
    @Id
    private ObjectId id;
    private String title;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    // 关联的用户故事列表
    private List<UserStory> userStories = new ArrayList<>();
}
