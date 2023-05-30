package Tables;

public class Rol {

    private int idRol;

    private String nume;

    private int salariu;

    public Rol( String nume, int salariu){

        this.nume = nume;
        this.salariu = salariu;
    }

    public Rol( int idRol, String nume, int salariu){

        this.idRol = idRol;
        this.nume = nume;
        this.salariu = salariu;
    }

    public int getIdRol(){
        return idRol;
    }

    public String getNume(){
        return nume;
    }

    public int getSalariu(){
        return salariu;
    }

    public void setIdRol(int idRol){
        this.idRol = idRol;
    }

    public void setNume(String nume){
        this.nume = nume;
    }

    public void setSalariu(int salariu){
        this.salariu = salariu;
    }
}
