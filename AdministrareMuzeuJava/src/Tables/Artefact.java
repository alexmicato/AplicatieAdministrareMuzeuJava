package Tables;

public class Artefact {

    private int idArtefact;

    private int idColectie;

    private String nume;

    private String descriere;

    private String conditie;

    private String furnizor;

    public Artefact(int idArtefact, int idColectie, String nume, String descriere, String conditie, String furnizor) {
        this.idArtefact = idArtefact;
        this.idColectie = idColectie;
        this.nume = nume;
        this.descriere = descriere;
        this.conditie = conditie;
        this.furnizor = furnizor;
    }

    public Artefact(int idArtefact, int idColectie, String nume, String descriere, String conditie) {
        this.idArtefact = idArtefact;
        this.idColectie = idColectie;
        this.nume = nume;
        this.descriere = descriere;
        this.conditie = conditie;
    }

    public Artefact(int idColectie, String nume, String descriere, String conditie, String furnizor) {

        this.idColectie = idColectie;
        this.nume = nume;
        this.descriere = descriere;
        this.conditie = conditie;
        this.furnizor = furnizor;
    }

    public Artefact(int idColectie, String nume, String descriere, String conditie) {

        this.idColectie = idColectie;
        this.nume = nume;
        this.descriere = descriere;
        this.conditie = conditie;
    }

    public int getIdArtefact() {
        return idArtefact;
    }

    public int getIdColectie() {
        return idColectie;
    }


    public String getNume() {
        return nume;
    }

    public String getDescriere(){
        return descriere;
    }

    public String getConditie(){
        return conditie;
    }

    public String getFurnizor()
    {
        return furnizor;
    }

    public void setIdArtefact(int idArtefact){
        this.idArtefact = idArtefact;
    }

    public void setIdColectie(int idColectie){
        this.idColectie = idColectie;
    }

    public void setNume(String nume){
        this.nume = nume;
    }

    public void setDescriere(String descriere){
        this.descriere = descriere;
    }

    public void setConditie(String conditie){
        this.conditie = conditie;
    }

    public void setFurnizor(String furnizor){
        this.furnizor = furnizor;
    }
}
