package soft.accueil.entities;
// Generated 16 janv. 2018 13:43:22 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * UtilisateurProfil generated by hbm2java
 */
@Entity
@Table(name="utilisateur_profil"
    ,catalog="accueilsoft"
    , uniqueConstraints = {@UniqueConstraint(columnNames="code"), @UniqueConstraint(columnNames="nom")} 
)
public class UtilisateurProfil  implements java.io.Serializable {


     private Integer idutilisateurProfil;
     private String code;
     private String nom;
     private String statut;
     private Integer idutilisateur;
     private Date dateCreation;
     private Set<UtilisateurProfilFonctionnalite> utilisateurProfilFonctionnalites = new HashSet<UtilisateurProfilFonctionnalite>(0);
     private Set<Utilisateur> utilisateurs = new HashSet<Utilisateur>(0);

    public UtilisateurProfil() {
    }

    public UtilisateurProfil(String code, String nom, String statut, Integer idutilisateur, Date dateCreation, Set<UtilisateurProfilFonctionnalite> utilisateurProfilFonctionnalites, Set<Utilisateur> utilisateurs) {
       this.code = code;
       this.nom = nom;
       this.statut = statut;
       this.idutilisateur = idutilisateur;
       this.dateCreation = dateCreation;
       this.utilisateurProfilFonctionnalites = utilisateurProfilFonctionnalites;
       this.utilisateurs = utilisateurs;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idutilisateur_profil", unique=true, nullable=false)
    public Integer getIdutilisateurProfil() {
        return this.idutilisateurProfil;
    }
    
    public void setIdutilisateurProfil(Integer idutilisateurProfil) {
        this.idutilisateurProfil = idutilisateurProfil;
    }

    
    @Column(name="code", unique=true, length=45)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    
    @Column(name="nom", unique=true)
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    
    @Column(name="statut", length=45)
    public String getStatut() {
        return this.statut;
    }
    
    public void setStatut(String statut) {
        this.statut = statut;
    }

    
    @Column(name="idutilisateur")
    public Integer getIdutilisateur() {
        return this.idutilisateur;
    }
    
    public void setIdutilisateur(Integer idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_creation", length=19)
    public Date getDateCreation() {
        return this.dateCreation;
    }
    
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="utilisateurProfil")
    public Set<UtilisateurProfilFonctionnalite> getUtilisateurProfilFonctionnalites() {
        return this.utilisateurProfilFonctionnalites;
    }
    
    public void setUtilisateurProfilFonctionnalites(Set<UtilisateurProfilFonctionnalite> utilisateurProfilFonctionnalites) {
        this.utilisateurProfilFonctionnalites = utilisateurProfilFonctionnalites;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="utilisateurProfil")
    public Set<Utilisateur> getUtilisateurs() {
        return this.utilisateurs;
    }
    
    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }




}


