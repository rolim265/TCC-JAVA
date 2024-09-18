package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tela extends JPanel implements Runnable {
    private Personagem personagem;
    private Image backgroundImage;
    private Fisica fisica;
    private int fps;
    private final int chaoAltura = 50;

    public Tela() {
        // Configuração do JFrame
        JFrame frame = new JFrame("BATMANE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);

        // Inicializa o personagem com o caminho relativo
        personagem = new Personagem("C:\\Users\\Rolim\\Documents\\Nova pasta\\java\\Batmane\\src\\main\\java\\img\\img_2.png", 10, 200 + chaoAltura);

        // Inicializa a física
        fisica = new Fisica();

        // Carrega a imagem de fundo usando o caminho relativo
        try {
            backgroundImage = ImageIO.read(new File("C:/caminho/para/imagem/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Adiciona controles de teclado
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT: // Movimento para a direita
                        fisica.moverDireita(personagem);
                        break;
                    case KeyEvent.VK_LEFT: // Movimento para a esquerda
                        fisica.moverEsquerda(personagem);
                        break;
                    case KeyEvent.VK_SPACE: // Pular
                        fisica.pular();
                        break;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Desenha o background
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }

        // Desenha o chão
        g.setColor(Color.BLACK);
        g.fillRect(0, getHeight() - chaoAltura, getWidth(), chaoAltura);

        // Desenha o personagem
        personagem.render(g);

        // Desenha o FPS no canto superior direito
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("FPS: " + fps, getWidth() - 100, 30);
    }

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (true) {
            // Aplica a física ao personagem (gravidade e colisão com o chão)
            fisica.aplicarFisica(personagem, getHeight() - chaoAltura);

            // Atualiza a tela
            repaint();

            // Controle de tempo para manter 60 FPS
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                fps = frames;
                frames = 0;
                timer += 1000;
            }

            long now = System.currentTimeMillis();
            long waitTime = 16 - (now - lastTime); // Aproximadamente 60 FPS
            lastTime = now;

            try {
                if (waitTime > 0) {
                    Thread.sleep(waitTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
