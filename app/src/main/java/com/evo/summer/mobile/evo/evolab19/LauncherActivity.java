package com.evo.summer.mobile.evo.evolab19;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.evo.summer.mobile.evo.evolab19.notelist.NoteListActivity;
import com.evo.summer.mobile.evo.evolab19.utils.PreferencesUtil;
import com.evo.summer.mobile.evo.evolab19.welcome.WelcomeActivity;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PreferencesUtil.getCheckFirstOpen(this)) {
            launchActivity(WelcomeActivity.class);
        } else {
            launchActivity(NoteListActivity.class);
        }
    }

    private void launchActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
        finish();
    }
}