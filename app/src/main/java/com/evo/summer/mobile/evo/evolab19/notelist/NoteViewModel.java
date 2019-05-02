package com.evo.summer.mobile.evo.evolab19.notelist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.evo.summer.mobile.evo.evolab19.database.NoteRepository;
import com.evo.summer.mobile.evo.evolab19.pojo.Note;
import com.evo.summer.mobile.evo.evolab19.utils.PreferencesUtil;


public class NoteViewModel extends AndroidViewModel {

    private final Application application;
    private LiveData<PagedList<Note>> liveResults;
    private NoteRepository repository;

    NoteViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository = new NoteRepository(this.application);
        liveResults = loadNotes();
    }

    private LiveData<PagedList<Note>> loadNotes() {
        String builder;
        String sort = PreferencesUtil.getSortQuery(application) ? "ASC" : "DESC";
        if (PreferencesUtil.getStoreQuery(application).isEmpty()) {
            builder = "SELECT * FROM note_table ORDER BY time " + sort;
        } else {
            builder = "SELECT * FROM note_table WHERE instr(LOWER(description),'"
                    + PreferencesUtil.getStoreQuery(application).toLowerCase() + "') ORDER BY time " + sort;
        }

        return new LivePagedListBuilder<>(
                repository.getNotes(builder),
                new PagedList.Config.Builder() //
                        .setPageSize(20) //
                        .setPrefetchDistance(20) //
                        .setEnablePlaceholders(false) //
                        .build())
                .build();
    }


    LiveData<PagedList<Note>> getNotes() {
        return liveResults;
    }


    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    void deleteAllNotes() {
        repository.deleteAllNotes();
    }


    void replaceSubscription(LifecycleOwner lifecycleOwner) {
        liveResults.removeObservers(lifecycleOwner);
        liveResults = loadNotes();
    }
}
