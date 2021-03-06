package soft.accueil.entities;
// Generated 16 janv. 2018 13:43:22 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Bureau generated by hbm2java
 */
@Entity
@Table(name="bureau"
    ,catalog="accueilsoft"
)
public class Bureau  implements java.io.Serializable {


     private Integer idbureau;
     private Batiment batiment;
     private String code;
     private String libelle;
     private String etage;
     private String numeroPorte;
     private Integer idutilisateur;
     private Set<Employe> employes = new HashSet<Employe>(0);
     private Set<Colis> colises = new HashSet<Colis>(0);
     private Set<Entree> entrees = new HashSet<Entree>(0);

    public Bureau() {
    }

    public Bureau(Batiment batiment, String code, String libelle, String etage, String numeroPorte, Integer idutilisateur, Set<Employe> employes, Set<Colis> colises, Set<Entree> entrees) {
       this.batiment = batiment;
       this.code = code;
       this.libelle = libelle;
       this.etage = etage;
       this.numeroPorte = numeroPorte;
       this.idutilisateur = idutilisateur;
       this.employes = employes;
       this.colises = colises;
       this.entrees = entrees;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idbureau", unique=true, nullable=false)
    public Integer getIdbureau() {
        return this.idbureau;
    }
    
    public void setIdbureau(Integer idbureau) {
        this.idbureau = idbureau;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idbatiment")
    public Batiment getBatiment() {
        return this.batiment;
    }
    
    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }

    
    @Column(name="code", length=45)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    
    @Column(name="libelle")
    public String getLibelle() {
        return this.libelle;
    }
    
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    
    @Column(name="etage", length=45)
    public String getEtage() {
        return this.etage;
    }
    
    public void setEtage(String etage) {
        this.etage = etage;
    }

    
    @Column(name="numero_porte", length=45)
    public String getNumeroPorte() {
        return this.numeroPorte;
    }
    
    public void setNumeroPorte(String numeroPorte) {
        this.numeroPorte = numeroPorte;
    }

    
    @Column(name="idutilisateur")
    public Integer getIdutilisateur() {
        return this.idutilisateur;
    }
    
    public void setIdutilisateur(Integer idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="bureau")
    public Set<Employe> getEmployes() {
        return this.employes;
    }
    
    public void setEmployes(Set<Employe> employes) {
        this.employes = employes;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="bureau")
    public Set<Colis> getColises() {
        return this.colises;
    }
    
    public void setColises(Set<Colis> colises) {
        this.colises = colises;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="bureau")
    public Set<Entree> getEntrees() {
        return this.entrees;
    }
    
    public void setEntrees(Set<Entree> entrees) {
        this.entrees = entrees;
    }




}


