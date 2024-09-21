package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Personagem {
    private Image imagemDireita;
    private Image imagemEsquerda;
    private int x, y, largura, altura;
    private boolean indoDireita;
    public ArrayList<Projeto> projetos;

    public Personagem(String caminhoImagemDireita, String caminhoImagemEsquerda, int x, int y) {
        try {
            imagemDireita = ImageIO.read(new File(caminhoImagemDireita));
            imagemEsquerda = ImageIO.read(new File(caminhoImagemEsquerda));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.x = x;
        this.y = y;
        this.largura = 500;
        this.altura = 500;
        this.projetos = new ArrayList<>();
    }

    public void render(Graphics g) {
        if (indoDireita) {
            g.drawImage(imagemDireita, x, y, largura, altura, null);
        } else {
            g.drawImage(imagemEsquerda, x, y, largura, altura, null);
        }
    }

    public void mover(int dx) {
        x += dx;
        indoDireita = dx > 0;
    }

    public void atirar() {
        projetos.add(new Projeto(x + largura / 2, y + 250, 5));
    }

    public void atualizarProjetos() {
        for (int i = 0; i < projetos.size(); i++) {
            Projeto projeto = projetos.get(i);
            projeto.mover();
            if (!projeto.isAtivo()) {
                projetos.remove(i);
                i--; // Corrige o índice após remoção
            }
        }
    }

    public void renderProjetos(Graphics g) {
        for (Projeto projeto : projetos) {
            projeto.render(g);
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }
}
