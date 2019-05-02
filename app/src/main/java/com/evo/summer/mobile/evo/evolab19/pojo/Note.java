package com.evo.summer.mobile.evo.evolab19.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;


@Entity(tableName = "note_table")
public class Note implements Parcelable {


    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private Long time;

    public Note() {
    }

    public Note(String description, Long time) {
        this.description = description;
        this.time = time;
    }

    public String getTimeByFormat(String format, Long time) {
        return (String) DateFormat.format(format, time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.description);
        dest.writeValue(this.time);
    }

    protected Note(Parcel in) {
        this.id = in.readInt();
        this.description = in.readString();
        this.time = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return getId() == note.getId() &&
                Objects.equals(getDescription(), note.getDescription()) &&
                Objects.equals(getTime(), note.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getTime());
    }
}