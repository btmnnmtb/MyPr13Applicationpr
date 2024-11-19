package com.example.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    private int score = 0;
    private TextView scoreText;
    private ImageView hamsterImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        scoreText = findViewById(R.id.scoreText);
        hamsterImage = findViewById(R.id.hamsterImage);

        hamsterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseScore();
                animateHamster();
            }
        });
    }

    private void increaseScore() {
        score++;
        scoreText.setText("Score: " + score);
    }
    private void animateHamster() {

        ObjectAnimator scaleUp = ObjectAnimator.ofPropertyValuesHolder(
                hamsterImage,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleUp.setDuration(100);

        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                hamsterImage,
                PropertyValuesHolder.ofFloat("scaleX", 1f),
                PropertyValuesHolder.ofFloat("scaleY", 1f));
        scaleDown.setDuration(100);

        scaleUp.start();
        scaleUp.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                scaleDown.start();
            }
        });
    }
}
