package com.evo.summer.mobile.evo.evolab19.welcome;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.evo.summer.mobile.evo.evolab19.LauncherActivity;
import com.evo.summer.mobile.evo.evolab19.R;
import com.evo.summer.mobile.evo.evolab19.databinding.ActivityWelcomeBinding;
import com.evo.summer.mobile.evo.evolab19.utils.PreferencesUtil;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWelcomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        binding.buttonStart.setOnClickListener(v -> {
            PreferencesUtil.setCheckFirstOpen(this);
            PreferencesUtil.setGenerateData(this, binding.switchGenerateData.isChecked());
            startActivity(new Intent(this, LauncherActivity.class));
            finish();
        });

    }
}
