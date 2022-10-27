package com.aleklew.calculator.modules.services;

import com.aleklew.calculator.modules.enums.CalculationOperation;
import com.aleklew.calculator.modules.enums.NumberMode;
import com.aleklew.calculator.modules.models.ImaginaryNumber;

public class CalculatorService {

    private ImaginaryNumber resultNumber;
    private ImaginaryNumber manipulationNumber;

    private CalculationOperation operation;

    public CalculatorService(ImaginaryNumber initNumber) {
        resultNumber = initNumber;
    }

    public CalculatorService() {
        resultNumber = new ImaginaryNumber();
    }

    public ImaginaryNumber calculate() throws Exception {
        switch (operation) {
            case ADD:
                resultNumber.addImaginaryNumber(manipulationNumber);
                break;
            case SUBTRACT:
                resultNumber.subtractImaginaryNumber(manipulationNumber);
                break;
            case MULTIPLY:
                resultNumber.multiplyImaginaryNumber(manipulationNumber);
                break;
            default:
                throw new Exception("Operation undefined");
        }

        clear();
        return resultNumber;
    }

    public boolean canPerformCalculation() {
        return manipulationNumber != null && operation != null;
    }

    public void clear() {
        manipulationNumber = null;
        operation = null;
    }

    public ImaginaryNumber getResultNumber() {
        return resultNumber;
    }

    public String getFormattedResult() {
       return getFormattedResult(resultNumber);
    }

    private String getFormattedResult(ImaginaryNumber number) {
        return String.format("%.5f + %.5f i", number.getNumber(),  number.getImaginaryPart());
    }

    public String getFormattedOutput() {
        if (operation == null) {
            return getFormattedResult();
        }

        if (manipulationNumber == null) {
            return getFormattedResult();
        }

        return getFormattedResult(manipulationNumber);
    }

    public void appendNumber(int number) {
        appendNumber(number, NumberMode.REAL, false);
    }

    public void appendNumber(int number, NumberMode mode, boolean dot) {
        if (operation == null) {
            if (mode == NumberMode.REAL) {
                resultNumber.setNumber(resultNumber.getNumber() * 10 + number);
            } else if(mode == NumberMode.IMAGINARY) {
                resultNumber.setImaginaryPart(resultNumber.getImaginaryPart() * 10 + number);
            }
            return;
        }

        if (manipulationNumber == null) {
            manipulationNumber = new ImaginaryNumber(number, 0.0);
            return;
        }

        if (mode == NumberMode.REAL) {
            manipulationNumber.setNumber(manipulationNumber.getNumber() * 10 + number);
        } else if(mode == NumberMode.IMAGINARY) {
            manipulationNumber.setImaginaryPart(manipulationNumber.getImaginaryPart() * 10 + number);
        }
    }

    public CalculatorService setResultNumber(ImaginaryNumber resultNumber) {
        this.resultNumber = resultNumber;
        return this;
    }

    public ImaginaryNumber getManipulationNumber() {
        return manipulationNumber;
    }

    public CalculatorService setManipulationNumber(ImaginaryNumber manipulationNumber) {
        this.manipulationNumber = manipulationNumber;
        return this;
    }

    public CalculationOperation getOperation() {
        return operation;
    }

    public CalculatorService setOperation(CalculationOperation operation) {
        this.operation = operation;
        this.manipulationNumber = null;
        return this;
    }
}
