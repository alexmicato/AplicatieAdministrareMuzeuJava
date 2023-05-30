package GUI;

import Database.Database;
import Tables.Artefact;
import Tables.Colectie;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class AdaugaArtefact {
    private JTextField textField1;
    private JTextField textField3;
    private JTextField textField4;
    private JList listColectii;
    private JButton adaugaColectieButton;
    private JButton finalizeazaButton;
    private JButton anuleazaButton;
    public JPanel adaugaArtefact;

    Vector<Colectie> colectii = new Vector<>();

    int idColectie = -1;

    AdaugaArtefact() throws SQLException {
        createList();
         anuleazaButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 textField1.setText("");
                 textField3.setText("");
                 textField4.setText("");
                 listColectii.clearSelection();
             }
         });

         adaugaColectieButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 int[] selectedIndices = listColectii.getSelectedIndices();

                 if(selectedIndices.length == 1)
                 {
                     idColectie = colectii.get(selectedIndices[0]).getIdColectie();
                 }
                 else {
                     JOptionPane.showMessageDialog(null, "Selectati o colectie", "Eroare", JOptionPane.ERROR_MESSAGE);
                     listColectii.clearSelection();
                 }
             }
         });

         finalizeazaButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 String nume = textField1.getText();
                 String descriere = textField3.getText();
                 String conditie = textField4.getText();

                 if(nume == "" || descriere == "" || conditie == "")
                     JOptionPane.showMessageDialog(null, "Nu ati completat campurile", "Eroare", JOptionPane.ERROR_MESSAGE);
                 else if(idColectie == -1)
                     JOptionPane.showMessageDialog(null, "Nu ati selectat nicio colectie", "Eroare", JOptionPane.ERROR_MESSAGE);
                 else {
                     Artefact artefact = new Artefact(idColectie, nume, descriere, conditie);

                     try {
                         Database.getInstance().insertArtefact(artefact);

                     } catch (SQLException ex) {
                         throw new RuntimeException(ex);
                     }
                     JOptionPane.showMessageDialog(null, "Artefact adaugat cu succes", "Success", JOptionPane.INFORMATION_MESSAGE);
                     textField1.setText("");
                     textField3.setText("");
                     textField4.setText("");
                     listColectii.clearSelection();
                     idColectie = -1;

                 }
             }
         });

    }

    public void createList() throws SQLException {
        DefaultListModel listModel = new DefaultListModel();

        colectii.clear();
        colectii = Database.getInstance().selectColectie();

        for(int i=0; i<colectii.size(); i++)
        {
            listModel.addElement(colectii.get(i).getNume());
        }

        listColectii.setModel(listModel);
    }
}
