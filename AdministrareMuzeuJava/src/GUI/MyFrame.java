package GUI;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MyFrame extends JFrame {

    JPanel panelCont = new JPanel();
    CardLayout c = new CardLayout();
    JMenuBar menuBar;
    JMenu acasa;
    JMenu personal;
    JMenu expozitii;
    JMenu artefacte;
    JMenuItem addAngajati;
    JMenuItem listAngajati;
    JMenuItem addExpo;
    JMenuItem veziExpo;
    JMenuItem addArt;
    JMenuItem veziArt;
    JMenuItem addCol;
    JMenuItem veziCol;
    AdaugaExpozitie adaugaExpozitie;
    Acasa acasaPanel;
    ListaExpozitii listaExpozitii;
    AdaugaSali adaugaSali;
    AdaugaArtefacte adaugaArtefacte;
    AdaugaOrganizator adaugaOrganizator;
    AdaugaAngajati adaugaAngajati;

    ListaAngajati listaAngajati;

    ListaColectii listaColectii;
    ListaArtefacte listaArtefacte;

    AdaugaArtefact adaugaArtefact;

    AdaugaColectie adaugaColectie;
    public MyFrame() throws SQLException {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        this.setLayout(new FlowLayout());
        this.getContentPane().setBackground(Color.WHITE);
        initMenuBar();

        panelCont.setLayout(c);
        acasaPanel = new Acasa();
        panelCont.add(acasaPanel.acasa, "acasa");
        adaugaSali = new AdaugaSali(panelCont, c);
        adaugaArtefacte = new AdaugaArtefacte(panelCont, c);
        adaugaOrganizator = new AdaugaOrganizator(panelCont, c);
        panelCont.add(adaugaSali, "AdaugaSali");
        panelCont.add(adaugaArtefacte, "AdaugaArtefacte");
        panelCont.add(adaugaOrganizator, "AdaugaOrganizator");
        adaugaExpozitie = new AdaugaExpozitie(panelCont, c, adaugaSali, adaugaOrganizator, adaugaArtefacte);
        panelCont.add(adaugaExpozitie.panel, "addExpo");
        listaExpozitii = new ListaExpozitii(panelCont, c, adaugaSali, adaugaArtefacte, adaugaOrganizator);
        panelCont.add(listaExpozitii.panel, "listaExpo");

        adaugaAngajati = new AdaugaAngajati(panelCont, c);
        panelCont.add(adaugaAngajati.adaugaAngajati, "AdaugaAngajati");

        listaAngajati = new ListaAngajati(panelCont, c);
        panelCont.add(listaAngajati.listaAngajati, "ListaAngajati");

        listaArtefacte = new ListaArtefacte();
        panelCont.add(listaArtefacte.listaArtefacte, "ListaArtefacte");

        listaColectii = new ListaColectii();
        panelCont.add(listaColectii.listaColectii, "ListaColectii");

        adaugaArtefact = new AdaugaArtefact();
        panelCont.add(adaugaArtefact.adaugaArtefact, "AdaugaArtefact");

        adaugaColectie = new AdaugaColectie();
        panelCont.add(adaugaColectie.adColectie, "AdaugaColectie");

        c.show(panelCont, "acasa");

        addExpo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /*try {
                    adaugaExpozitie = new AdaugaExpozitie(panelCont, c);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }*/
                adaugaArtefacte.setPreviousTab("addExpo");
                adaugaOrganizator.setPreviousTab("addExpo");
                adaugaSali.setPreviousTab("addExpo");
                c.show(panelCont, "addExpo");
            }
        });


        veziExpo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*try {
                    veziExpozitii = new VeziExpozitii(panelCont, c);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }*/
                adaugaArtefacte.setPreviousTab("veziExpo");
                adaugaOrganizator.setPreviousTab("veziExpo");
                adaugaSali.setPreviousTab("veziExpo");
                c.show(panelCont, "listaExpo");
            }
        });

        acasa.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                c.show(panelCont, "acasa");
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        addAngajati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.show(panelCont, "AdaugaAngajati");
            }
        });

        listAngajati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.show(panelCont, "ListaAngajati");
            }
        });

        veziArt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.show(panelCont, "ListaArtefacte");
            }
        });

        veziCol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.show(panelCont, "ListaColectii");
            }
        });

        addArt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    adaugaArtefact.createList();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                c.show(panelCont, "AdaugaArtefact");
            }
        });

        addCol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.show(panelCont, "AdaugaColectie");
            }
        });

        this.add(panelCont);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void initMenuBar()
    {
        menuBar = new JMenuBar();

        acasa = new JMenu("Acasa");
        personal = new JMenu("Personal");
        expozitii = new JMenu("Expozitii");
        artefacte = new JMenu("Artefacte");

        addAngajati = new JMenuItem("Adauga angajat");
        listAngajati = new JMenuItem("Vezi lista angajati");

        personal.add(addAngajati);
        personal.add(listAngajati);

        addExpo = new JMenuItem("Adauga expozitie");
        veziExpo = new JMenuItem("Lista expozitii");

        expozitii.add(addExpo);
        expozitii.add(veziExpo);

        addArt = new JMenuItem("Adauga artefacte");
        veziArt = new JMenuItem("Vezi artefacte");
        veziCol = new JMenuItem("Vezi colectii");
        addCol = new JMenuItem("Adauga colectie");


        artefacte.add(veziCol);
        artefacte.add(veziArt);
        artefacte.add(addCol);
        artefacte.add(addArt);

        menuBar.add(acasa);
        menuBar.add(personal);
        menuBar.add(expozitii);
        menuBar.add(artefacte);

        this.setJMenuBar(menuBar);
    }
}
