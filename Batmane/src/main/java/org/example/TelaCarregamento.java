package org.example; // Declara o pacote da classe

import javax.swing.*; // Importa classes para a interface gráfica
import java.awt.*; // Importa classes para manipulação de gráficos
import java.io.File; // Importa a classe para manipulação de arquivos
import java.io.IOException; // Importa a classe para tratar exceções de entrada e saída
import javax.imageio.ImageIO; // Importa a classe para leitura e escrita de imagens

// Classe que representa a tela de carregamento do jogo
public class TelaCarregamento extends JPanel {
    private Image imagemCarregamento; // Imagem de carregamento
    private int tempoRestante = 3; // 30 segundos para carregar
    private boolean jogoIniciado = false; // Verifica se o jogo já foi iniciado

    // Construtor da tela de carregamento
    public TelaCarregamento() {
        // Carrega a imagem de carregamento
//        JOptionPane.showConfirmDialog(null, "Volta pra mim amandinha!", "Volta pra mim amandinha!", JOptionPane.WARNING_MESSAGE);
        try {
            imagemCarregamento = ImageIO.read(new File("src/main/java/img/img_1.png")); // Lê a imagem de carregamento
        } catch (IOException e) { // Captura exceções de entrada/saída
            e.printStackTrace(); // Exibe a pilha de erro
        }

        // Cria o JFrame para a tela de carregamento
        JFrame frame = new JFrame("Carregando..."); // Título da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha o aplicativo ao fechar a janela
        frame.setSize(new Dimension(800, 600)); // Define o tamanho da janela
        frame.setLocationRelativeTo(null); // Centraliza a janela na tela
        frame.add(this); // Adiciona o painel de carregamento à janela
        frame.setVisible(true); // Torna a janela visível

        // Timer para a contagem regressiva de segundos
        Timer timer = new Timer(1000, e -> { // Timer que dispara a cada 1000 ms (1 segundo)
            tempoRestante--; // Decrementa o tempo restante
            repaint(); // Atualiza a tela a cada segundo

            // Verifica se o tempo restante chegou a zero e se o jogo ainda não foi iniciado
            if (tempoRestante <= 0 && !jogoIniciado) {
                jogoIniciado = true; // Marca que o jogo foi iniciado
                frame.dispose(); // Fecha a tela de carregamento
                iniciarJogo(); // Inicia o jogo
            }
        });
        timer.start(); // Inicia o timer
    }

    // Método que desenha a imagem de carregamento e a contagem regressiva
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Chama o método da superclasse para limpar a tela

        // Desenha a imagem de carregamento
        if (imagemCarregamento != null) { // Verifica se a imagem foi carregada
            g.drawImage(imagemCarregamento, 0, 0, getWidth(), getHeight(), null); // Desenha a imagem
        }

        // Desenha a contagem regressiva no centro da tela
        g.setColor(Color.white); // Define a cor do texto como branco
        g.setFont(new Font("Arial", Font.BOLD, 30)); // Define a fonte do texto
        g.drawString("Carregando... " + tempoRestante + "s", getWidth() / 2 - 100, getHeight() / 2 + 150); // Desenha o texto da contagem
    }

    // Método para iniciar o jogo após a tela de carregamento
    private void iniciarJogo() {
        Tela tela = new Tela(); // Cria a tela do jogo
        Thread gameThread = new Thread(tela); // Cria uma nova thread para o jogo
        gameThread.start(); // Inicia o loop do jogo

    }

    // Método principal para iniciar a tela de carregamento
    public static void main(String[] args) {
        new TelaCarregamento(); // Inicia a tela de carregamento
    }
}
