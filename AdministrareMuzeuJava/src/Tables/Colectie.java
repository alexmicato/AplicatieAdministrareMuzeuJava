package Tables;

public class Colectie {

    private int idColectie;

    private String nume;

    private String descriere;

    public Colectie(String nume, String descriere)
    {

        this.nume = nume;
        this.descriere = descriere;
    }

    public Colectie(int idColectie, String nume, String descriere)
    {

        this.idColectie = idColectie;
        this.nume = nume;
        this.descriere = descriere;
    }


    public int getIdColectie() {
        return idColectie;
    }

    public String getNume()
    {
        return nume;
    }

    public String getDescriere()
    {
        return descriere;
    }

    public void setIdColectie(int idColectie)
    {
        this.idColectie = idColectie;
    }

    public void setNume(String nume)
    {
        this.nume = nume;
    }

    public void setDescriere(String descriere)
    {
        this.descriere = descriere;
    }
}
