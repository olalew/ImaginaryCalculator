package com.aleklew.calculator.modules.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ImaginaryNumber implements Parcelable {
    public static final Creator<ImaginaryNumber> CREATOR = new Creator<ImaginaryNumber>() {
        @Override
        public ImaginaryNumber createFromParcel(Parcel in) {
            return new ImaginaryNumber(in);
        }

        @Override
        public ImaginaryNumber[] newArray(int size) {
            return new ImaginaryNumber[size];
        }
    };

    private double number;
    private double imaginaryPart;

    public ImaginaryNumber(double number, double imaginaryPart) {
        this.number = number;
        this.imaginaryPart = imaginaryPart;
    }

    public ImaginaryNumber() {
        this(0.0, 0.0);
    }

    ImaginaryNumber(Parcel in) {
        number = in.readDouble();
        imaginaryPart = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(number);
        parcel.writeDouble(imaginaryPart);
    }

    // region GETTERS AND SETTERS

    public double getNumber() {
        return number;
    }

    public ImaginaryNumber setNumber(double number) {
        this.number = number;
        return this;
    }

    public double getImaginaryPart() {
        return imaginaryPart;
    }

    public ImaginaryNumber setImaginaryPart(double imaginaryPart) {
        this.imaginaryPart = imaginaryPart;
        return this;
    }

    // endregion

    public void addImaginaryNumber(ImaginaryNumber number) {
        this.number += number.getNumber();
        this.imaginaryPart += number.getImaginaryPart();
    }

    public void subtractImaginaryNumber(ImaginaryNumber number) {
        this.number -= number.getNumber();
        this.imaginaryPart -= number.getImaginaryPart();
    }

    public void multiplyImaginaryNumber(ImaginaryNumber number) {
        double tempNumber = this.number * number.getNumber() - this.imaginaryPart * number.imaginaryPart;
        double tempImaginaryNumber = this.number * number.getImaginaryPart()  + number.getImaginaryPart() * this.imaginaryPart;

        this.number = tempNumber;
        this.imaginaryPart = tempImaginaryNumber;
    }
}
