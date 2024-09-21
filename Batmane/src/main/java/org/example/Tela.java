package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Tela extends JPanel implements Runnable {
    public Personagem personagem;
    private Image backgroundImage;
    private Fisica fisica;
    private int fps;
    private final int chaoAltura = 150;
    private ArrayList<Bola> bolas;
    private int projetosAcertados = 0;

    public Tela() {
        JFrame frame = new JFrame("BATMANE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.add(this);
//        JLabel pontos = new JLabel("0");
//        pontos.setBounds(0, 0, 100, 100);
//        pontos.setForeground(new Color(0xFFFFFF));
//        frame.add(pontos);
        frame.setVisible(true);

        // Inicialização de componentes
        personagem = new Personagem("src/main/java/img/img_2.png", "src/main/java/img/img_3.png", 0, 400);
        fisica = new Fisica(getHeight() - chaoAltura);
        bolas = new ArrayList<>();
        backgroundImage = Toolkit.getDefaultToolkit().getImage("src/main/java/img/img_1.png");
        carregarImagens(frame);

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
                    case KeyEvent.VK_UP:
                        personagem.atirar();
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

        Timer timer = new Timer(1000, e -> {
            int x = new Random().nextInt(getWidth() - 50);
            bolas.add(new Bola(x, 0));
        });
        timer.start();
    }

    private void carregarImagens(JFrame frame) {
        MediaTracker tracker = new MediaTracker(frame);
        tracker.addImage(backgroundImage, 0);
        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
        personagem.render(g);
        personagem.renderProjetos(g);

        for (Bola bola : bolas) {
            bola.render(g);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("FPS: " + fps, getWidth() - 100, 30);
        g.drawString("Acertos: " + projetosAcertados, getWidth() - 100, 60);
    }

    private void verificarColisoes() {
        for (int i = bolas.size() - 1; i >= 0; i--) {
            Bola bola = bolas.get(i);

            // Verificação se a bola colidiu com o personagem
            if (bola.getY() + bola.getAltura() >= personagem.getY() &&
                    bola.getX() < personagem.getX() + personagem.getLargura() &&
                    bola.getX() + bola.getLargura() > personagem.getX()) {
                System.out.println("Uma bola atingiu o personagem.");
                continue; // Aqui você pode implementar alguma lógica de penalidade
            }

            for (int j = personagem.projetos.size() - 1; j >= 0; j--) {
                Projeto projeto = personagem.projetos.get(j);
                if (bola.getY() <= projeto.getY() + projeto.getAltura() &&
                        bola.getY() + bola.getAltura() >= projeto.getY() &&
                        bola.getX() < projeto.getX() + projeto.getLargura() &&
                        bola.getX() + bola.getLargura() > projeto.getX()) {

                    // Remove a bola e o projeto
                    bolas.remove(i);
                    personagem.projetos.remove(j);
                    projetosAcertados++;

                    System.out.println("Bola atingida! Projetos Acertados: " + projetosAcertados);
                    break; // Sai do loop após remover
                }
            }
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (true) {
            fisica.aplicarFisica(personagem);
            personagem.atualizarProjetos();
            verificarColisoes();

            for (int i = bolas.size() - 1; i >= 0; i--) {
                Bola bola = bolas.get(i);
                bola.mover();
                if (bola.isForaDaTela(getHeight())) {
                    bolas.remove(i);
                }
            }

            repaint();

            frames++;
            if (System.currentTimeMillis() - timer >= 1000) {
                fps = frames;
                frames = 0;
                timer += 1000;
            }

            long now = System.nanoTime();
            long waitTime = (16_000_000 - (now - lastTime)) / 1_000_000;
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

    public static void main(String[] args) {
        Tela tela = new Tela();
        new Thread(tela).start();
    }
}
