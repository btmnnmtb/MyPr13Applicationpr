package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences themeSettings;
    private SharedPreferences.Editor settingsEditor;
    private ImageButton imageTheme;
    private Button[][] buttons = new Button[3][3];
    private boolean playerXTurn = true; // true - X, false - O
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        // Получаем SharedPreferences
        themeSettings = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        if (!themeSettings.contains("MODE_NIGHT_ON")) {
            settingsEditor = themeSettings.edit();
            settingsEditor.putBoolean("MODE_NIGHT_ON", false);
            settingsEditor.apply();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Toast.makeText(this, "С запуском", Toast.LENGTH_SHORT).show();
        } else {
            setCurrentTheme();
        }

        setContentView(R.layout.activity_main);

        // Находим кнопка для изменения темы
        button = findViewById(R.id.butsled) ;
        imageTheme = findViewById(R.id.Img);
        updateImageButton();

        // Устанавливаем слушатель клика для кнопки смены темы
        imageTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTheme();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);

            }
        });


        initGameButtons();
    }

    private void initGameButtons() {
        buttons[0][0] = findViewById(R.id.but1);
        buttons[0][1] = findViewById(R.id.but2);
        buttons[0][2] = findViewById(R.id.but3);
        buttons[1][0] = findViewById(R.id.but4);
        buttons[1][1] = findViewById(R.id.but5);
        buttons[1][2] = findViewById(R.id.but6);
        buttons[2][0] = findViewById(R.id.but7);
        buttons[2][1] = findViewById(R.id.but8);
        buttons[2][2] = findViewById(R.id.but9);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int x = i;
                final int y = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onGameButtonClick(x, y);
                    }
                });
            }
        }
    }

    private void onGameButtonClick(int x, int y) {
        if (buttons[x][y].getText().toString().equals("")) {
            buttons[x][y].setText(playerXTurn ? "X" : "O");
            playerXTurn = !playerXTurn;
            checkForWinner();
        }
    }

    private void checkForWinner() {

        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().toString().equals(buttons[i][1].getText().toString()) &&
                    buttons[i][0].getText().toString().equals(buttons[i][2].getText().toString()) &&
                    !buttons[i][0].getText().toString().equals("")) {
                showWinner(buttons[i][0].getText().toString());
                return;
            }
        }


        for (int i = 0; i < 3; i++) {
            if (buttons[0][i].getText().toString().equals(buttons[1][i].getText().toString()) &&
                    buttons[0][i].getText().toString().equals(buttons[2][i].getText().toString()) &&
                    !buttons[0][i].getText().toString().equals("")) {
                showWinner(buttons[0][i].getText().toString());
                return;
            }
        }


        if (buttons[0][0].getText().toString().equals(buttons[1][1].getText().toString()) &&
                buttons[0][0].getText().toString().equals(buttons[2][2].getText().toString()) &&
                !buttons[0][0].getText().toString().equals("")) {
            showWinner(buttons[0][0].getText().toString());
            return;
        }

        if (buttons[0][2].getText().toString().equals(buttons[1][1].getText().toString()) &&
                buttons[0][2].getText().toString().equals(buttons[2][0].getText().toString()) &&
                !buttons[0][2].getText().toString().equals("")) {
            showWinner(buttons[0][2].getText().toString());
            return;
        }


        boolean isDraw = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().equals("")) {
                    isDraw = false;
                    break;
                }
            }
        }
        if (isDraw) {
            showDraw();
        }
    }

    private void showWinner(String winner) {
        Toast.makeText(this, "Победитель: " + winner, Toast.LENGTH_SHORT).show();
        resetGame();
    }

    private void showDraw() {
        Toast.makeText(this, "Ничья!", Toast.LENGTH_SHORT).show();
        resetGame();
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        playerXTurn = true;
    }

    private void toggleTheme() {
        if (themeSettings.getBoolean("MODE_NIGHT_ON", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            settingsEditor = themeSettings.edit();
            settingsEditor.putBoolean("MODE_NIGHT_ON", false);
            settingsEditor.apply();
            Toast.makeText(MainActivity.this, "Тёмная тема отключена", Toast.LENGTH_SHORT).show();
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            settingsEditor = themeSettings.edit();
            settingsEditor.putBoolean("MODE_NIGHT_ON", true);
            settingsEditor.apply();
            Toast.makeText(MainActivity.this, "Тёмная тема включена", Toast.LENGTH_SHORT).show();
        }
        updateImageButton();
        setCurrentTheme();
    }

    private void updateImageButton() {
        if (themeSettings.getBoolean("MODE_NIGHT_ON", false)) {
            imageTheme.setImageResource(R.drawable.sol); // иконка для светлой темы
        } else {
            imageTheme.setImageResource(R.drawable.luna); // иконка для темной темы
        }
    }

    private void setCurrentTheme() {
        if (themeSettings.getBoolean("MODE_NIGHT_ON", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

}