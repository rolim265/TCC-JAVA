package org.example;

public class Fisica { // Define a classe Fisica
    private double velocidadeY = 0; // Velocidade vertical do personagem (para pular)
    private double gravidade = 0.8; // Valor da gravidade
    private double forcaPulo = -20; // Força do pulo (valor negativo para ir para cima)
    private boolean noChao = true; // Verifica se o personagem está no chão
    private int velocidadeX = 5; // Velocidade horizontal
    private int alturaChao; // Altura do chão

    // Construtor da classe Fisica que recebe a altura do chão
    public Fisica(int alturaChao) {
        this.alturaChao = alturaChao; // Inicializa a altura do chão
    }

    // Aplica a física de gravidade e movimento vertical
    public void aplicarFisica(Personagem personagem) {
        // Se o personagem não estiver no chão, aplica a gravidade
        if (!noChao) {
            velocidadeY += gravidade; // Aumenta a velocidade vertical conforme a gravidade
        }

        // Atualiza a posição vertical
        int novaY = personagem.getY() + (int) velocidadeY;

        // Verifica se o personagem colidiu com o chão
        if (novaY >= alturaChao) {
            novaY = alturaChao; // Coloca o personagem no chão
            velocidadeY = 0; // Reseta a velocidade vertical
            noChao = true; // O personagem está no chão
        } else {
            noChao = false; // O personagem não está no chão
        }

        personagem.setY(novaY); // Atualiza a posição do personagem
    }

    // Método para fazer o personagem pular
    public void pular() {
        if (noChao) { // Só permite pular se o personagem estiver no chão
            velocidadeY = forcaPulo; // Define a velocidade inicial do pulo
            noChao = false; // O personagem não está mais no chão
        }
    }

    // Movimento para a direita
    public void moverDireita(Personagem personagem) {
        personagem.setX(personagem.getX() + velocidadeX); // Move o personagem para a direita
    }

    // Movimento para a esquerda
    public void moverEsquerda(Personagem personagem) {
        personagem.setX(personagem.getX() - velocidadeX); // Move o personagem para a esquerda
    }
}
