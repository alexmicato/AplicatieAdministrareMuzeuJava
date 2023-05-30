package GUI;

import Database.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ListaExpozitii {
    public JPanel panel;
    private JList listaExpozitii;
    private JButton veziExpozitieButton;
    private JLabel title;
    private JButton stergeExpozitieButton;
    private JButton refreshButton;
    VeziExpozitie veziExpozitie;

    Vector<Integer> idExpozitii = new Vector<>();
    Vector<String> expozitiiSelectate = new Vector<>();
    ListaExpozitii(JPanel panelCont, CardLayout c, AdaugaSali adaugaSali, AdaugaArtefacte adaugaArtefacte,
                   AdaugaOrganizator adaugaOrganizator) throws SQLException {
        initList();

        veziExpozitie = new VeziExpozitie(panelCont, c, adaugaSali, adaugaArtefacte, adaugaOrganizator);
        panelCont.add(veziExpozitie.veziExpo, "veziExpo");

        veziExpozitieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedIndices = listaExpozitii.getSelectedIndices();

                if(selectedIndices.length < 1)
                    JOptionPane.showMessageDialog(null, "Nu ati selectat nicio expozitie", "Eroare", JOptionPane.ERROR_MESSAGE);
                else if(selectedIndices.length > 1)
                    JOptionPane.showMessageDialog(null, "Selectati doar o expozitie pentru a vizualiza", "Eroare", JOptionPane.ERROR_MESSAGE);
                else {

                    String nume = listaExpozitii.getModel().getElementAt(selectedIndices[0]).toString();

                    try {
                        PreparedStatement pst = Database.getInstance().connection.prepareStatement("select " +
                                "id_expozitie from expozitie where nume = ?");

                        pst.setString(1, nume);

                        ResultSet rs = pst.executeQuery();

                        int idExpozitie = -1;

                        if(rs.next())
                        {
                            idExpozitie = rs.getInt("id_expozitie");
                        }

                        veziExpozitie.SetIdExpozitie(idExpozitie);
                        veziExpozitie.updateLabels();
                        veziExpozitie.updateListaSali();
                        veziExpozitie.updateListaArtefacte();
                        veziExpozitie.updateListaOrganizatori();

                        c.show(panelCont, "veziExpo");



                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }


                }

            }
        });

        stergeExpozitieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                expozitiiSelectate.clear();
                idExpozitii.clear();

                int[] selectedIndices = listaExpozitii.getSelectedIndices();

                if(selectedIndices.length < 1)
                    JOptionPane.showMessageDialog(null, "Nu ati selectat nicio expozitie", "Eroare", JOptionPane.ERROR_MESSAGE);
                else {

                    for(int i=0; i<selectedIndices.length; i++)
                    {
                        String sel = listaExpozitii.getModel().getElementAt(selectedIndices[i]).toString();
                        expozitiiSelectate.add(sel);

                    }

                    PreparedStatement pst = null;
                    try {
                        pst = Database.getInstance().connection.prepareStatement("select id_expozitie " +
                                "from expozitie where nume = ?");
                        for(int i=0; i<expozitiiSelectate.size(); i++)
                        {
                            pst.setString(1, expozitiiSelectate.get(i));
                            ResultSet rs = pst.executeQuery();

                            while(rs.next())
                            {
                                //System.out.println(rs.getInt("id_sala"));
                                idExpozitii.add(rs.getInt("id_expozitie"));
                            }

                            pst.clearParameters();
                        }

                        pst = Database.getInstance().connection.prepareStatement("delete from expozitie where " +
                                "id_expozitie = ?");

                        for(int i=0; i<idExpozitii.size(); i++)
                        {
                            pst.setInt(1, idExpozitii.get(i));
                            pst.executeUpdate();
                            pst.clearParameters();
                        }


                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(null, "Expozitiile selectate au fost sterse", "Success", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        initList();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    initList();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void initList() throws SQLException {
        PreparedStatement pst = Database.getInstance().connection.prepareStatement("select nume " +
                "from expozitie");

        ResultSet expo = pst.executeQuery();
        DefaultListModel listModel = new DefaultListModel();

        while(expo.next())
        {
            String nume = expo.getString("nume");
            listModel.addElement(nume);
        }

        listaExpozitii.setModel(listModel);

    }
}
