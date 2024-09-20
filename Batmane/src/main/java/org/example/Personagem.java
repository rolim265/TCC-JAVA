package org.example; // Declara o pacote da classe

import javax.swing.*; // Importa classes para a interface gráfica
import java.awt.*; // Importa classes para manipulação de gráficos
import java.io.File; // Importa a classe para manipulação de arquivos
import java.io.IOException; // Importa a classe para tratar exceções de entrada e saída
import javax.imageio.ImageIO; // Importa a classe para leitura e escrita de imagens

// Classe que representa o personagem do jogo
public class Personagem {
    private Image imagemDireita; // Imagem do personagem voltado para a direita
    private Image imagemEsquerda; // Imagem do personagem voltado para a esquerda
    private int x, y, largura, altura; // Posições e dimensões do personagem
    private boolean indoDireita; // Indica a direção do movimento do personagem

    // Construtor da classe Personagem
    public Personagem(String caminhoImagemDireita, String caminhoImagemEsquerda, int x, int y) {
        this.x = x; // Inicializa a coordenada x do personagem
        this.y = y; // Inicializa a coordenada y do personagem
        carregarImagens(caminhoImagemDireita, caminhoImagemEsquerda); // Carrega as imagens do personagem
        this.indoDireita = true; // Inicialmente, o personagem está indo para a direita
    }

    // Método privado para carregar as imagens do personagem
    private void carregarImagens(String caminhoImagemDireita, String caminhoImagemEsquerda) {
        try {
            imagemDireita = ImageIO.read(new File(caminhoImagemDireita)); // Carrega a imagem da direita
            imagemEsquerda = ImageIO.read(new File(caminhoImagemEsquerda)); // Carrega a imagem da esquerda
            largura = imagemDireita.getWidth(null); // Obtém a largura da imagem da direita
            altura = imagemDireita.getHeight(null); // Obtém a altura da imagem da direita
        } catch (IOException e) { // Captura exceções de entrada/saída
            e.printStackTrace(); // Exibe a pilha de erro
        }
    }

    // Método para renderizar o personagem na tela
    public void render(Graphics g) {
        // Desenha a imagem do personagem de acordo com a direção
        if (indoDireita) { // Se o personagem está indo para a direita
            g.drawImage(imagemDireita, x, y, null); // Desenha a imagem da direita
        } else { // Se o personagem está indo para a esquerda
            g.drawImage(imagemEsquerda, x, y, null); // Desenha a imagem da esquerda
        }
    }

    // Método para mover o personagem horizontalmente
    public void mover(int deltaX) {
        x += deltaX; // Atualiza a posição x do personagem
        // Atualiza a direção do movimento
        if (deltaX > 0) { // Se o deltaX for positivo
            indoDireita = true; // O personagem está indo para a direita
        } else if (deltaX < 0) { // Se o deltaX for negativo
            indoDireita = false; // O personagem está indo para a esquerda
        }
    }

    // Métodos getters e setters para acessar e modificar as propriedades do personagem
    public int getX() {
        return x; // Retorna a posição x do personagem
    }

    public void setX(int x) {
        this.x = x; // Define a posição x do personagem
    }

    public int getY() {
        return y; // Retorna a posição y do personagem
    }

    public void setY(int y) {
        this.y = y; // Define a posição y do personagem
    }

    public int getLargura() {
        return largura; // Retorna a largura do personagem
    }

    public int getAltura() {
        return altura; // Retorna a altura do personagem
    }
}
