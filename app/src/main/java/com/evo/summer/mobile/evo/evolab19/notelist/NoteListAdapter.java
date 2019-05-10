package com.evo.summer.mobile.evo.evolab19.notelist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.evo.summer.mobile.evo.evolab19.R;
import com.evo.summer.mobile.evo.evolab19.databinding.ItemNoteBinding;
import com.evo.summer.mobile.evo.evolab19.models.Note;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListAdapter extends PagedListAdapter<Note, RecyclerView.ViewHolder> {

    NoteListAdapter() {
        super(DIFF_CALLBACK);
    }


    private NoteViewHolder.OnClickListener onClickListener;


    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Note>() {
                @Override
                public boolean areItemsTheSame(@NonNull Note oldNote, @NonNull Note newNote) {
                    return oldNote.getId() == newNote.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Note oldNote, @NonNull Note newNote) {
                    return oldNote.equals(newNote);
                }
            };


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemNoteBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_note, parent, false);
        return new NoteViewHolder(binding, onClickListener);
    }

    Note getNoteAt(int position) {
        return getItem(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Note item = getItem(position);
        NoteViewHolder noteViewHolder = (NoteViewHolder) holder;
        if (item != null) {
            noteViewHolder.bind(item);
        }
    }

    void setOnClickListener(NoteViewHolder.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;

    }

}