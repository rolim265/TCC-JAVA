package org.example; // Define o pacote do código

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tela extends JPanel implements Runnable { // Define a classe Tela que estende JPanel e implementa Runnable
    private Personagem personagem; // Declara um objeto Personagem
    private Image backgroundImage; // Declara um objeto Image para a imagem de fundo
    private Fisica fisica; // Declara um objeto Fisica para lógica de física do jogo
    private int fps; // Declara uma variável para armazenar a contagem de FPS
    private final int chaoAltura = 150; // Declara a altura do chão, constante

    public Tela() { // Construtor da classe Tela
        // Configuração do JFrame
        JFrame frame = new JFrame("BATMANE"); // Cria um novo JFrame com o título "BATMANE"
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define a operação padrão para fechar o JFrame
        frame.setSize(new Dimension(800, 600)); // Define o tamanho do JFrame
        frame.setLocationRelativeTo(null); // Centraliza o JFrame na tela
        frame.add(this); // Adiciona o painel atual (Tela) ao JFrame
        frame.setVisible(true); // Torna o JFrame visível

        // Inicializa o personagem com o caminho relativo
        personagem = new Personagem("src/main/java/img/img_2.png", 0, 100); // Cria um novo Personagem com uma imagem e posição inicial

        // Inicializa a física com a altura do chão
        fisica = new Fisica(getHeight() - chaoAltura); // Cria um novo objeto Fisica usando a altura do painel menos a altura do chão

        // Carrega a imagem de fundo usando o caminho relativo
        try {
            backgroundImage = ImageIO.read(new File("src/main/java/img/wallpaper.jpg")); // Tenta ler a imagem do fundo
        } catch (IOException e) {
            e.printStackTrace(); // Imprime a pilha de erros se a leitura da imagem falhar
        }

        // Adiciona controles de teclado
        frame.addKeyListener(new KeyAdapter() { // Adiciona um KeyAdapter para escutar eventos de teclado
            @Override
            public void keyPressed(KeyEvent e) { // Sobrescreve o método keyPressed para tratar eventos de tecla pressionada
                switch (e.getKeyCode()) { // Verifica o código da tecla pressionada
                    case KeyEvent.VK_RIGHT: // Se a tecla for a seta para a direita
                        fisica.moverDireita(personagem); // Move o personagem para a direita
                        break;
                    case KeyEvent.VK_LEFT: // Se a tecla for a seta para a esquerda
                        fisica.moverEsquerda(personagem); // Move o personagem para a esquerda
                        break;
                    case KeyEvent.VK_SPACE: // Se a tecla for a barra de espaço
                        fisica.pular(); // Faz o personagem pular
                        break;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) { // Sobrescreve o método paintComponent para desenhar no painel
        super.paintComponent(g); // Chama o método da classe pai

        // Desenha o background
        if (backgroundImage != null) { // Verifica se a imagem de fundo foi carregada
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null); // Desenha a imagem de fundo ocupando todo o painel
        }

        // Desenha o chão
       // g.setColor(Color.GREEN); // Define a cor para o chão
        //g.fillRect(0, getHeight() - chaoAltura, getWidth(), chaoAltura); // Desenha um retângulo preto para representar o chão

        // Desenha o personagem
        personagem.render(g); // Chama o método render do objeto Personagem para desenhar o personagem

        // Desenha o FPS no canto superior direito
        g.setColor(Color.BLACK); // Define a cor preta para o texto do FPS
        g.setFont(new Font("Arial", Font.BOLD, 20)); // Define a fonte para o texto do FPS
        g.drawString("FPS: " + fps, getWidth() - 100, 30); // Desenha a contagem de FPS no canto superior direito
    }

    @Override
    public void run() { // Implementa o método run da interface Runnable
        long lastTime = System.currentTimeMillis(); // Armazena o tempo atual em milissegundos
        int frames = 0; // Inicializa o contador de frames
        long timer = System.currentTimeMillis(); // Armazena o tempo atual para controle de FPS

        while (true) { // Loop principal do jogo
            // Aplica a física ao personagem (gravidade e colisão com o chão)
            fisica.aplicarFisica(personagem); // Aplica a lógica de física ao personagem

            // Atualiza a tela
            repaint(); // Solicita a atualização do painel

            // Controle de tempo para manter 60 FPS
            frames++; // Incrementa o contador de frames
            if (System.currentTimeMillis() - timer > 1000) { // Se passou um segundo
                fps = frames; // Atualiza a contagem de FPS
                frames = 0; // Reseta o contador de frames
                timer += 1000; // Atualiza o timer para o próximo segundo
            }

            long now = System.currentTimeMillis(); // Armazena o tempo atual
            long waitTime = 16 - (now - lastTime); // Calcula o tempo de espera para manter 60 FPS
            lastTime = now; // Atualiza o último tempo registrado

            try {
                if (waitTime > 0) { // Se o tempo de espera for positivo
                    Thread.sleep(waitTime); // Aguarda o tempo de espera
                }
            } catch (InterruptedException e) {
                e.printStackTrace(); // Imprime a pilha de erros se a espera for interrompida
            }
        }
    }
}
