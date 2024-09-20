package org.example; // Declara o pacote da classe

// Classe que gerencia a física do personagem no jogo
public class Fisica {
    private double velocidadeY = 0; // Velocidade vertical do personagem
    private double gravidade = 0.7; // Força da gravidade aplicada ao personagem
    private double forcaPulo = -22; // Força do pulo (valor negativo para mover para cima)
    private boolean noChao = true; // Indica se o personagem está no chão
    private int velocidadeX = 6; // Velocidade horizontal do personagem
    private int alturaChao; // Altura do chão

    private boolean movendoDireita = false; // Indica se o personagem está se movendo para a direita
    private boolean movendoEsquerda = false; // Indica se o personagem está se movendo para a esquerda

    // Construtor da classe Fisica
    public Fisica(int alturaChao) {
        this.alturaChao = alturaChao; // Inicializa a altura do chão
    }

    // Método para aplicar a física ao personagem
    public void aplicarFisica(Personagem personagem) {
        // Aplicar movimento horizontal
        if (movendoDireita) { // Se estiver se movendo para a direita
            personagem.setX(personagem.getX() + velocidadeX); // Move o personagem para a direita
        }
        if (movendoEsquerda) { // Se estiver se movendo para a esquerda
            personagem.setX(personagem.getX() - velocidadeX); // Move o personagem para a esquerda
        }

        // Aplicar física de pulo
        if (!noChao) { // Se o personagem não está no chão
            velocidadeY += gravidade; // Aumenta a velocidade Y pela gravidade
        }

        // Atualiza a posição Y do personagem
        int novaY = personagem.getY() + (int) velocidadeY; // Calcula a nova posição Y

        // Verifica se o personagem atingiu o chão
        if (novaY >= alturaChao) { // Se a nova posição Y for maior ou igual à altura do chão
            novaY = alturaChao; // Define novaY como a altura do chão
            velocidadeY = 0; // Reseta a velocidade Y
            noChao = true; // O personagem agora está no chão
        } else {
            noChao = false; // O personagem não está no chão
        }

        personagem.setY(novaY); // Atualiza a posição Y do personagem
    }

    // Método para fazer o personagem pular
    public void pular() {
        if (noChao) { // Permite pular apenas se estiver no chão
            velocidadeY = forcaPulo; // Define a velocidade Y para a força do pulo
            noChao = false; // O personagem não está mais no chão
        }
    }

    // Método para definir o movimento à direita
    public void moverDireita(boolean mover) {
        movendoDireita = mover; // Atualiza o estado de movimento para a direita
    }

    // Método para definir o movimento à esquerda
    public void moverEsquerda(boolean mover) {
        movendoEsquerda = mover; // Atualiza o estado de movimento para a esquerda
    }
}
