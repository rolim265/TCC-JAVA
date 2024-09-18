package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Personagem {
    private Image imagem;
    private int x, y;

    // Construtor do personagem
    public Personagem(String caminhoImagem, int x, int y) {
        this.x = x;
        this.y = y;

        // Carrega a imagem usando o caminho absoluto
        try {
            imagem = ImageIO.read(new File(caminhoImagem));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a imagem: " + caminhoImagem);
        }
    }

    // Desenha o personagem na tela
    public void render(Graphics g) {
        g.drawImage(imagem, x, y, null);
    }

    // Getters e Setters para posição
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
}
