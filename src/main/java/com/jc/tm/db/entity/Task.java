package com.jc.tm.db.entity;

import com.jc.tm.db.Status;
import com.jc.tm.service.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "task")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime created;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    //default status for any task is to do
    private Status status = Status.TODO;
    @Column(columnDefinition = "TIMESTAMP", name = "due_date")
    private LocalDateTime dueDate;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    //default priority for any task is normal
    private Priority priority = Priority.NORMAL;

}
