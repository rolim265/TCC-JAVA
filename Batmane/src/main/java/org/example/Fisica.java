package org.example;

public class Fisica {
    private double velocidadeY = 0;
    private double gravidade = 0.7;
    private double forcaPulo = -22; // Ajuste a força do pulo conforme necessário
    private boolean noChao = true;
    private int velocidadeX = 6;
    private int alturaChao;

    private boolean movendoDireita = false;
    private boolean movendoEsquerda = false;

    public Fisica(int alturaChao) {
        this.alturaChao = alturaChao;
    }

    public void aplicarFisica(Personagem personagem) {
        // Aplicar movimento horizontal
        if (movendoDireita) {
            personagem.setX(personagem.getX() + velocidadeX);
        }
        if (movendoEsquerda) {
            personagem.setX(personagem.getX() - velocidadeX);
        }

        // Aplicar física de pulo
        if (!noChao) {
            velocidadeY += gravidade; // Aplica gravidade quando não está no chão
        }

        // Atualiza a posição Y do personagem
        int novaY = personagem.getY() + (int) velocidadeY;

        // Verifica se o personagem atingiu o chão
        if (novaY >= alturaChao) {
            novaY = alturaChao;
            velocidadeY = 0; // Reseta a velocidade Y
            noChao = true; // O personagem agora está no chão
        } else {
            noChao = false; // O personagem não está no chão
        }

        personagem.setY(novaY); // Atualiza a posição Y
    }

    public void pular() {
        if (noChao) { // Permite pular apenas se estiver no chão
            velocidadeY = forcaPulo; // Aplica a força do pulo
            noChao = false; // O personagem não está mais no chão
        }
    }

    public void moverDireita(boolean mover) {
        movendoDireita = mover;
    }

    public void moverEsquerda(boolean mover) {
        movendoEsquerda = mover;
    }
}
