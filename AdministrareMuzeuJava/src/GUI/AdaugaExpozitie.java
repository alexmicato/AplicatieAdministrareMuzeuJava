package GUI;

import Database.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class AdaugaExpozitie extends JPanel {
    private JTextField tfName;
    private JTextField tfDateFirst;
    private JTextField tfDateSec;
    private JButton adaugaSaliButton;
    private JButton adaugaOrganizatoriButton;
    private JButton finalizeazaButton;
    public JPanel panel;
    private JLabel dateSec;
    private JLabel dateFirst;
    private JLabel numeExpozitieLabel;
    private JButton adaugaArtefacteButton;
    public AdaugaExpozitie(JPanel panelCont, CardLayout c, AdaugaSali adaugaSali, AdaugaOrganizator adaugaOrganizator,
                           AdaugaArtefacte adaugaArtefacte) throws SQLException {

        adaugaSaliButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.show(panelCont, "AdaugaSali");
            }
        });

        adaugaArtefacteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.show(panelCont, "AdaugaArtefacte");
            }
        });

        adaugaOrganizatoriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.show(panelCont, "AdaugaOrganizator");
            }
        });

        finalizeazaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expoName = tfName.getText();
                String dateFirst = tfDateFirst.getText();
                String dateSecond = tfDateSec.getText();
                Vector<Integer> idSali = adaugaSali.getSali();
                Vector<Integer> idArtefacte = adaugaArtefacte.getIdArtefacte();
                Vector<Integer> idOrganizatori = adaugaOrganizator.getOrganizatori();
                if(expoName.isEmpty() || dateFirst.isEmpty() || dateSecond.isEmpty())
                    JOptionPane.showMessageDialog(null, "Nu ati completat toate campurile", "Eroare", JOptionPane.ERROR_MESSAGE);
                else if(idSali.isEmpty())
                    JOptionPane.showMessageDialog(null, "Nu ati selectat sali", "Eroare", JOptionPane.ERROR_MESSAGE);
                else if(idArtefacte.isEmpty())
                    JOptionPane.showMessageDialog(null, "Nu ati selectat artefacte", "Eroare", JOptionPane.ERROR_MESSAGE);
                else if(idOrganizatori.isEmpty())
                    JOptionPane.showMessageDialog(null, "Nu ati selectat organizatori", "Eroare", JOptionPane.ERROR_MESSAGE);
                else {

                    int idExpozitie = -1;
                    PreparedStatement pst = null;
                    try {
                        pst = Database.getInstance().connection.prepareStatement("insert into " +
                                "expozitie(nume, data_inceput, data_final) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                        pst.setString(1, expoName);
                        pst.setString(2, dateFirst);
                        pst.setString(3, dateSecond);

                        pst.executeUpdate();

                        ResultSet rs = pst.getGeneratedKeys();
                        if(rs.next())
                            idExpozitie = rs.getInt(1);

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    System.out.println(idExpozitie);

                    try {
                        pst = Database.getInstance().connection.prepareStatement("insert into sala_expozitie(id_sala, id_expozitie) " +
                                "values (?, ?) ");

                        for(int i=0; i<idSali.size(); i++)
                        {
                            pst.setInt(1, idSali.get(i));
                            pst.setInt(2, idExpozitie);

                            pst.executeUpdate();
                            pst.clearParameters();

                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    try {
                        pst = Database.getInstance().connection.prepareStatement("insert into artefact_expus(id_artefact, id_expozitie) " +
                                "values (?, ?) ");

                        for(int i=0; i<idArtefacte.size(); i++)
                        {
                            pst.setInt(1, idArtefacte.get(i));
                            pst.setInt(2, idExpozitie);

                            pst.executeUpdate();

                            pst.clearParameters();

                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    try {
                        pst = Database.getInstance().connection.prepareStatement("insert into angajat_expozitie(id_angajat, id_expozitie) " +
                                "values (?, ?) ");

                        for(int i=0; i<idOrganizatori.size(); i++)
                        {
                            pst.setInt(1, idOrganizatori.get(i));
                            pst.setInt(2, idExpozitie);

                            pst.executeUpdate();

                            pst.clearParameters();

                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    JOptionPane.showMessageDialog(null, "Expozitie adaugata cu succes", "Success", JOptionPane.INFORMATION_MESSAGE);
                    tfName.setText("");
                    tfDateFirst.setText("");
                    tfDateSec.setText("");

                    try {
                        adaugaSali.updateModel();
                        adaugaArtefacte.updateModel();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });
    }

}
