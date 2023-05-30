package GUI;

import Database.Database;
import Tables.Colectie;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class ListaColectii {
    private JLabel labelColectii;
    private JButton refreshButton;
    private JList listColectii;
    private JButton stergeButton;
    public JPanel listaColectii;

    Vector<Colectie> colectii = new Vector<>();

    ListaColectii() throws SQLException {
        createList();

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createList();
                    listColectii.clearSelection();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        stergeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedIndices = listColectii.getSelectedIndices();
                if (selectedIndices.length < 1)
                    JOptionPane.showMessageDialog(null, "Nu ati selectat nicio colectie", "Eroare", JOptionPane.ERROR_MESSAGE);
                else {

                    for (int i = 0; i < selectedIndices.length; i++) {
                        try {
                            Database.getInstance().deleteColectie(colectii.get(selectedIndices[i]));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Colectiile selectate au fost sterse", "Success", JOptionPane.INFORMATION_MESSAGE);
                    listColectii.clearSelection();
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

        colectii.clear();

        colectii = Database.getInstance().selectColectie();

        for(int i=0; i<colectii.size(); i++)
        {
            listModel.addElement(colectii.get(i).getNume());
        }

        listColectii.setModel(listModel);
    }
}



