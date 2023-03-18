package com.abhibarkade.todo.roomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.abhibarkade.todo.pojo.POJO_Todo;

@Entity
public class EntityTask {

    @PrimaryKey(autoGenerate = true)
    private long local_id;

    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "userId")
    private String userId;

    @ColumnInfo(name = "creationDate")
    private String creationDate;

    @ColumnInfo(name = "startTime")
    private String startTime;

    @ColumnInfo(name = "endTime")
    private String endTime;

    @ColumnInfo(name = "priority")
    private String priority;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "category")
    private String category;

    public EntityTask() {
    }

    public EntityTask(String id, String title, String description, String userId, String creationDate, String startTime, String endTime, String priority, String status, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.creationDate = creationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.status = status;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getLocal_id() {
        return local_id;
    }

    public void setLocal_id(long local_id) {
        this.local_id = local_id;
    }

    public static EntityTask toEntityTask(POJO_Todo todo) {
        return new EntityTask(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getUserId(),
                todo.getcreationDate(),
                todo.getStartTime(),
                todo.getEndTime(),
                "" + todo.getPriority(),
                "" + todo.getStatus(),
                "" + todo.getCategory()
        );
    }
}
