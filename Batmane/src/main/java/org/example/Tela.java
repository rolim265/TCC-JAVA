package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Tela extends JPanel implements Runnable {
    private Personagem personagem;
    private Image backgroundImage;
    private Fisica fisica;
    private int fps;
    private final int chaoAltura = 150;
    private ArrayList<Bloco> blocos;

    public Tela() {
        JFrame frame = new JFrame("BATMANE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);

        personagem = new Personagem("src/main/java/img/img_2.png", "src/main/java/img/img_3.png", 0, 100);
        fisica = new Fisica(getHeight() - chaoAltura);

        // Carregar a imagem de fundo de forma mais segura
        backgroundImage = Toolkit.getDefaultToolkit().getImage("src/main/java/img/wallpaper.jpg");
        MediaTracker tracker = new MediaTracker(frame);
        tracker.addImage(backgroundImage, 0);
        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Inicializa os blocos
        blocos = new ArrayList<>();
        blocos.add(new Bloco(500, 900, 150, 20, Color.GREEN, null));
        blocos.add(new Bloco(800, 650, 150, 20, Color.YELLOW, null));
        blocos.add(new Bloco(500, 450, 150, 20, Color.YELLOW, null));
        blocos.add(new Bloco(800, 150, 900, 20, Color.YELLOW, "src/main/java/img/img_9.png")); // Bloco com imagem

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        fisica.moverDireita(true);
                        personagem.mover(5);
                        break;
                    case KeyEvent.VK_LEFT:
                        fisica.moverEsquerda(true);
                        personagem.mover(-5);
                        break;
                    case KeyEvent.VK_SPACE:
                        fisica.pular();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        fisica.moverDireita(false);
                        break;
                    case KeyEvent.VK_LEFT:
                        fisica.moverEsquerda(false);
                        break;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenhar a imagem de fundo se carregada
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }

        // Desenhar os blocos
        for (Bloco bloco : blocos) {
            g.setColor(bloco.getCor());
            g.fillRect(bloco.getX(), bloco.getY(), bloco.getLargura(), bloco.getAltura());
        }

        // Renderizar o personagem
        personagem.render(g);

        // Verificar se o personagem completou o desafio
        Bloco ultimoBloco = blocos.get(blocos.size() - 1);
        if (personagem.getX() >= ultimoBloco.getX() && personagem.getX() <= ultimoBloco.getX() + ultimoBloco.getLargura()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Parabéns! Você completou o desafio!", getWidth() / 2 - 200, getHeight() / 2);
        }

        // Exibir FPS
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("FPS: " + fps, getWidth() - 100, 30);
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (true) {
            fisica.aplicarFisica(personagem);
            repaint();

            frames++;
            if (System.currentTimeMillis() - timer >= 1000) {
                fps = frames;
                frames = 0;
                timer += 1000;
            }

            long now = System.nanoTime();
            long waitTime = (16_000_000 - (now - lastTime)) / 1_000_000; // Espera para 60 FPS
            lastTime = now;

            if (waitTime > 0) {
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
