package com.example.ameramain;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Проверка на любое касание экрана
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Переход на SigninActivity при любом касании экрана
            startActivity(new Intent(MainActivity.this, SigninActivity.class));
            finish(); // Закрыть текущую активность
            return true;
        }
        return super.onTouchEvent(event);
    }
}
