package org.example;

public class Fisica {
    private double velocidadeY = 0; // Velocidade vertical do personagem (para pular)
    private double gravidade = 0.8; // Valor da gravidade
    private double forcaPulo = -12; // Força do pulo (valor negativo para ir para cima)
    private boolean noChao = true;  // Verifica se o personagem está no chão
    private int velocidadeX = 5; // Velocidade horizontal

    // Aplica a física de gravidade e movimento vertical
    public void aplicarFisica(Personagem personagem, int alturaChao) {
        // Se o personagem não estiver no chão, aplica a gravidade
        if (!noChao) {
            velocidadeY += gravidade; // Aumenta a velocidade vertical conforme a gravidade
        }

        // Atualiza a posição vertical
        personagem.setY(personagem.getY() + (int) velocidadeY);

        // Verifica se o personagem colidiu com o chão
        if (personagem.getY() >= alturaChao) {
            personagem.setY(alturaChao); // Coloca o personagem no chão
            velocidadeY = 0; // Reseta a velocidade vertical
            noChao = true; // O personagem está no chão
        } else {
            noChao = false; // O personagem não está no chão
        }
    }

    // Método para fazer o personagem pular
    public void pular() {
        if (noChao) { // Só permite pular se o personagem estiver no chão
            velocidadeY = forcaPulo;
            noChao = false; // O personagem não está mais no chão
        }
    }

    // Movimento para a direita
    public void moverDireita(Personagem personagem) {
        personagem.setX(personagem.getX() + velocidadeX);
    }

    // Movimento para a esquerda
    public void moverEsquerda(Personagem personagem) {
        personagem.setX(personagem.getX() - velocidadeX);
    }
}
