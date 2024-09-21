package org.example;

public class Fisica {
    private double velocidadeY = 0;
    private double gravidade = 0.7;
    private double forcaPulo = -22;
    private boolean noChao = true;
    private int velocidadeX = 6;
    private int alturaChao;

    private boolean movendoDireita = false;
    private boolean movendoEsquerda = false;

    public Fisica(int alturaChao) {
        this.alturaChao = alturaChao;
    }

    public void aplicarFisica(Personagem personagem) {
        if (movendoDireita) {
            personagem.setX(personagem.getX() + velocidadeX);
        }
        if (movendoEsquerda) {
            personagem.setX(personagem.getX() - velocidadeX);
        }

        if (!noChao) {
            velocidadeY += gravidade;
        }

        int novaY = personagem.getY() + (int) velocidadeY;

        if (novaY >= alturaChao) {
            novaY = alturaChao;
            velocidadeY = 0;
            noChao = true;
        } else {
            noChao = false;
        }

        personagem.setY(novaY);
    }

    public void pular() {
        if (noChao) {
            velocidadeY = forcaPulo;
            noChao = false;
        }
    }

    public void moverDireita(boolean mover) {
        movendoDireita = mover;
    }

    public void moverEsquerda(boolean mover) {
        movendoEsquerda = mover;
    }
}
