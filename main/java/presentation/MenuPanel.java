package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private CardLayout layout;
    private JPanel contenedor;
    private JButton btnPlay;
    private JButton btnLeader;
    private GamePanel gamePanel;
    public MenuPanel(CardLayout layout, JPanel contenedor,GamePanel gamePanel){
        this.layout = layout;
        this.contenedor = contenedor;
        this.gamePanel = gamePanel;
        prepareElements();
        prepareActions();

    }
    private void prepareElements(){
        setPreferredSize(new Dimension(800, 500));
        setLayout(new BorderLayout());

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        JLabel label1 = new JLabel("THE DOPO'S..");
        label1.setForeground(Color.black);
        label1.setFont(new Font("Monospaced", Font.BOLD, 28));

        JLabel label2 = new JLabel("HARDEST GAME");
        label2.setForeground(Color.blue);
        label2.setFont(new Font("Monospaced", Font.BOLD, 72));

        JPanel buttons = new JPanel(new GridLayout(1, 2, 40, 0));
        buttons.setOpaque(false);

        btnPlay = new JButton("PLAY GAME");
        btnPlay.setForeground(Color.red);
        btnPlay.setFont(new Font("Monospaced", Font.BOLD, 36));
        btnPlay.setContentAreaFilled(false);
        btnPlay.setBorderPainted(false);
        btnPlay.setFocusPainted(false);

        btnLeader = new JButton("LEADER BOARD");
        btnLeader.setForeground(Color.blue);
        btnLeader.setFont(new Font("Monospaced", Font.BOLD, 36));
        btnLeader.setContentAreaFilled(false);
        btnLeader.setBorderPainted(false);
        btnLeader.setFocusPainted(false);

        buttons.add(btnPlay);
        buttons.add(btnLeader);

        centro.add(label1);
        centro.add(label2);
        centro.add(Box.createVerticalStrut(80));
        centro.add(buttons);
        label1.setAlignmentX(Component.LEFT_ALIGNMENT);
        label2.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(centro, BorderLayout.NORTH);
    }
    private void prepareActions(){
        btnPlay.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        layout.show(contenedor,"GAME");
                        gamePanel.iniciar();

                    }
                }
        );

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Color arribaAzul = new Color(100, 149, 237);
        Color abajoBlanco = new Color(255, 255, 255);

        GradientPaint gradiente = new GradientPaint(
                0, 0, arribaAzul,
                0, getHeight(), abajoBlanco
        );

        g2d.setPaint(gradiente);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
