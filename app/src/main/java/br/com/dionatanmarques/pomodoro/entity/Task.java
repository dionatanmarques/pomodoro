package br.com.dionatanmarques.pomodoro.entity;

public class Task {
    private Integer id;
    private String title;
    private String description;
    private Integer pomodoro;
    private Integer status;

    public Task() {
    }

    public Task(Integer id, String title, String description, Integer pomodoro, Integer status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pomodoro = pomodoro;
        this.status = status;
    }

    public Task(String title, String description, Integer pomodoro) {
        this.title = title;
        this.description = description;
        this.pomodoro = pomodoro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getPomodoro() {
        return pomodoro;
    }

    public void setPomodoro(Integer pomodoro) {
        this.pomodoro = pomodoro;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}