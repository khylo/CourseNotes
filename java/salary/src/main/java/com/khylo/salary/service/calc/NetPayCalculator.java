package com.khylo.salary.service.calc;

import java.util.Scanner;

enum PRSIClass {
    A(0.0465),
    B(0.0505),
    C(0.0555),
    D(0.0555),
    E(0.0555),
    F(0.0555);

    private final double prsiRate;

    PRSIClass(double prsiRate) {
        this.prsiRate = prsiRate;
    }

    public double getPrsiRate() {
        return prsiRate;
    }
}

public class NetPayCalculator {
    private static final PRSIClass[] PRSI_CLASSES = {PRSIClass.A, PRSIClass.B, PRSIClass.C, PRSIClass.D, PRSIClass.E, PRSIClass.F};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter gross pay: ");
        double grossPay = scanner.nextDouble();

        // TODO get std rate cut off //Get the user's tax credits
        //System.out.print("Enter standard rate tax credit: ");
        //double standardRateTaxCredit = scanner.nextDouble();

        // TODO get tax credits.
        //System.out.print("Enter higher rate tax credit: ");
        //double higherRateTaxCredit = scanner.nextDouble();

        //System.out.print("Enter USC additional earner tax credit: ");
        //double uscAdditionalEarnerTaxCredit = scanner.nextDouble();

        // Determine the PAYE tax band
        int taxBand;
        if (grossPay <= 37500) {
            taxBand = 1;
        } else if (grossPay <= 75000) {
            taxBand = 2;
        } else if (grossPay <= 100342) {
            taxBand = 3;
        } else if (grossPay <= 127219) {
            taxBand = 4;
        } else if (grossPay <= 150000) {
            taxBand = 5;
        } else {
            taxBand = 6;
        }

        // Calculate the PAYE tax
        double payeTax = calculatePayeTax(grossPay, taxBand);

        // Calculate the PRSI
        double prsi;
        System.out.print("Enter PRSI class: ");
        int prsiClass = scanner.nextInt();
        if (prsiClass < 0 || prsiClass >= PRSI_CLASSES.length) {
            throw new IllegalArgumentException("Invalid PRSI class: " + prsiClass);
        }
        prsi = grossPay * PRSI_CLASSES[prsiClass].getPrsiRate();

        // Calculate the USC
        double usc = calculateUsc(grossPay);

        // Calculate the net pay
        double netPay = grossPay - payeTax - prsi - usc;

        System.out.println("Net pay: " + netPay);
    }

    private static double calculatePayeTax(double grossPay, int taxBand) {
        switch (taxBand) {
            case 1:
                return grossPay * 0.2;
            case 2:
                return grossPay * 0.235;
            case 3:
                return grossPay * 0.284;
            case 4:
                return grossPay * 0.334;
            case 5:
                return grossPay * 0.358;
            default:
                return grossPay * 0.4;
        }
    }

    private static double calculateUsc(double grossPay) {
        if (grossPay <= 70000) {
            return grossPay * 0.005;
        } else {
            return grossPay * 0.008;
        }
    }
}
