package com.example.quadraticequationsapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView equationText;
    private EditText fieldX1;
    private EditText fieldX2;
    private Button checkButton;
    private Equation equation;
    private Context context;
    private String x1text;
    private String x2text;
    private String equationString;
    private int x1;
    private int x2;

    private void init(Bundle savedInstanceState) {
        equationText = findViewById(R.id.equationText);
        fieldX1 = findViewById(R.id.fieldX1);
        fieldX2 = findViewById(R.id.fieldX2);
        checkButton = findViewById(R.id.checkButton);
        context = getApplicationContext();
        checkButton.setActivated(false);

        checkButton.setOnClickListener(checkButtonClickListener);
        fieldX1.setOnFocusChangeListener(fieldFocusChangeListener);
        fieldX2.setOnFocusChangeListener(fieldFocusChangeListener);

        if (savedInstanceState != null) {
            equationString = savedInstanceState.getString("equationString");
            x1text = savedInstanceState.getString("x1text");
            x2text = savedInstanceState.getString("x2text");
        } else {
            x1text = "";
            x2text = "";

            equation = new Equation();
            Random r = new Random();
            equation.generateEquation(r.nextInt(10) + 1, r.nextInt(100) - 50, r.nextInt(100) - 50);
            equationString = equation.getEquationText();
            equationText.setText(equationString);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init(savedInstanceState);
    }

    private final View.OnFocusChangeListener fieldFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            // проверять только если это view который стал неактивен
            if (!hasFocus) {
                if (fieldX1.getText().toString().equals("") || fieldX2.getText().toString().equals("")) {
                    Toast.makeText(context, "Пустая строка!", Toast.LENGTH_SHORT).show();
                } else {
                    checkButton.setActivated(true);
                }
            }
        }
    };

    private final View.OnClickListener checkButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                x1 = Integer.parseInt(fieldX1.getText().toString());
                x2 = Integer.parseInt(fieldX2.getText().toString());
                // проверять все случаи, пользователь может ввести правильное решение но в разные поля
                if ((x1 == equation.getX1() && x2 == equation.getX2()) || (x1 == equation.getX2() && x2 == equation.getX1())) {
                    Toast.makeText(context, "Правильное решение!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Неправильное решение!", Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException ignore) {
                Toast.makeText(context, "Неправльное значение!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        equationText.setText(equationString);
        fieldX1.setText(x1text);
        fieldX2.setText(x2text);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("equationString", equationString);
        outState.putString("x1text", x1text);
        outState.putString("x2text", x2text);
    }
}