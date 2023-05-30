package GUI;

import Database.Database;
import Tables.Angajat;
import Tables.Rol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class AdaugaAngajati {
    private JTextField tfNume;
    private JTextField tfPrenume;
    private JButton adaugaRolButton;
    private JList listaRol;
    private JButton finalizeazaButton;
    public JPanel adaugaAngajati;
    private JLabel numeLabel;
    private JLabel prenumeLabel;
    private JButton anuleazaButton;
    Vector<Rol> roluri = new Vector<>();
    Vector<Integer> idRoluri = new Vector<>();

    AdaugaAngajati(JPanel panelCont, CardLayout c) throws SQLException {

        createList();

        anuleazaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idRoluri.clear();
                tfNume.setText("");
                tfPrenume.setText("");
                listaRol.clearSelection();
            }
        });

        adaugaRolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                idRoluri.clear();
                int[] selectedIndices = listaRol.getSelectedIndices();

                if(selectedIndices.length == 1) {
                    JOptionPane.showMessageDialog(null, "Selectati un rol", "Eroare", JOptionPane.ERROR_MESSAGE);
                    listaRol.clearSelection();
                }
                else

                    for(int i=0; i<selectedIndices.length; i++)
                    {
                        idRoluri.add(roluri.get(selectedIndices[i]).getIdRol());
                    }
            }
        });

        finalizeazaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nume = tfNume.getText();
                String prenume = tfPrenume.getText();

                if(nume == "" || prenume == "")
                    JOptionPane.showMessageDialog(null, "Nu ati selectat toate campurile", "Eroare", JOptionPane.ERROR_MESSAGE);
                else if(idRoluri.size() == 0)
                    JOptionPane.showMessageDialog(null, "Nu ati adaugat niciun rol", "Eroare", JOptionPane.ERROR_MESSAGE);
                else {
                    Angajat angajat = new Angajat(nume, prenume);

                    try {
                        Database.getInstance().insertAngajat(angajat);

                        for (int i = 0; i < idRoluri.size(); i++) {
                            Database.getInstance().insertIntoRolAngajat(angajat.getIdAngajat(), idRoluri.get(i));
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(null, "Angajat adaugat cu succes", "Success", JOptionPane.INFORMATION_MESSAGE);
                    tfNume.setText("");
                    tfPrenume.setText("");
                    listaRol.clearSelection();
                    idRoluri.clear();
                }

            }
        });

    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void createList() throws SQLException {
        DefaultListModel listModel = new DefaultListModel();

        roluri.clear();
        roluri = Database.getInstance().selectRol();

        for(int i=0; i<roluri.size(); i++)
        {
            listModel.addElement(roluri.get(i).getNume());
        }

        listaRol.setModel(listModel);
    }
}
