package GUI;

import Database.Database;
import Tables.Artefact;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class ListaArtefacte {
    public JPanel listaArtefacte;
    private JLabel artefacteLabel;
    private JButton refreshButton;
    private JButton stergeButton;
    private JList listArtefacte;
    private JButton veziEditeazaButton;

    Vector<Artefact> artefacte = new Vector<>();

    ListaArtefacte() throws SQLException {
        createList();

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createList();
                    listArtefacte.clearSelection();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        stergeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedIndices = listArtefacte.getSelectedIndices();
                if(selectedIndices.length < 1)
                    JOptionPane.showMessageDialog(null, "Nu ati selectat niciun artefact", "Eroare", JOptionPane.ERROR_MESSAGE);
                else {

                    for (int i = 0; i <selectedIndices.length; i++)
                    {
                        try {
                            Database.getInstance().deleteArtefact(artefacte.get(selectedIndices[i]));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Artefactele selectate au fost sterse", "Success", JOptionPane.INFORMATION_MESSAGE);
                    listArtefacte.clearSelection();
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

        artefacte.clear();

        artefacte = Database.getInstance().selectArtefact();

        for(int i=0; i<artefacte.size(); i++)
        {
            listModel.addElement(artefacte.get(i).getNume());
        }

        listArtefacte.setModel(listModel);
    }
}
