package com.tsb.skribbl.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "rounds")
public class RoundEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "word", nullable = false)
    private String word;

    @Column(name = "drawing_user_name", nullable = false)
    private String drawingUserName;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDrawingUserName() {
        return drawingUserName;
    }

    public void setDrawingUserName(String drawingUserName) {
        this.drawingUserName = drawingUserName;
    }
}
