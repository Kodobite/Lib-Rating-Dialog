package io.kodebite.libratingdialog;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import io.kodebite.ratingdialog.RatingUtils;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.showDialog);

        button.setOnClickListener(v -> {
            RatingUtils ratingUtils = new RatingUtils(this);
            ratingUtils.setDialog();
            ratingUtils.showDialog();

        });

//        RatingUtils ratingUtils = new RatingUtils(this);
//        ratingUtils.setTargetLaunchCount(1);
//        ratingUtils.startLaunchCounting(true);
//        ratingUtils.setDialog();


    }
}