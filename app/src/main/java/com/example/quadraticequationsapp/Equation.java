package com.example.quadraticequationsapp;

public class Equation {
    private String equationText;
    private int x1;
    private int x2;
    public Equation() {}
    // Эта функция генерирует такие квадратные уравнения, которые всегда будут иметь целые решения которые мы сами задаем как x1 и x2
    public void generateEquation(int a, int x1, int x2) {
        this.x1 = x1;
        this.x2 = x2;
        int b = -a * (x1 + x2);
        int c = a * x1 * x2;

        equationText = (a + "x^2 " + formatCoefficent(b) + b + "x " + formatCoefficent(c) + c + " = 0");
    }

    private String formatCoefficent(int n) {
        return n > 0 ? "+" : "";
    }

    public String getEquationText() {
        return equationText;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }
}
