package com.evo.summer.mobile.evo.evolab19.database;

import com.evo.summer.mobile.evo.evolab19.models.NoteDTO;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;



@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteDTO note);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(NoteDTO note);

    @Delete
    void delete(NoteDTO note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @RawQuery(observedEntities = NoteDTO.class)
    DataSource.Factory<Integer, NoteDTO> getAllNotes(SupportSQLiteQuery query);

}
