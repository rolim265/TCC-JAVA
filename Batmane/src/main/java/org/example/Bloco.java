package org.example; // Declara o pacote da classe

import java.awt.*; // Importa classes para manipulação de gráficos
import java.io.File; // Importa a classe para manipulação de arquivos
import java.io.IOException; // Importa a classe para tratar exceções de entrada e saída
import javax.imageio.ImageIO; // Importa a classe para leitura e escrita de imagens

// Classe que representa um bloco no jogo
public class Bloco {
    private int x, y, largura, altura; // Coordenadas e dimensões do bloco
    private Color cor; // Cor do bloco
    private Image imagem; // Imagem do bloco

    // Construtor da classe Bloco
    public Bloco(int x, int y, int largura, int altura, Color cor, String caminhoImagem) {
        this.x = x; // Inicializa a coordenada x
        this.y = y; // Inicializa a coordenada y
        this.largura = largura; // Inicializa a largura
        this.altura = altura; // Inicializa a altura
        this.cor = cor; // Inicializa a cor

        // Tente carregar a imagem, mas se falhar, não interrompa o programa
        if (caminhoImagem != null && !caminhoImagem.isEmpty()) { // Verifica se o caminho da imagem não é nulo ou vazio
            try {
                this.imagem = ImageIO.read(new File(caminhoImagem)); // Tenta carregar a imagem do arquivo
            } catch (IOException e) { // Captura exceções de entrada/saída
                System.err.println("Erro ao carregar a imagem: " + e.getMessage()); // Exibe mensagem de erro
                this.imagem = null; // Se falhar, mantém a imagem como null
            }
        } else {
            this.imagem = null; // Se o caminho da imagem for vazio, não carrega nada
        }
    }

    // Métodos getters para acessar as propriedades do bloco
    public int getX() {
        return x; // Retorna a coordenada x
    }

    public int getY() {
        return y; // Retorna a coordenada y
    }

    public int getLargura() {
        return largura; // Retorna a largura
    }

    public int getAltura() {
        return altura; // Retorna a altura
    }

    public Color getCor() {
        return cor; // Retorna a cor
    }

    public Image getImagem() {
        return imagem; // Retorna a imagem
    }

    // Método para renderizar o bloco
    public void render(Graphics g) {
        if (imagem != null) { // Se a imagem foi carregada
            g.drawImage(imagem, x, y, largura, altura, null); // Desenha a imagem no bloco
        } else {
            g.setColor(cor); // Define a cor do bloco
            g.fillRect(x, y, largura, altura); // Desenha um retângulo preenchido com a cor
        }
    }
}
