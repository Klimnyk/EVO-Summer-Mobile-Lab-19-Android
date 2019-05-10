package com.evo.summer.mobile.evo.evolab19.database;

import android.app.Application;
import android.os.AsyncTask;

import com.evo.summer.mobile.evo.evolab19.models.Note;
import com.evo.summer.mobile.evo.evolab19.models.NoteDTO;
import com.evo.summer.mobile.evo.evolab19.utils.PreferencesUtil;

import androidx.paging.DataSource;
import androidx.sqlite.db.SimpleSQLiteQuery;


public class NoteRepository {
    private final Application application;
    private NoteDao noteDao;

    public NoteRepository(Application application) {
        this.application = application;
        NoteDatabase database = NoteDatabase.getGetInstance(this.application);
        noteDao = database.noteDao();

    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note.convert());
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note.convert());

    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note.convert());

    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }


    public DataSource.Factory<Integer, Note> getNotes() {
        String builder;
        String sort = PreferencesUtil.getSortQuery(application) ? "ASC" : "DESC";
        if (PreferencesUtil.getStoreQuery(application).isEmpty()) {
            builder = "SELECT * FROM note_table ORDER BY time " + sort;
        } else {
            builder = "SELECT * FROM note_table WHERE LOWER(description) LIKE '%"
                    + PreferencesUtil.getStoreQuery(application).toLowerCase() + "%' ORDER BY time " + sort;
        }
        return noteDao.getAllNotes(new SimpleSQLiteQuery(builder)).map(NoteDTO::convert);
    }

    private static class InsertNoteAsyncTask extends AsyncTask<NoteDTO, Void, Void> {

        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteDTO... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }


    private static class UpdateNoteAsyncTask extends AsyncTask<NoteDTO, Void, Void> {

        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteDTO... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<NoteDTO, Void, Void> {

        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteDTO... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }


}
