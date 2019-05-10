package com.evo.summer.mobile.evo.evolab19.notelist;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.evo.summer.mobile.evo.evolab19.R;
import com.evo.summer.mobile.evo.evolab19.databinding.ActivityMainBinding;
import com.evo.summer.mobile.evo.evolab19.notedetails.NoteDetailsActivity;
import com.evo.summer.mobile.evo.evolab19.utils.PreferencesUtil;
import com.evo.summer.mobile.evo.evolab19.viewmodel.NoteViewModel;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;


public class NoteListActivity extends AppCompatActivity implements LifecycleOwner, View.OnClickListener {

    private NoteListAdapter adapter;
    private NoteViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.floatingActionButton.setOnClickListener(this);
        setSupportActionBar(binding.toolbar);
        setTitle(R.string.note);

        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        subscribe();

        initRecyclerView();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_list, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        if (!PreferencesUtil.getStoreQuery(this).isEmpty()) {
            searchMenuItem.expandActionView();
            searchView.setQuery(PreferencesUtil.getStoreQuery(this), false);
            searchView.clearFocus();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                PreferencesUtil.setStoredQuery(getContext(), query);
//                viewModel.replaceSubscription(getViewLifecycleOwner());
//                subscribe();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                PreferencesUtil.setStoredQuery(NoteListActivity.this, s);
                viewModel.replaceSubscription(NoteListActivity.this);
                subscribe();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sub_sort_default) {
            if (PreferencesUtil.getSortQuery(this)) {
                PreferencesUtil.setSortQuery(this, false);
                viewModel.replaceSubscription(this);
                subscribe();
            }
        }
        if (item.getItemId() == R.id.sub_sort_time_create_ASC) {
            if (!PreferencesUtil.getSortQuery(this)) {
                PreferencesUtil.setSortQuery(this, true);
                viewModel.replaceSubscription(this);
                subscribe();
            }

        }
        if (item.getItemId() == R.id.sub_delete_all) {
            viewModel.deleteAllNotes();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, VERTICAL, false);
        binding.rvNoteList.setLayoutManager(layoutManager);
        binding.rvNoteList.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        adapter = new NoteListAdapter();
        binding.rvNoteList.setAdapter(adapter);
        adapter.setOnClickListener(note -> startActivity(NoteDetailsActivity.newIntent(this, note)));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NotNull RecyclerView recyclerView,
                                  @NotNull RecyclerView.ViewHolder viewHolder,
                                  @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(binding.rvNoteList);
    }

    private void subscribe() {
        viewModel.getNotes().observe(this, notes -> {
            binding.placeholderNote.setVisibility(!notes.isEmpty() ? View.GONE : View.VISIBLE);
            binding.rvNoteList.setVisibility(notes.isEmpty() ? View.GONE : View.VISIBLE);

            adapter.submitList(notes);
        });
    }

    @Override
    public void onClick(View v) {
        startActivity(NoteDetailsActivity.newIntent(this));
    }
}

