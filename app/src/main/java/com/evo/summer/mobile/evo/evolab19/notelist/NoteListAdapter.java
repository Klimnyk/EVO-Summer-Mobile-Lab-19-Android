package com.evo.summer.mobile.evo.evolab19.notelist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.evo.summer.mobile.evo.evolab19.R;
import com.evo.summer.mobile.evo.evolab19.databinding.ItemNoteBinding;
import com.evo.summer.mobile.evo.evolab19.pojo.Note;

public class NoteListAdapter extends PagedListAdapter<Note, RecyclerView.ViewHolder> {

    NoteListAdapter() {
        super(DIFF_CALLBACK);
    }

    private final String TAG = "NoteListAdapter";

    private NoteViewHolder.OnClickListener onClickListener;


    @Override
    public void submitList(@Nullable PagedList<Note> pagedList) {
        super.submitList(pagedList);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Note>() {
                @Override
                public boolean areItemsTheSame(@NonNull Note oldUser, @NonNull Note newUser) {
                    return oldUser.getId() == newUser.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Note oldUser, @NonNull Note newUser) {
                    return oldUser.equals(newUser);
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