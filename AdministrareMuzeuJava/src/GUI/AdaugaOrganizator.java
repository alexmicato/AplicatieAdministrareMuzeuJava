package GUI;

import Database.Database;
import com.microsoft.sqlserver.jdbc.StringUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class AdaugaOrganizator extends JPanel {

    private JButton inapoiButton;
    private JButton alegeButton;
    public JPanel adaugaOrganizatori;
    private JScrollPane selOrganizatori;
    private JList listaOrganizatori;
    private JLabel titlu;
    private JPanel buttonPanel;
    Vector<String> orgSelectat = new Vector<>();
    Vector<Integer> idOrganizator = new Vector<>();

    String previousTab = new String();

    AdaugaOrganizator(JPanel panelCont, CardLayout c) throws SQLException {

        boolean selectatOrganizator = false;
        boolean selectatSali = false;
        boolean selectatArtefact = false;
        this.setLayout(new BorderLayout());
        initComp();

        inapoiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.show(panelCont, previousTab);
            }
        });


        /*listaOrganizatori.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()) {

                    orgSelectat.add((String) listaOrganizatori.getSelectedValue());
                }

                for(int i=0; i<orgSelectat.size(); i++)
                    System.out.println(orgSelectat.get(i));
            }
        });*/

        alegeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                orgSelectat.clear();
                idOrganizator.clear();
                int[] selectedIndex = listaOrganizatori.getSelectedIndices();

                if(selectedIndex.length == 0)
                    JOptionPane.showMessageDialog(null, "Nu ati selectat nimic", "Eroare", JOptionPane.ERROR_MESSAGE);
                for(int i=0; i<selectedIndex.length; i++)
                {
                    String sel = listaOrganizatori.getModel().getElementAt(selectedIndex[i]).toString();
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
                            idOrganizator.add(rs.getInt("id_angajat"));
                        }

                        pst.clearParameters();
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    public void setPreviousTab(String tab)
    {
        previousTab = tab;
    }
    Vector<Integer> getOrganizatori()
    {
        return idOrganizator;
    }

    public void initComp() throws SQLException {
        buttonPanel = new JPanel(new FlowLayout());
        inapoiButton = new JButton("Inapoi");
        alegeButton = new JButton("Alege");
        titlu = new JLabel("Personal disponibil");

        buttonPanel.add(inapoiButton);
        this.add(buttonPanel, BorderLayout.WEST);
        this.add(alegeButton, BorderLayout.SOUTH);
        this.add(titlu, BorderLayout.NORTH);

        PreparedStatement pst = Database.getInstance().connection.prepareStatement("SELECT nume, prenume \n" +
                "FROM   angajat \n" +
                "WHERE  id_angajat IN (SELECT id_angajat FROM rol_angajat where id_rol = 2)");

        ResultSet angajati = pst.executeQuery();

        writeResultSet(angajati);

        this.add(selOrganizatori, BorderLayout.CENTER);
    }

    public void writeResultSet(ResultSet angajati) throws SQLException {
        DefaultListModel listModel = new DefaultListModel();


        while(angajati.next()){
            String nume = angajati.getString("nume");
            String prenume = angajati.getString("prenume");
            String numePrenume = nume + " " + prenume;
            listModel.addElement(numePrenume);
            //System.out.println(nume);
            // temp.add(toateSali.getString("nume"));
        }

        listaOrganizatori = new JList<>(listModel);
        selOrganizatori = new JScrollPane(listaOrganizatori);

    }
}
