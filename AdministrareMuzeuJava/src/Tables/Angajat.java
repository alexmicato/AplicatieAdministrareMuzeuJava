package Tables;

public class Angajat {

    private int idAngajat;
    private String nume;
    private String prenume;

    public Angajat(String nume, String prenume)
    {

        this.nume = nume;
        this.prenume = prenume;
    }

    public Angajat(int idAngajat, String nume, String prenume)
    {
        this.idAngajat = idAngajat;
        this.nume = nume;
        this.prenume = prenume;
    }


    public int getIdAngajat() {
        return idAngajat;
    }

    public String getNume()
    {
        return nume;
    }

    public String getPrenume()
    {
        return prenume;
    }

    public void setIdAngajat(int idAngajat)
    {
        this.idAngajat = idAngajat;
    }

    public void setNume(String nume)
    {
        this.nume = nume;
    }

    public void setPrenume(String prenume)
    {
        this.prenume = prenume;
    }



}
