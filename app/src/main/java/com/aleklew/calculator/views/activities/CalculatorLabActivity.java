package com.aleklew.calculator.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.aleklew.calculator.R;
import com.aleklew.calculator.modules.enums.CalculationOperation;
import com.aleklew.calculator.modules.models.ImaginaryNumber;

public class CalculatorLabActivity extends AppCompatActivity {

    public ActivityResultLauncher<Intent> graphLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() != Activity.RESULT_OK) {
                    return;
                }
            });

    private Spinner modeSpinner;
    private TextView resultTextView;
    private ImageView printGraphButton;

    private ImaginaryNumber resultNumber;

    private CalculationOperation selectedOperation;

    private AppCompatEditText realPartOneEditText, imaginaryOneEditText, realPartTwoEditText, imaginaryTwoEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_calculator);

        initView();
    }

    private void initView() {
        this.modeSpinner = findViewById(R.id.modeSpinner);
        this.initSpinnerOptions();

        this.resultTextView = findViewById(R.id.resultTextView);

        this.realPartOneEditText = findViewById(R.id.realPartOneEditText);
        this.imaginaryOneEditText = findViewById(R.id.imaginaryOneEditText);
        this.realPartTwoEditText = findViewById(R.id.realPartTwoEditText);
        this.imaginaryTwoEditText = findViewById(R.id.imaginaryTwoEditText);

        this.initButton();
    }

    private void initButton() {
        this.printGraphButton = findViewById(R.id.printGraphButton);
        this.printGraphButton.setOnClickListener(view -> {
            if (resultNumber == null) {
                Toast.makeText(CalculatorLabActivity.this, "Nie wyliczono rezultatu",
                        Toast.LENGTH_LONG).show();
                return;
            }

            Intent printGraphIntent = new Intent(CalculatorLabActivity.this, GraphActivity.class);
            printGraphIntent.putExtra("number", resultNumber);
            graphLauncher.launch(printGraphIntent);
        });

        findViewById(R.id.calculateButton).setOnClickListener((v) -> {
            calculateResult();
        });
    }

    private void calculateResult() {
        ImaginaryNumber numberOne = new ImaginaryNumber(Double.parseDouble(realPartOneEditText.getText().toString()),
                Double.parseDouble(imaginaryOneEditText.getText().toString()));

        ImaginaryNumber numberTwo = new ImaginaryNumber(Double.parseDouble(realPartTwoEditText.getText().toString()),
                Double.parseDouble(imaginaryTwoEditText.getText().toString()));

        switch (selectedOperation) {
            case ADD:
                numberOne.addImaginaryNumber(numberTwo);
                break;
            case SUBTRACT:
                numberOne.subtractImaginaryNumber(numberTwo);
                break;
            case MULTIPLY:
                numberOne.multiplyImaginaryNumber(numberOne);
                break;
            default:
                break;
        }

        this.resultNumber = numberOne;

        String formatted = getFormattedResult(numberOne);
        this.resultTextView.setText(formatted);
    }

    private String getFormattedResult(ImaginaryNumber number) {
        return String.format("%.5f + %.5f i", number.getNumber(),  number.getImaginaryPart());
    }

    private void initSpinnerOptions() {
        String[] options = {
                "Dodawanie",
                "Odejmowanie",
                "Mnożenie"
        };

        CalculationOperation[] operations = {
            CalculationOperation.ADD,
            CalculationOperation.SUBTRACT,
            CalculationOperation.MULTIPLY
        };

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, options);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(adapter);
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                CalculatorLabActivity.this.selectedOperation = operations[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        this.selectedOperation = operations[0];
        modeSpinner.setSelection(0);
    }
}
