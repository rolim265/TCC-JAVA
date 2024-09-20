package org.example;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bloco {
    private int x, y, largura, altura;
    private Color cor;
    private Image imagem;

    public Bloco(int x, int y, int largura, int altura, Color cor, String caminhoImagem) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
        this.cor = cor;

        // Tente carregar a imagem, mas se falhar, não interrompa o programa
        if (caminhoImagem != null && !caminhoImagem.isEmpty()) {
            try {
                this.imagem = ImageIO.read(new File(caminhoImagem));
            } catch (IOException e) {
                System.err.println("Erro ao carregar a imagem: " + e.getMessage());
                this.imagem = null; // Se falhar, mantenha a imagem como null
            }
        } else {
            this.imagem = null; // Se o caminho da imagem for vazio, não carregue nada
        }
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

    public Color getCor() {
        return cor;
    }

    public Image getImagem() {
        return imagem;
    }

    // Método para renderizar o bloco
    public void render(Graphics g) {
        if (imagem != null) {
            g.drawImage(imagem, x, y, largura, altura, null);
        } else {
            g.setColor(cor);
            g.fillRect(x, y, largura, altura);
        }
    }
}
