package database;

import java.time.LocalDate;
import java.util.ArrayList;

public class Task {
    private int id;
    private String name;
    private String description;
    private String inputDate;
    LocalDate date = LocalDate.parse(inputDate);
    private enum Status {
        ToDo,
        InProgress,
        Complate
    }
    ArrayList<String> comment = new ArrayList<String>();
    Task subTask = new Task();
    //The last one is field for attached filed, but I don't know which type must be this field

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<String> getComment() {
        return comment;
    }

    public void setComment(ArrayList<String> comment) {
        this.comment = comment;
    }

    public Task getSubTask() {
        return subTask;
    }

    public void setSubTask(Task subTask) {
        this.subTask = subTask;
    }
}
