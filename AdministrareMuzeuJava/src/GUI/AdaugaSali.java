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

public class AdaugaSali extends JPanel{

    private JButton inapoiButton;
    private JButton alegeButton;
    public JPanel adaugaSali;
    private JScrollPane selSali;
    private JList listaSali;
    private JLabel titlu;
    private JPanel buttonPanel;
    Vector<String> saliSelectate = new Vector<>();
    Vector<Integer> idSali = new Vector<>();
    String previousTab = new String();
    AdaugaSali(JPanel panelCont, CardLayout c) throws SQLException {

        this.setLayout(new BorderLayout());
        initComp();

        inapoiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                c.show(panelCont, previousTab);
            }
        });


        /*listaSali.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()) {

                    saliSelectate.add((String) listaSali.getSelectedValue());
                }

                for(int i=0; i<saliSelectate.size(); i++)
                    System.out.println(saliSelectate.get(i));
            }
        });*/

        alegeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                saliSelectate.clear();
                idSali.clear();
                int[] selectedIndex = listaSali.getSelectedIndices();

                if(selectedIndex.length == 0)
                    JOptionPane.showMessageDialog(null, "Nu ati selectat nimic", "Eroare", JOptionPane.ERROR_MESSAGE);
                for(int i=0; i<selectedIndex.length; i++)
                {
                    String sel = listaSali.getModel().getElementAt(selectedIndex[i]).toString();
                    saliSelectate.add(sel);

                }
                for(int i=0; i<saliSelectate.size(); i++)
                    System.out.println(saliSelectate.get(i));
                try {
                    PreparedStatement pst = Database.getInstance().connection.prepareStatement("select id_sala from sala where nume = ?");

                    for(int i=0; i<saliSelectate.size(); i++)
                    {
                        pst.setString(1, saliSelectate.get(i));
                        ResultSet rs = pst.executeQuery();

                        while(rs.next())
                        {
                            //System.out.println(rs.getInt("id_sala"));
                            idSali.add(rs.getInt("id_sala"));
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

    Vector<Integer> getSali()
    {
        return idSali;
    }
    public void updateModel() throws SQLException {
        PreparedStatement pst = Database.getInstance().connection.prepareStatement("SELECT id_sala, nume \n" +
                "FROM   sala \n" +
                "WHERE  id_sala NOT IN (SELECT id_sala FROM sala_expozitie)");

        ResultSet resultSet = pst.executeQuery();
        DefaultListModel listModel = new DefaultListModel();


        while(resultSet.next()){
            String nume = resultSet.getString("nume");
            listModel.addElement(nume);
        }

        listaSali.setModel(listModel);

    }
    public void initComp() throws SQLException {
        buttonPanel = new JPanel(new FlowLayout());
        inapoiButton = new JButton("Inapoi");
        alegeButton = new JButton("Alege");
        titlu = new JLabel("Sali disponibile");

        buttonPanel.add(inapoiButton);
        this.add(buttonPanel, BorderLayout.WEST);
        this.add(alegeButton, BorderLayout.SOUTH);
        this.add(titlu, BorderLayout.NORTH);

        PreparedStatement pst = Database.getInstance().connection.prepareStatement("SELECT id_sala, nume \n" +
                "FROM   sala \n" +
                "WHERE  id_sala NOT IN (SELECT id_sala FROM sala_expozitie)");

        ResultSet sali = pst.executeQuery();

        writeResultSet(sali);

        this.add(selSali, BorderLayout.CENTER);
    }

    public void writeResultSet(ResultSet sali) throws SQLException {
        DefaultListModel listModel = new DefaultListModel();


        while(sali.next()){
            String nume = sali.getString("nume");
            listModel.addElement(nume);
            //System.out.println(nume);
           // temp.add(toateSali.getString("nume"));
        }

        listaSali = new JList<>(listModel);
        selSali = new JScrollPane(listaSali);

    }

}
