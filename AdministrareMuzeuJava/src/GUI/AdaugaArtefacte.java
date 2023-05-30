package GUI;

import Database.Database;

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

public class AdaugaArtefacte extends JPanel{
    private JButton inapoiButton;
    private JButton alegeButton;
    public JPanel adaugaArtefacte;
    private JScrollPane selArtefacte;
    private JList listaArtefacte;
    private JLabel titlu;
    private JPanel buttonPanel;
    Vector<String> artefacteSelectate = new Vector<>();
    Vector<Integer> idArtefacte = new Vector<>();

    String previousTab = new String();

    AdaugaArtefacte(JPanel panelCont, CardLayout c) throws SQLException {

        this.setLayout(new BorderLayout());
        initComp();

        inapoiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.show(panelCont, previousTab);
            }
        });


        /*listaArtefacte.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()) {

                    artefacteSelectate.add((String) listaArtefacte.getSelectedValue());
                }

                for(int i=0; i<artefacteSelectate.size(); i++)
                    System.out.println(artefacteSelectate.get(i));
            }
        });*/

        alegeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                artefacteSelectate.clear();
                idArtefacte.clear();
                int[] selectedIndex = listaArtefacte.getSelectedIndices();

                if(selectedIndex.length == 0)
                    JOptionPane.showMessageDialog(null, "Nu ati selectat nimic", "Eroare", JOptionPane.ERROR_MESSAGE);
                for(int i=0; i<selectedIndex.length; i++)
                {
                    String sel = listaArtefacte.getModel().getElementAt(selectedIndex[i]).toString();
                    artefacteSelectate.add(sel);

                }
            for(int i=0; i<artefacteSelectate.size(); i++)
                System.out.println(artefacteSelectate.get(i));
                try {
                    PreparedStatement pst = Database.getInstance().connection.prepareStatement("select id_artefact from artefact where nume = ?");

                    for(int i=0; i<artefacteSelectate.size(); i++)
                    {
                        pst.setString(1, artefacteSelectate.get(i));
                        ResultSet rs = pst.executeQuery();

                        while(rs.next())
                        {
                            System.out.println(rs.getInt("id_artefact"));
                            idArtefacte.add(rs.getInt("id_artefact"));
                        }

                        pst.clearParameters();
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                repaint();
            }
        });

    }
    public void setPreviousTab(String tab)
    {
        previousTab = tab;
    }
    Vector<Integer> getIdArtefacte()
    {
        return idArtefacte;
    }

    public void updateModel() throws SQLException {
        PreparedStatement pst = Database.getInstance().connection.prepareStatement("SELECT nume \n" +
                "FROM   artefact \n" +
                "WHERE  id_artefact NOT IN (SELECT id_artefact FROM artefact_expus)");

        ResultSet resultSet = pst.executeQuery();
        DefaultListModel listModel = new DefaultListModel();


        while(resultSet.next()){
            String nume = resultSet.getString("nume");
            listModel.addElement(nume);
        }

        listaArtefacte.setModel(listModel);
    }

    public void initComp() throws SQLException {
        buttonPanel = new JPanel(new FlowLayout());
        inapoiButton = new JButton("Inapoi");
        alegeButton = new JButton("Alege");
        titlu = new JLabel("Artefacte disponibile");

        buttonPanel.add(inapoiButton);
        this.add(buttonPanel, BorderLayout.WEST);
        this.add(alegeButton, BorderLayout.SOUTH);
        this.add(titlu, BorderLayout.NORTH);

        PreparedStatement pst = Database.getInstance().connection.prepareStatement("SELECT nume \n" +
                "FROM   artefact \n" +
                "WHERE  id_artefact NOT IN (SELECT id_artefact FROM artefact_expus)");

        ResultSet artefacte = pst.executeQuery();

        writeResultSet(artefacte);

        this.add(selArtefacte, BorderLayout.CENTER);
    }

    public void writeResultSet(ResultSet artefacte) throws SQLException {
        DefaultListModel listModel = new DefaultListModel();


        while(artefacte.next()){
            String nume = artefacte.getString("nume");
            listModel.addElement(nume);
            //System.out.println(nume);
            // temp.add(toateSali.getString("nume"));
        }

        listaArtefacte = new JList<>(listModel);
        selArtefacte = new JScrollPane(listaArtefacte);

    }

}
