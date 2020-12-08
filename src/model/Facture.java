package model;

public class Facture extends Entity {

    private int id;
    private int montant;
    private Contrat contrat;

    public Facture() {
        this(0);
    }

    public Facture(int id) {
        this(id, 0);
    }
    
    public Facture(int id, int montant) {
        super();
        this.id = id;
        this.montant = montant;
    }

    public Facture(int id, int montant, Contrat contrat) {
        super();
        this.id = id;
        this.montant = montant;
        this.contrat = contrat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }
}