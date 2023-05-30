package GUI;

import Database.Database;
import Tables.Colectie;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdaugaColectie {
    private JTextField textField1;
    private JTextField textField2;
    private JButton finalizeazaButton;
    private JButton anuleazaButton;
    public JLabel numeLabel;
    public JPanel adColectie;

    AdaugaColectie(){

        anuleazaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                textField2.setText("");
            }
        });

        finalizeazaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nume = textField1.getText();
                String descriere = textField2.getText();
                if(nume == "" || descriere == "")
                    JOptionPane.showMessageDialog(null, "Nu ati completat toate campurile", "Eroare", JOptionPane.ERROR_MESSAGE);
                else {

                    Colectie colectie = new Colectie(nume, descriere);

                    try {
                        Database.getInstance().insertColectie(colectie);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    JOptionPane.showMessageDialog(null, "Colectie adaugata cu succes", "Success", JOptionPane.INFORMATION_MESSAGE);
                    textField1.setText("");
                    textField2.setText("");
                }
            }
        });
    }
}
