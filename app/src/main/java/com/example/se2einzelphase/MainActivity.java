package com.example.se2einzelphase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.matrikelNummerField);
        TextView outputView = findViewById(R.id.outputField);
        Button button = findViewById(R.id.bttnSendToServer);

        button.setOnClickListener(
                v -> runOnUiThread(new Thread(() -> {
                    try {
                        String response = new Se2Client().execute(textView.getText().toString()).get();
                        outputView.setText(response);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }))
        );
    }
}