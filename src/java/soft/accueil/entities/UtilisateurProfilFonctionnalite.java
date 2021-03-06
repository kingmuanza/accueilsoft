package soft.accueil.entities;
// Generated 16 janv. 2018 13:43:22 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UtilisateurProfilFonctionnalite generated by hbm2java
 */
@Entity
@Table(name="utilisateur_profil_fonctionnalite"
    ,catalog="accueilsoft"
)
public class UtilisateurProfilFonctionnalite  implements java.io.Serializable {


     private Integer idutilisateurProfilFonctionnalite;
     private Fonctionnalite fonctionnalite;
     private UtilisateurProfil utilisateurProfil;
     private Integer nombreUtilisation;

    public UtilisateurProfilFonctionnalite() {
    }

    public UtilisateurProfilFonctionnalite(Fonctionnalite fonctionnalite, UtilisateurProfil utilisateurProfil, Integer nombreUtilisation) {
       this.fonctionnalite = fonctionnalite;
       this.utilisateurProfil = utilisateurProfil;
       this.nombreUtilisation = nombreUtilisation;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idutilisateur_profil_fonctionnalite", unique=true, nullable=false)
    public Integer getIdutilisateurProfilFonctionnalite() {
        return this.idutilisateurProfilFonctionnalite;
    }
    
    public void setIdutilisateurProfilFonctionnalite(Integer idutilisateurProfilFonctionnalite) {
        this.idutilisateurProfilFonctionnalite = idutilisateurProfilFonctionnalite;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idfonctionnalite")
    public Fonctionnalite getFonctionnalite() {
        return this.fonctionnalite;
    }
    
    public void setFonctionnalite(Fonctionnalite fonctionnalite) {
        this.fonctionnalite = fonctionnalite;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idutilisateur_profil")
    public UtilisateurProfil getUtilisateurProfil() {
        return this.utilisateurProfil;
    }
    
    public void setUtilisateurProfil(UtilisateurProfil utilisateurProfil) {
        this.utilisateurProfil = utilisateurProfil;
    }

    
    @Column(name="nombre_utilisation")
    public Integer getNombreUtilisation() {
        return this.nombreUtilisation;
    }
    
    public void setNombreUtilisation(Integer nombreUtilisation) {
        this.nombreUtilisation = nombreUtilisation;
    }




}


