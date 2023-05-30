package GUI;

import javax.swing.*;

public class Acasa {
    public JPanel acasa;

    public static void main(String[] args) {
        JFrame frame = new JFrame("AcasaGen");
        frame.setContentPane(new Acasa().acasa);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
