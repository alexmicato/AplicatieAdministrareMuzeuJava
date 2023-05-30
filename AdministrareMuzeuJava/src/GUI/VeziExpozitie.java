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

public class VeziExpozitie {
    private JLabel numeExpo;
    private JLabel dateFirstLabel;
    private JLabel dateSecLabel;
    private JLabel saliLabel;
    private JList listSali;
    private JScrollPane saliPane;
    private JLabel artefacteLabel;
    private JList listArtefacte;
    private JScrollPane artefactePane;
    private JLabel organizatoriLabel;
    public JPanel veziExpo;
    private JList listaOrg;
    private JButton salveazaButton;
    private JButton refreshButton;
    private JButton inapoiButton;
    private JButton adaugaSaliButton;
    private JButton adaugaArtefacteButton;
    private JButton adaugaOrgButton;
    private JButton stergeSaliButton;
    private JButton stergeArtefacteButton;
    private JButton stergeOrgButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton anuleazaButton;
    int idExpozitie = -1;
    Vector<String> saliSelectate = new Vector<>();
    Vector<Integer> idSaliToDelete = new Vector<>();
    Vector<String> artefacteSelectate = new Vector<>();
    Vector<Integer> idArtefacteToDelete = new Vector<>();
    Vector<String> orgSelectat = new Vector<>();
    Vector<Integer> idOrgToDelete = new Vector<>();
    Vector<Integer> idSaliToAdd = new Vector<>();
    Vector<Integer> idArtefacteToAdd = new Vector<>();
    Vector<Integer> idOrgToAdd = new Vector<>();

    VeziExpozitie(JPanel panelCont, CardLayout c, AdaugaSali adaugaSali, AdaugaArtefacte adaugaArtefacte,
                  AdaugaOrganizator adaugaOrganizator) throws SQLException {

        inapoiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.show(panelCont, "listaExpo");
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateLabels();
                    updateListaSali();
                    updateListaArtefacte();
                    updateListaOrganizatori();
                    idSaliToDelete.clear();
                    idArtefacteToDelete.clear();
                    idOrgToDelete.clear();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        anuleazaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateLabels();
                    updateListaSali();
                    updateListaArtefacte();
                    updateListaOrganizatori();
                    idSaliToDelete.clear();
                    idSaliToAdd.clear();
                    idArtefacteToDelete.clear();
                    idArtefacteToAdd.clear();
                    idOrgToDelete.clear();
                    idOrgToAdd.clear();
                    saliSelectate.clear();
                    artefacteSelectate.clear();
                    orgSelectat.clear();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

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

        adaugaOrgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.show(panelCont, "AdaugaOrganizator");
            }
        });
        stergeSaliButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedIndex = listSali.getSelectedIndices();
                listSali.setSelectionBackground(Color.RED);

                if(selectedIndex.length == 0)
                    JOptionPane.showMessageDialog(null, "Nu ati selectat nimic", "Eroare", JOptionPane.ERROR_MESSAGE);
                for(int i=0; i<selectedIndex.length; i++)
                {
                    String sel = listSali.getModel().getElementAt(selectedIndex[i]).toString();
                    saliSelectate.add(sel);

                }
                try {
                    PreparedStatement pst = Database.getInstance().connection.prepareStatement("select id_sala from sala where nume = ?");

                    for(int i=0; i<saliSelectate.size(); i++)
                    {
                        pst.setString(1, saliSelectate.get(i));
                        ResultSet rs = pst.executeQuery();

                        while(rs.next())
                        {
                            //System.out.println(rs.getInt("id_sala"));
                            idSaliToDelete.add(rs.getInt("id_sala"));
                        }

                        pst.clearParameters();
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                listSali.setCellRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList listaSali,
                                                                  Object value, int index, boolean isSelected,
                                                                  boolean cellHasFocus) {

                        super.getListCellRendererComponent(listaSali, value, index,
                                isSelected, cellHasFocus);


                        if (isSelected) {
                            setBackground(Color.RED);
                        }

                        return this;
                    }
                });

            }
        });
        stergeArtefacteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedIndex = listArtefacte.getSelectedIndices();

                if(selectedIndex.length == 0)
                    JOptionPane.showMessageDialog(null, "Nu ati selectat nimic", "Eroare", JOptionPane.ERROR_MESSAGE);
                for(int i=0; i<selectedIndex.length; i++)
                {
                    String sel = listArtefacte.getModel().getElementAt(selectedIndex[i]).toString();
                    artefacteSelectate.add(sel);

                }
                try {
                    PreparedStatement pst = Database.getInstance().connection.prepareStatement("select id_artefact from artefact where nume = ?");

                    for(int i=0; i<artefacteSelectate.size(); i++)
                    {
                        pst.setString(1, artefacteSelectate.get(i));
                        ResultSet rs = pst.executeQuery();

                        while(rs.next())
                        {
                            //System.out.println(rs.getInt("id_sala"));
                            idArtefacteToDelete.add(rs.getInt("id_artefact"));
                        }

                        pst.clearParameters();
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                listArtefacte.setCellRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList listaArtefacte,
                                                                  Object value, int index, boolean isSelected,
                                                                  boolean cellHasFocus) {

                        super.getListCellRendererComponent(listaArtefacte, value, index,
                                isSelected, cellHasFocus);


                        if (isSelected) {
                            setBackground(Color.RED);
                        }

                        return this;
                    }
                });

            }
        });
        stergeOrgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedIndex = listaOrg.getSelectedIndices();

                if(selectedIndex.length == 0)
                    JOptionPane.showMessageDialog(null, "Nu ati selectat nimic", "Eroare", JOptionPane.ERROR_MESSAGE);
                for(int i=0; i<selectedIndex.length; i++)
                {
                    String sel = listaOrg.getModel().getElementAt(selectedIndex[i]).toString();
                    orgSelectat.add(sel);

                }

                for(int i=0; i<orgSelectat.size(); i++)
                    System.out.println(orgSelectat.get(i));
                try {
                    PreparedStatement pst = Database.getInstance().connection.prepareStatement("select id_angajat from angajat where nume = ? and prenume = ?");

                    for(int i=0; i<orgSelectat.size(); i++)
                    {
                        String[] split = orgSelectat.get(i).split("\\s");
                        pst.setString(1, split[0]);
                        pst.setString(2, split[1]);
                        //System.out.println(split[0] + " " + split[1]);
                        ResultSet rs = pst.executeQuery();

                        while(rs.next())
                        {
                            //System.out.println(rs.getInt("id_sala"));
                            idOrgToDelete.add(rs.getInt("id_angajat"));
                        }

                        pst.clearParameters();
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                listaOrg.setCellRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList listaOrg,
                                                                  Object value, int index, boolean isSelected,
                                                                  boolean cellHasFocus) {

                        super.getListCellRendererComponent(listaOrg, value, index,
                                isSelected, cellHasFocus);


                        if (isSelected) {
                            setBackground(Color.RED);
                        }

                        return this;
                    }
                });
            }
        });

        adaugaSaliButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idSaliToAdd = adaugaSali.getSali();
            }
        });
        adaugaArtefacteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idArtefacteToAdd = adaugaArtefacte.getIdArtefacte();
            }
        });
        adaugaOrgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idOrgToAdd = adaugaOrganizator.getOrganizatori();
            }
        });

        salveazaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textField1.getText();
                String dateFirst = textField2.getText();
                String dateSec = textField3.getText();

                if(name == "")
                    JOptionPane.showMessageDialog(null, "Nu ati completat numele", "Eroare", JOptionPane.ERROR_MESSAGE);
                else if(dateFirst == "" || dateSec == "")
                    JOptionPane.showMessageDialog(null, "Nu ati completat datile", "Eroare", JOptionPane.ERROR_MESSAGE);
                else {
                    if(!idSaliToDelete.isEmpty()) {
                        try {
                            PreparedStatement pst = Database.getInstance().connection.prepareStatement("delete from sala_expozitie " +
                                    "where id_expozitie = ? and id_sala = ?");


                            for (int i = 0; i < idSaliToDelete.size(); i++) {
                                pst.setInt(1, idExpozitie);
                                pst.setInt(2, idSaliToDelete.get(i));
                                pst.executeUpdate();
                                pst.clearParameters();
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if(!idArtefacteToDelete.isEmpty()) {
                        try {
                            PreparedStatement pst = Database.getInstance().connection.prepareStatement("delete from artefact_expus " +
                                    "where id_expozitie = ? and id_artefact = ?");


                            for (int i = 0; i < idArtefacteToDelete.size(); i++) {
                                pst.setInt(1, idExpozitie);
                                pst.setInt(2, idArtefacteToDelete.get(i));
                                pst.executeUpdate();
                                pst.clearParameters();
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if(!idOrgToDelete.isEmpty()) {
                        try {
                            PreparedStatement pst = Database.getInstance().connection.prepareStatement("delete from angajat_expozitie where " +
                                    "id_expozitie = ? and id_angajat = ?");

                            for (int i = 0; i < idOrgToDelete.size(); i++) {
                                pst.setInt(1, idExpozitie);
                                pst.setInt(2, idOrgToDelete.get(i));
                                pst.executeUpdate();
                                pst.clearParameters();
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    if(!idSaliToAdd.isEmpty())
                    {
                        try {
                            PreparedStatement pst = Database.getInstance().connection.prepareStatement("insert into sala_expozitie(id_sala, id_expozitie) " +
                                    "values (?, ?) ");

                            for(int i=0; i<idSaliToAdd.size(); i++)
                            {
                                pst.setInt(1, idSaliToAdd.get(i));
                                pst.setInt(2, idExpozitie);

                                pst.executeUpdate();
                                pst.clearParameters();

                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if(!idArtefacteToAdd.isEmpty())
                    {
                        try {
                            PreparedStatement pst = Database.getInstance().connection.prepareStatement("insert into artefact_expus(id_artefact, id_expozitie) " +
                                    "values (?, ?) ");

                            for(int i=0; i<idArtefacteToAdd.size(); i++)
                            {
                                pst.setInt(1, idArtefacteToAdd.get(i));
                                pst.setInt(2, idExpozitie);

                                pst.executeUpdate();

                                pst.clearParameters();

                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if(!idOrgToAdd.isEmpty())
                    {
                        try {
                            PreparedStatement pst = Database.getInstance().connection.prepareStatement("insert into angajat_expozitie(id_angajat, id_expozitie) " +
                                    "values (?, ?) ");

                            for(int i=0; i<idOrgToAdd.size(); i++)
                            {
                                pst.setInt(1, idOrgToAdd.get(i));
                                pst.setInt(2, idExpozitie);

                                pst.executeUpdate();

                                pst.clearParameters();

                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    try {
                        PreparedStatement pst = Database.getInstance().connection.prepareStatement("update expozitie " +
                                "set nume = ?, data_inceput = ?, data_final = ? where id_expozitie = ?");

                        pst.setString(1, name);
                        pst.setString(2, dateFirst);
                        pst.setString(3, dateSec);
                        pst.setInt(4, idExpozitie);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    textField1.setText(name);
                    textField2.setText(dateFirst);
                    textField3.setText(dateSec);
                    try {
                        adaugaSali.updateModel();
                        adaugaArtefacte.updateModel();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(null, "Expozitie modificata cu succes", "Success", JOptionPane.INFORMATION_MESSAGE);

                    idSaliToDelete.clear();
                    idSaliToAdd.clear();
                    idArtefacteToDelete.clear();
                    idArtefacteToAdd.clear();
                    idOrgToDelete.clear();
                    idOrgToAdd.clear();
                    saliSelectate.clear();
                    artefacteSelectate.clear();
                    orgSelectat.clear();
                }
            }
        });
    }

    public void SetIdExpozitie(int id)
    {
        this.idExpozitie = id;
    }

    public void updateLabels() throws SQLException {
        PreparedStatement pst = Database.getInstance().connection.prepareStatement("select nume, data_inceput, data_final" +
                " from expozitie where id_expozitie = ?");

        pst.setInt(1, idExpozitie);

        ResultSet rs = pst.executeQuery();

        if(rs.next())
        {
            String nume = rs.getString("nume");
            String dateFirst = rs.getString("data_inceput");
            String dateSec = rs.getString("data_final");

            textField1.setText(nume);
            textField2.setText(dateFirst);
            textField3.setText(dateSec);

        }

    }

    public void updateListaSali() throws SQLException {
        PreparedStatement pst = Database.getInstance().connection.prepareStatement("select nume from sala " +
                "where id_sala in (select id_sala from sala_expozitie where id_expozitie = ?)");

        pst.setInt(1, idExpozitie);

        ResultSet rs = pst.executeQuery();

        DefaultListModel listModel = new DefaultListModel<>();

        while(rs.next())
        {
            String nume = rs.getString("nume");
            listModel.addElement(nume);
        }

        listSali.setModel(listModel);
    }

    public void updateListaArtefacte() throws SQLException {
        PreparedStatement pst = Database.getInstance().connection.prepareStatement("select nume from artefact " +
                "where id_artefact in (select id_artefact from artefact_expus where id_expozitie = ?)");

        pst.setInt(1, idExpozitie);

        ResultSet rs = pst.executeQuery();

        DefaultListModel listModel = new DefaultListModel<>();

        while(rs.next())
        {
            String nume = rs.getString("nume");
            listModel.addElement(nume);
        }

        listArtefacte.setModel(listModel);

    }

    public void updateListaOrganizatori() throws SQLException {
        PreparedStatement pst = Database.getInstance().connection.prepareStatement("select nume, prenume from angajat " +
                "where id_angajat in (select id_angajat from angajat_expozitie where id_expozitie = ?)");

        pst.setInt(1, idExpozitie);

        ResultSet rs = pst.executeQuery();

        DefaultListModel listModel = new DefaultListModel<>();

        while(rs.next())
        {
            String nume = rs.getString("nume");
            String prenume = rs.getString("prenume");
            String numePrenume = nume + " " + prenume;
            listModel.addElement(numePrenume);
        }

        listaOrg.setModel(listModel);

    }

}
