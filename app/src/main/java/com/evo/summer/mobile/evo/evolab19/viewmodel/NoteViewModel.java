package com.evo.summer.mobile.evo.evolab19.viewmodel;

import android.app.Application;

import com.evo.summer.mobile.evo.evolab19.database.NoteRepository;
import com.evo.summer.mobile.evo.evolab19.models.Note;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;


public class NoteViewModel extends AndroidViewModel {

    private LiveData<PagedList<Note>> liveResults;
    private NoteRepository repository;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        liveResults = loadNotes();
    }

    private LiveData<PagedList<Note>> loadNotes() {
        return new LivePagedListBuilder<>(
                repository.getNotes(),
                new PagedList.Config.Builder()
                        .setPageSize(20)
                        .setPrefetchDistance(20)
                        .setEnablePlaceholders(false)
                        .build())
                .build();
    }


    public LiveData<PagedList<Note>> getNotes() {
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

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }


    public void replaceSubscription(LifecycleOwner lifecycleOwner) {
        liveResults.removeObservers(lifecycleOwner);
        liveResults = loadNotes();
    }
}
