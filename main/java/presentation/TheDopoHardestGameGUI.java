package presentation;

import javax.swing.*;
import java.awt.*;

public class TheDopoHardestGameGUI{
    public static void main(String[] args) {
        JFrame frame = new JFrame("The Dopo Hardest Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        CardLayout cardLayout = new CardLayout();
        JPanel contenedor = new JPanel(cardLayout);


        GamePanel game = new GamePanel(cardLayout,contenedor);
        MenuPanel menu = new MenuPanel(cardLayout,contenedor,game);

        contenedor.add(menu,"MENU");
        contenedor.add(game,"GAME");

        frame.add(contenedor);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

    }
}
