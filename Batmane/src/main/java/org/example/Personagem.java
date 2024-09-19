package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Personagem { // Define a classe Personagem
    private Image imagem; // Declara um objeto Image para armazenar a imagem do personagem
    private int x, y; // Declara variáveis para armazenar a posição do personagem

    // Construtor do personagem
    public Personagem(String caminhoImagem, int x, int y) {
        this.x = x; // Inicializa a coordenada x do personagem
        this.y = y; // Inicializa a coordenada y do personagem

        // Carrega a imagem usando o caminho absoluto
        try {
            imagem = ImageIO.read(new File(caminhoImagem)); // Tenta ler a imagem do arquivo
            if (imagem == null) { // Verifica se a imagem foi carregada corretamente
                System.out.println("A imagem não pôde ser carregada: " + caminhoImagem); // Mensagem de erro
            } else {
                System.out.println("Imagem carregada com sucesso: " + caminhoImagem); // Mensagem de sucesso
                System.out.println("Dimensões da imagem: " + imagem.getWidth(null) + "x" + imagem.getHeight(null)); // Exibe as dimensões da imagem
            }
        } catch (IOException e) {
            e.printStackTrace(); // Imprime a pilha de erros se a leitura da imagem falhar
            System.out.println("Erro ao carregar a imagem: " + caminhoImagem); // Mensagem de erro
        }
    }

    // Método para retornar a imagem carregada
    public Image getImage() {
        return imagem; // Retorna o objeto Image
    }

    // Desenha o personagem na tela
    public void render(Graphics g) {
        if (imagem != null) { // Verifica se a imagem foi carregada
            g.drawImage(imagem, x, y, null); // Desenha a imagem do personagem na posição (x, y)
            System.out.println("Renderizando personagem na posição: (" + x + ", " + y + ")"); // Mensagem de depuração
        } else {
            System.out.println("A imagem do personagem não foi carregada, não é possível renderizar."); // Mensagem de erro se a imagem não for carregada
        }
    }

    // Getters e Setters para posição
    public int getX() {
        return x; // Retorna a coordenada x do personagem
    }

    public void setX(int x) {
        this.x = x; // Define a coordenada x do personagem
    }

    public int getY() {
        return y; // Retorna a coordenada y do personagem
    }

    public void setY(int y) {
        this.y = y; // Define a coordenada y do personagem
    }
}
