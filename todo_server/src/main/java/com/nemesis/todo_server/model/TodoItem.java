package com.nemesis.todo_server.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class TodoItem implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Lob
    private String text;

    private boolean checked;

}
