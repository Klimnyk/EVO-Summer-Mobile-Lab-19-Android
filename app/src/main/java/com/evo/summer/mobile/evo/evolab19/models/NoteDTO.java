package com.evo.summer.mobile.evo.evolab19.models;

import org.modelmapper.ModelMapper;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class NoteDTO {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private Long time;

    public NoteDTO() {
    }

//    public NoteDTO(String description, Long time) {
//        this.description = description;
//        this.time = time;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Note convert() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Note.class);
    }
}
