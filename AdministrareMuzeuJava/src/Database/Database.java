package Database;

import Tables.Angajat;
import Tables.Artefact;
import Tables.Colectie;
import Tables.Rol;

import java.sql.*;
import java.util.Vector;

public class Database {
    private String connectionString = "jdbc:sqlserver://DESKTOP-1VJGKR1\\SQLEXPRESS01:1433;DatabaseName=muzeu1;user=Alex;password=nappy;encrypt=true;trustServerCertificate=true";

    private static Database instance = null;

    public Connection connection;

    private Database()
    {
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            connection = DriverManager.getConnection(connectionString);

            if(connection != null)
                System.out.println("Connection established");
            else
                System.out.println("Connection failed");
        }
        catch (SQLException | ClassNotFoundException e)
        {
            System.out.println(e);
        }
    }

    public static Database getInstance(){
        if(instance == null)
            instance = new Database();
        return instance;
    }

    public void insertAngajat(Angajat angajat) throws SQLException {
        String nume = angajat.getNume();
        String prenume = angajat.getPrenume();

        PreparedStatement pst = instance.connection.prepareStatement("insert into angajat(nume, prenume) values (?, ?)",
                Statement.RETURN_GENERATED_KEYS);

        pst.setString(1, nume);
        pst.setString(2, prenume);

        pst.executeUpdate();

        ResultSet rs = pst.getGeneratedKeys();

        if(rs.next()){
            angajat.setIdAngajat(rs.getInt(1));
        }
    }

    public void deleteAngajat(Angajat angajat) throws SQLException {
        PreparedStatement pst = instance.connection.prepareStatement("delete from angajat where id_angajat = ?");

        pst.setInt(1, angajat.getIdAngajat());

        pst.executeUpdate();

    }

    public Vector<Angajat> selectAngajat() throws SQLException {
        PreparedStatement pst = instance.connection.prepareStatement("select id_angajat, nume, prenume from angajat");

        ResultSet rs = pst.executeQuery();

        Vector<Angajat> angajati = new Vector<>();

        while(rs.next())
        {
            String nume = rs.getString("nume");
            String prenume = rs.getString("prenume");
            int idAngajat = rs.getInt("id_angajat");

            angajati.add(new Angajat(idAngajat, nume, prenume));
        }

        return angajati;
    }

    public void insertArtefact(Artefact artefact) throws SQLException {
        int idColectie = artefact.getIdColectie();
        String nume = artefact.getNume();;
        String descriere = artefact.getDescriere();
        String conditie = artefact.getConditie();
        String furnizor = artefact.getFurnizor();

        if(furnizor == null)
        {
            PreparedStatement pst = instance.connection.prepareStatement("insert into artefact(id_colectie, nume," +
                    " descriere, conditie) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, idColectie);
            pst.setString(2, nume);
            pst.setString(3, descriere);
            pst.setString(4, conditie);


            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            if(rs.next()) {
                artefact.setIdArtefact(rs.getInt(1));
            }
        }
        else {
            PreparedStatement pst = instance.connection.prepareStatement("insert into artefact(id_colectie, nume," +
                    " descriere, conditie, furnizor) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, idColectie);
            pst.setString(2, nume);
            pst.setString(3, descriere);
            pst.setString(4, conditie);
            pst.setString(5, furnizor);

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            if(rs.next()) {
                artefact.setIdArtefact(rs.getInt(1));
            }
        }
    }

    public void deleteArtefact(Artefact artefact) throws SQLException {
        PreparedStatement pst = instance.connection.prepareStatement("delete from artefact where id_artefact = ?");

        pst.setInt(1, artefact.getIdArtefact());

        pst.executeUpdate();
    }

    public Vector<Artefact> selectArtefact() throws SQLException {
        PreparedStatement pst = instance.connection.prepareStatement("select id_artefact, id_colectie, nume, descriere, " +
                "conditie, furnizor from artefact");

        ResultSet rs = pst.executeQuery();

        Vector<Artefact> artefacte = new Vector<>();

        while(rs.next())
        {
            int idArtefact = rs.getInt("id_artefact");
            String nume = rs.getString("nume");
            String descriere = rs.getString("descriere");
            int idColectie = rs.getInt("id_colectie");
            String conditie = rs.getString("conditie");
            String furnizor = rs.getString("furnizor");


            if(furnizor == null)
                artefacte.add(new Artefact(idArtefact, idColectie, nume, descriere, conditie));
            else
                artefacte.add(new Artefact(idArtefact, idColectie, nume, descriere, conditie, furnizor));
        }

        return artefacte;
    }

    public void insertColectie(Colectie colectie) throws SQLException {
        String nume = colectie.getNume();
        String descriere = colectie.getDescriere();

        PreparedStatement pst = instance.connection.prepareStatement("insert into colectie(nume, descriere) values (?, ?)",
                Statement.RETURN_GENERATED_KEYS);

        pst.setString(1, nume);
        pst.setString(2, descriere);

        pst.executeUpdate();

        ResultSet rs = pst.getGeneratedKeys();

        if(rs.next()){
            colectie.setIdColectie(rs.getInt(1));
        }

    }

    public void deleteColectie(Colectie colectie) throws SQLException {
        PreparedStatement pst = instance.connection.prepareStatement("delete from colectie where id_colectie = ?");

        pst.setInt(1, colectie.getIdColectie());

        pst.executeUpdate();
    }

    public Vector<Colectie> selectColectie() throws SQLException {
        PreparedStatement pst = instance.connection.prepareStatement("select id_colectie, nume, descriere from colectie");

        ResultSet rs = pst.executeQuery();

        Vector<Colectie> colectii = new Vector<>();

        while(rs.next())
        {
            String nume = rs.getString("nume");
            String descriere = rs.getString("descriere");
            int idColectie = rs.getInt("id_colectie");

            colectii.add(new Colectie(idColectie, nume, descriere));
        }

        return colectii;
    }

    public Vector<Rol> selectRol() throws SQLException {
        PreparedStatement pst = instance.connection.prepareStatement("select id_rol, nume, salariu from rol");

        ResultSet rs = pst.executeQuery();

        Vector<Rol> roluri = new Vector<>();

        while(rs.next())
        {
            int idRol = rs.getInt("id_rol");
            String nume = rs.getString("nume");
            int salariu = rs.getInt("salariu");

            roluri.add(new Rol(idRol, nume, salariu));
        }

        return roluri;
    }

    public void insertIntoRolAngajat(int idAngajat, int idRol) throws SQLException {

        PreparedStatement pst = instance.connection.prepareStatement("insert into rol_angajat(id_angajat, id_rol) values " +
                "(?, ?)");

        pst.setInt(1, idAngajat);
        pst.setInt(2, idRol);

        pst.executeUpdate();
    }





}

