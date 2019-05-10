package com.evo.summer.mobile.evo.evolab19.notedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.evo.summer.mobile.evo.evolab19.R;
import com.evo.summer.mobile.evo.evolab19.databinding.ActivityNoteDetalsBinding;
import com.evo.summer.mobile.evo.evolab19.models.Note;
import com.evo.summer.mobile.evo.evolab19.viewmodel.NoteViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class NoteDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM = "EXTRA_ITEM";

    private Note note;
    private NoteViewModel viewModel;
    private ActivityNoteDetalsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_note_detals);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        note = (getIntent().getExtras() != null) ? getIntent().getParcelableExtra(EXTRA_ITEM) : new Note();

        binding.setItem(note);
        binding.edtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                note.setDescription(s.toString().replaceAll("\n", " "));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (getIntent().getExtras() != null) {
            inflater.inflate(R.menu.menu_note_details, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_item_share) {
            shareNote();
            return true;
        }
        if (itemId == R.id.menu_item_delete) {
            viewModel.delete(note);
            finish();
            return true;
        }
        if (itemId == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
//        if (!isChangingConfigurations()) {
            saveNote();
//        }
        super.finish();
    }


    public static Intent newIntent(Context packageContext, Note note) {
        Intent i = new Intent(packageContext, NoteDetailsActivity.class);
        i.putExtra(EXTRA_ITEM, note);
        return i;
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, NoteDetailsActivity.class);
    }

    private void shareNote() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(getString(R.string.share_note,
                        note.getTimeByFormat("HH:mm, dd MMMM (EEE)",
                                note.getTime()), note.getDescription()))
                .getIntent();
        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            shareIntent = Intent.createChooser(shareIntent, getString(R.string.send_report));
            startActivity(shareIntent);
        }
    }

    private void saveNote() {
        if (binding.edtInput.getText().toString().trim().isEmpty()) {
            return;
        }
        if (getIntent().getExtras() == null) {
            note.setTime(System.currentTimeMillis());
            viewModel.insert(note);
        } else {
            viewModel.update(note);
        }
    }
}
