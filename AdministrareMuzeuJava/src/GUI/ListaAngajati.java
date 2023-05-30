package GUI;

import Database.Database;
import Tables.Angajat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class ListaAngajati {
    private JButton refreshButton;
    private JLabel angajatiLabel;
    private JList listAngajati;
    private JButton stergeButton;
    public JPanel listaAngajati;
    Vector<Angajat> angajati = new Vector<>();
    public ListaAngajati(JPanel panelCont, CardLayout c) throws SQLException {
        createList();

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    listAngajati.clearSelection();
                    createList();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        stergeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedIndices = listAngajati.getSelectedIndices();

                if(selectedIndices.length < 1)
                    JOptionPane.showMessageDialog(null, "Nu ati selectat niciun angajat", "Eroare", JOptionPane.ERROR_MESSAGE);
                else {

                    for(int i=0; i<selectedIndices.length; i++)
                    {
                        try {
                            Database.getInstance().deleteAngajat(angajati.get(selectedIndices[i]));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    JOptionPane.showMessageDialog(null, "Angajatii selectati au fost stersi", "Success", JOptionPane.INFORMATION_MESSAGE);
                    listAngajati.clearSelection();
                    try {
                        createList();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    public void createList() throws SQLException {
        DefaultListModel listModel = new DefaultListModel<>();

        angajati.clear();
        angajati = Database.getInstance().selectAngajat();

        for(int i=0; i<angajati.size(); i++) {
            listModel.addElement(angajati.get(i).getNume() + " " + angajati.get(i).getPrenume());
        }

        listAngajati.setModel(listModel);
    }
}
