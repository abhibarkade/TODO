package com.abhibarkade.todo.pojo;

import com.abhibarkade.todo.utils.enums.Category;
import com.abhibarkade.todo.utils.enums.Priority;
import com.abhibarkade.todo.utils.enums.Status;

public class POJO_Todo {
    String id, title, description, userId, dueDate;
    Priority priority;
    Status status;
    Category category;

    POJO_Todo(){
        priority = Priority.Undefined;
        status = Status.Undefined;
        category = Category.Undefined;
    }

    public POJO_Todo(String id, String title, String description, String userId, String dueDate, Priority priority, Status status, Category category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.dueDate = dueDate;
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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
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
}
