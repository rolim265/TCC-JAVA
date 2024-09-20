package org.example; // Declara o pacote da classe

import javax.swing.*; // Importa classes para criar interfaces gráficas
import java.awt.*; // Importa classes para manipulação de gráficos
import java.awt.event.KeyAdapter; // Importa a classe para ouvir eventos de teclado
import java.awt.event.KeyEvent; // Importa a classe que representa eventos de teclado
import java.util.ArrayList; // Importa a classe para listas dinâmicas

// Classe principal da tela que implementa Runnable para executar em um thread
public class Tela extends JPanel implements Runnable {
    private Personagem personagem; // Instância do personagem
    private Image backgroundImage; // Imagem de fundo da tela
    private Fisica fisica; // Instância da classe que gerencia a física do jogo
    private int fps; // Variável para armazenar frames por segundo
    private final int chaoAltura = 150; // Altura do chão fixada
    private ArrayList<Bloco> blocos; // Lista de blocos que o personagem interage

    // Construtor da classe Tela
    public Tela() {
        // Cria uma nova janela para o jogo
        JFrame frame = new JFrame("BATMANE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha o aplicativo ao fechar a janela
        frame.setSize(new Dimension(800, 600)); // Define o tamanho da janela
        frame.setLocationRelativeTo(null); // Centraliza a janela na tela
        frame.add(this); // Adiciona a instância da tela ao frame
        frame.setVisible(true); // Torna a janela visível

        // Inicializa o personagem com suas imagens e posição
        personagem = new Personagem("src/main/java/img/img_2.png", "src/main/java/img/img_3.png", 0, 100);
        // Inicializa a física com a altura do chão
        fisica = new Fisica(getHeight() - chaoAltura);

        // Carrega a imagem de fundo de forma segura
        backgroundImage = Toolkit.getDefaultToolkit().getImage("src/main/java/img/wallpaper.jpg");
        MediaTracker tracker = new MediaTracker(frame); // Usado para rastrear o carregamento da imagem
        tracker.addImage(backgroundImage, 0); // Adiciona a imagem ao tracker
        try {
            tracker.waitForAll(); // Espera até que todas as imagens estejam carregadas
        } catch (InterruptedException e) {
            e.printStackTrace(); // Trata exceções caso o carregamento seja interrompido
        }

        // Inicializa a lista de blocos
        blocos = new ArrayList<>(); // Cria uma nova lista de blocos
        // Adiciona blocos à lista com suas propriedades
        blocos.add(new Bloco(500, 900, 150, 20, Color.GREEN, null));
        blocos.add(new Bloco(800, 650, 150, 20, Color.YELLOW, null));
        blocos.add(new Bloco(500, 450, 150, 20, Color.YELLOW, null));
        blocos.add(new Bloco(800, 150, 900, 20, Color.YELLOW, "src/main/java/img/img_9.png")); // Bloco com imagem

        // Adiciona um listener para eventos de teclado
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) { // Método chamado quando uma tecla é pressionada
                switch (e.getKeyCode()) { // Verifica qual tecla foi pressionada
                    case KeyEvent.VK_RIGHT: // Tecla da direita
                        fisica.moverDireita(true); // Ativa movimento à direita
                        personagem.mover(5); // Move o personagem para a direita
                        break;
                    case KeyEvent.VK_LEFT: // Tecla da esquerda
                        fisica.moverEsquerda(true); // Ativa movimento à esquerda
                        personagem.mover(-5); // Move o personagem para a esquerda
                        break;
                    case KeyEvent.VK_SPACE: // Tecla de espaço
                        fisica.pular(); // Faz o personagem pular
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) { // Método chamado quando uma tecla é liberada
                switch (e.getKeyCode()) { // Verifica qual tecla foi liberada
                    case KeyEvent.VK_RIGHT: // Tecla da direita
                        fisica.moverDireita(false); // Desativa movimento à direita
                        break;
                    case KeyEvent.VK_LEFT: // Tecla da esquerda
                        fisica.moverEsquerda(false); // Desativa movimento à esquerda
                        break;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) { // Método chamado para desenhar na tela
        super.paintComponent(g); // Chama o método da superclasse para limpar a tela

        // Desenhar a imagem de fundo se carregada
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null); // Desenha a imagem de fundo
        }

        // Desenhar os blocos
        for (Bloco bloco : blocos) { // Para cada bloco na lista
            g.setColor(bloco.getCor()); // Define a cor do bloco
            g.fillRect(bloco.getX(), bloco.getY(), bloco.getLargura(), bloco.getAltura()); // Desenha o bloco
        }

        // Renderiza o personagem
        personagem.render(g); // Chama o método de renderização do personagem

        // Verifica se o personagem completou o desafio
        Bloco ultimoBloco = blocos.get(blocos.size() - 1); // Pega o último bloco
        if (personagem.getX() >= ultimoBloco.getX() && personagem.getX() <= ultimoBloco.getX() + ultimoBloco.getLargura()) {
            g.setColor(Color.RED); // Define a cor do texto
            g.setFont(new Font("Arial", Font.BOLD, 30)); // Define a fonte do texto
            g.drawString("Parabéns! Você completou o desafio!", getWidth() / 2 - 200, getHeight() / 2); // Desenha a mensagem de sucesso
        }

        // Exibir FPS
        g.setColor(Color.BLACK); // Define a cor do texto de FPS
        g.setFont(new Font("Arial", Font.BOLD, 20)); // Define a fonte do texto de FPS
        g.drawString("FPS: " + fps, getWidth() - 100, 30); // Desenha o FPS na tela
    }

    @Override
    public void run() { // Método executado quando a thread é iniciada
        long lastTime = System.nanoTime(); // Armazena o tempo da última execução
        int frames = 0; // Contador de frames
        long timer = System.currentTimeMillis(); // Armazena o tempo atual

        while (true) { // Loop infinito para o jogo
            fisica.aplicarFisica(personagem); // Aplica a física ao personagem
            repaint(); // Atualiza a tela

            frames++; // Incrementa o contador de frames
            if (System.currentTimeMillis() - timer >= 1000) { // Se passou um segundo
                fps = frames; // Atualiza o FPS
                frames = 0; // Reseta o contador de frames
                timer += 1000; // Atualiza o timer
            }

            long now = System.nanoTime(); // Armazena o tempo atual
            long waitTime = (16_000_000 - (now - lastTime)) / 1_000_000; // Calcula o tempo de espera para manter 60 FPS
            lastTime = now; // Atualiza o tempo da última execução

            if (waitTime > 0) { // Se houver tempo para esperar
                try {
                    Thread.sleep(waitTime); // Espera o tempo calculado
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Trata exceções
                }
            }
        }
    }
}
