package com.evo.summer.mobile.evo.evolab19.database;

import android.content.Context;
import android.os.AsyncTask;

import com.evo.summer.mobile.evo.evolab19.models.Note;
import com.evo.summer.mobile.evo.evolab19.models.NoteDTO;
import com.evo.summer.mobile.evo.evolab19.utils.PreferencesUtil;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {NoteDTO.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    static synchronized NoteDatabase getGetInstance(Context context) {
        if (instance == null) {

            if (PreferencesUtil.getGenerateData(context)) {
                instance = Room.databaseBuilder(context, NoteDatabase.class, "note_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build();
            } else {
                instance = Room.databaseBuilder(context, NoteDatabase.class, "note_database")
                        .fallbackToDestructiveMigration()
                        .build();
            }

        }
        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        PopulateDbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 500; i++) {
                noteDao.insert(new Note("description " + i, System.currentTimeMillis()).convert());
            }
            return null;
        }
    }

}
