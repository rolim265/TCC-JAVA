package org.example;

import java.awt.*;

public class Bola {
    private int x, y;
    private final int largura = 50;
    private final int altura = 50;
    private final int velocidade = 2;

    public Bola(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void mover() {
        y += velocidade; // Move a bola para baixo
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, largura, altura);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public boolean isForaDaTela(int alturaTela) {
        return y > alturaTela;
    }
}
