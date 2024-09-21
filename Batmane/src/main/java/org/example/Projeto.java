package org.example;

import java.awt.*;

public class Projeto {
    private int x, y;
    private int velocidade;
    private boolean ativo;

    public Projeto(int x, int y, int velocidade) {
        this.x = x;
        this.y = y;
        this.velocidade = velocidade;
        this.ativo = true;
    }

    public void mover() {
        y -= velocidade;
        if (y < 0) {
            ativo = false;
        }
    }

    public void render(Graphics g) {
        if (ativo) {
            g.setColor(Color.RED);
            g.fillRect(x, y, 5, 10);
        }
    }

    public boolean isAtivo() {
        return ativo;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLargura() {
        return 5;
    }

    public int getAltura() {
        return 10;
    }
}
