package com.evo.summer.mobile.evo.evolab19.notelist;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evo.summer.mobile.evo.evolab19.databinding.ItemNoteBinding;
import com.evo.summer.mobile.evo.evolab19.pojo.Note;

class NoteViewHolder extends RecyclerView.ViewHolder {

    private OnClickListener onClickListener;

    public interface OnClickListener {
        void onClick(Note note);
    }

    private ItemNoteBinding binding;

    NoteViewHolder(@NonNull ItemNoteBinding binding, OnClickListener onClickListener) {
        super(binding.getRoot());
        this.binding = binding;
        this.onClickListener = onClickListener;
    }

    void bind(Note item) {
        binding.setItem(item);
        binding.getRoot().setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClick(item);
            }
        });
    }
}

