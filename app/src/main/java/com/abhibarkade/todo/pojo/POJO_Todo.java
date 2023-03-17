package com.abhibarkade.todo.pojo;

import com.abhibarkade.todo.utils.enums.Category;
import com.abhibarkade.todo.utils.enums.Priority;
import com.abhibarkade.todo.utils.enums.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class POJO_Todo {
    String id, title, description, userId, creationDate, startTime, endTime;
    Priority priority;
    Status status;
    Category category;

    POJO_Todo() {
        id = "TODO:" + UUID.randomUUID();
        priority = Priority.Undefined;
        status = Status.Incomplete;
        category = Category.Undefined;
    }

    public POJO_Todo(String title, String description, String userId, String creationDate, String startTime, String endTime, Priority priority, Status status, Category category) {
        id = "TODO:" + UUID.randomUUID();
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

    public String getcreationDate() {
        return creationDate;
    }

    public void setcreationDate(String creationDate) {
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    //    String title, String description, String userId, String creationDate,
//    String startTime, String endTime, Priority priority, Status status, Category category
//
    public static Map<String, String> toMap(POJO_Todo todo) {
        Map<String, String> map = new HashMap<>();
        map.put("id", todo.getId());
        map.put("title", todo.getTitle());
        map.put("description", todo.getDescription());
        map.put("userId", todo.getUserId());
        map.put("creationDate", todo.getcreationDate());
        map.put("startTime", todo.getStartTime());
        map.put("endTime", todo.getEndTime());
        map.put("priority", "" + todo.getPriority());
        map.put("status", "" + todo.getStatus());
        map.put("category", "" + todo.getCategory());
        return map;
    }
}
