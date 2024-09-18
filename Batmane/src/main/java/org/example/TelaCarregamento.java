package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TelaCarregamento extends JPanel {
    private Image imagemCarregamento; // Imagem de carregamento
    private int tempoRestante = 1; // 30 segundos para carregar
    private boolean jogoIniciado = false; // Verifica se o jogo já foi iniciado

    // Construtor da tela de carregamento
    public TelaCarregamento() {
        // Carrega a imagem de carregamento
        try {
            imagemCarregamento = ImageIO.read(new File("src/main/java/img/img_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Cria o JFrame para a tela de carregamento
        JFrame frame = new JFrame("Carregando...");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.add(this); // Adiciona o painel de carregamento à janela
        frame.setVisible(true);

        // Timer para a contagem regressiva de segundos
        Timer timer = new Timer(1000, e -> {
            tempoRestante--;
            repaint(); // Atualiza a tela a cada segundo

            if (tempoRestante <= 0 && !jogoIniciado) {
                // Fecha a tela de carregamento e inicia o jogo apenas uma vez
                jogoIniciado = true; // Marca que o jogo foi iniciado
                frame.dispose();
                iniciarJogo();
            }
        });
        timer.start();
    }

    // Método que desenha a imagem de carregamento e a contagem regressiva
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenha a imagem de carregamento
        if (imagemCarregamento != null) {
            g.drawImage(imagemCarregamento, 0, 0, getWidth(), getHeight(), null);
        }

        // Desenha a contagem regressiva no centro da tela
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Carregando... " + tempoRestante + "s", getWidth() / 2 - 100, getHeight() / 2 + 150);
    }

    // Método para iniciar o jogo após a tela de carregamento
    private void iniciarJogo() {
        Tela tela = new Tela(); // Cria a tela do jogo
        Thread gameThread = new Thread(tela);
        gameThread.start(); // Inicia o loop do jogo
    }

    // Método principal para iniciar a tela de carregamento
    public static void main(String[] args) {
        new TelaCarregamento(); // Inicia a tela de carregamento
    }
}
