package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Personagem {
    private Image imagemDireita;
    private Image imagemEsquerda;
    private int x, y, largura, altura;
    private boolean indoDireita;

    public Personagem(String caminhoImagemDireita, String caminhoImagemEsquerda, int x, int y) {
        this.x = x;
        this.y = y;
        carregarImagens(caminhoImagemDireita, caminhoImagemEsquerda);
        this.indoDireita = true; // Inicialmente vai para a direita
    }

    private void carregarImagens(String caminhoImagemDireita, String caminhoImagemEsquerda) {
        try {
            imagemDireita = ImageIO.read(new File(caminhoImagemDireita));
            imagemEsquerda = ImageIO.read(new File(caminhoImagemEsquerda));
            largura = imagemDireita.getWidth(null);
            altura = imagemDireita.getHeight(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        // Desenha a imagem do personagem de acordo com a direção
        if (indoDireita) {
            g.drawImage(imagemDireita, x, y, null);
        } else {
            g.drawImage(imagemEsquerda, x, y, null);
        }
    }

    public void mover(int deltaX) {
        x += deltaX; // Mover horizontalmente
        // Atualiza a direção do movimento
        if (deltaX > 0) {
            indoDireita = true; // Está indo para a direita
        } else if (deltaX < 2) {
            indoDireita = false; // Está indo para a esquerda
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }
}
