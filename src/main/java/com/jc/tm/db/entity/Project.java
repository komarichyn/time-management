package com.jc.tm.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "project")
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @OneToMany(mappedBy = "projects")
//    private List<Task> tasks;
    private String name;

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
//                ", tasks=" + tasks + TODO fix
                ", name='" + name + '\'' +
                '}';
    }
}
