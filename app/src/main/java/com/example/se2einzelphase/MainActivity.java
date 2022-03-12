package com.example.se2einzelphase;

import android.os.Bundle;
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
        Button sendToServerButton = findViewById(R.id.bttnSendToServer);

        sendToServerButton.setOnClickListener(
                v -> runOnUiThread(new Thread(() -> {
                    try {
                        String response = new Se2Client().execute(textView.getText().toString()).get();
                        outputView.setText(response);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }))
        );

        Button calculationButton = findViewById(R.id.bttnCalculation);
        TextView outputCalulation = findViewById(R.id.outputCalc);
        calculationButton.setOnClickListener(view -> outputCalulation.setText(calculate(textView.getText().toString()))
        );
    }

    private String calculate(String matrikelNummer) {
        Integer intMatrikelNummer = Integer.valueOf(matrikelNummer);
        Integer crossTotal = calculateCrossTotal(intMatrikelNummer);
        return toBinary(crossTotal);
    }

    private String toBinary(Integer crossTotal) {
        StringBuilder stringBuilder = new StringBuilder();
        int quotient = crossTotal;
        while (quotient != 0) {
            int remainder = quotient % 2;
            stringBuilder.append(remainder);
            System.out.println(stringBuilder.toString());
            quotient = quotient / 2;
        }
        return stringBuilder.reverse().toString();
    }

    private Integer calculateCrossTotal(Integer intMatrikelNummer) {
        int crossTotal = 0;
        Integer matrikelNummer = intMatrikelNummer;
        while (matrikelNummer != 0) {
            crossTotal = crossTotal + (matrikelNummer % 10);

            matrikelNummer = matrikelNummer / 10;
        }
        return crossTotal;
    }
}