package com.example.logoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResetActivity extends AppCompatActivity {

    private Button button;

    TextView pointsDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        initView();

        button = (Button) findViewById(R.id.resetButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenMainActivity();
            }
        });
    }

    private void initView() {
        Bundle extras = getIntent().getExtras();
        String strPointsDisplay = extras.getString("strTotalPoints");
        int totalPoints = Integer.parseInt(strPointsDisplay);

        pointsDisplay = (TextView)findViewById(R.id.pointsDisplay);
        pointsDisplay.setText("Your points: " + totalPoints);
    }

    public void OpenMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
