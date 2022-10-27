package com.aleklew.calculator.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aleklew.calculator.R;
import com.aleklew.calculator.modules.enums.CalculationOperation;
import com.aleklew.calculator.modules.enums.NumberMode;
import com.aleklew.calculator.modules.models.ImaginaryNumber;
import com.aleklew.calculator.modules.services.CalculatorService;

public class CalculatorMainActivity extends AppCompatActivity {

    public ActivityResultLauncher<Intent> graphLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() != Activity.RESULT_OK) {
                    return;
                }
            });

    private int modeIndex = 1;

    private Spinner modeSpinner;
    private TextView resultTextView;
    private ImageView printGraphButton;

    private CalculatorService service = new CalculatorService();

    private NumberMode numberMode = NumberMode.REAL;

    private Button button0, button1, button2, button3, button4, button5, button6, button7,
        button8, button9, addButton, subtractButton, multiplyButton, iButton, equalButton, clearButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strange_calculator);

        initView();
    }

    // region View

    private void initView() {
        this.modeSpinner = findViewById(R.id.modeSpinner);
        this.initSpinnerOptions();

        this.resultTextView = findViewById(R.id.resultTextView);
        this.resultTextView.setText(service.getFormattedResult());

        this.initButton();
    }

    private void initButton() {
        this.printGraphButton = findViewById(R.id.printGraphButton);
        this.printGraphButton.setOnClickListener(view -> {
            Intent printGraphIntent = new Intent(CalculatorMainActivity.this, GraphActivity.class);
            printGraphIntent.putExtra("number", service.getResultNumber());
            graphLauncher.launch(printGraphIntent);
        });

        // region buttons

        this.button0 = this.handleNumeric(findViewById(R.id.button0), 0);
        this.button1 = this.handleNumeric(findViewById(R.id.button1), 1);
        this.button2 = this.handleNumeric(findViewById(R.id.button2), 2);
        this.button3 = this.handleNumeric(findViewById(R.id.button3), 3);
        this.button4 = this.handleNumeric(findViewById(R.id.button4), 4);
        this.button5 = this.handleNumeric(findViewById(R.id.button5), 5);
        this.button6 = this.handleNumeric(findViewById(R.id.button6), 6);
        this.button7 = this.handleNumeric(findViewById(R.id.button7), 7);
        this.button8 = this.handleNumeric(findViewById(R.id.button8), 8);
        this.button9 = this.handleNumeric(findViewById(R.id.button9), 9);

        this.iButton = findViewById(R.id.buttonI);
        this.iButton.setOnClickListener((v) -> {
            this.numberMode = NumberMode.IMAGINARY;
        });

        this.multiplyButton = this.handleOperation(findViewById(R.id.multiplyButton), CalculationOperation.MULTIPLY);
        this.addButton = this.handleOperation(findViewById(R.id.plusButton), CalculationOperation.ADD);
        this.subtractButton = this.handleOperation(findViewById(R.id.minusButton), CalculationOperation.SUBTRACT);

        this.clearButton = findViewById(R.id.clearButton);
        this.clearButton.setOnClickListener((v) -> {
            service.clear();
            this.numberMode = NumberMode.REAL;
            service.setResultNumber(new ImaginaryNumber());
            CalculatorMainActivity.this.resultTextView.setText(service.getFormattedOutput());
        });

        this.equalButton = findViewById(R.id.equalButton);
        this.equalButton.setOnClickListener((v) -> {
            try {
                service.calculate();
                this.numberMode = NumberMode.REAL;
            } catch (Exception e) {}

            CalculatorMainActivity.this.resultTextView.setText(service.getFormattedOutput());
        });

        // endregion
    }

    private Button handleNumeric(Button button, int value) {
        button.setOnClickListener(view -> {
            service.appendNumber(value, numberMode, false);
            CalculatorMainActivity.this.resultTextView.setText(service.getFormattedOutput());
        });

        return button;
    }

    private Button handleOperation(Button button, CalculationOperation operation) {
        button.setOnClickListener(view -> {
            service.setOperation(operation);
            CalculatorMainActivity.this.resultTextView.setText(service.getFormattedOutput());
        });

        return button;
    }

    private void initSpinnerOptions() {
        String[] options = {
                "COŚ 1",
                "COŚ 2",
                "COŚ 3"
        };
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, options);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(adapter);
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                CalculatorMainActivity.this.modeIndex = pos;
                String option = options[CalculatorMainActivity.this.modeIndex];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        modeSpinner.setSelection(modeIndex);
    }

    // endregion

}
