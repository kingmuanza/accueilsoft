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
 * Fonctionnalite generated by hbm2java
 */
@Entity
@Table(name="fonctionnalite"
    ,catalog="accueilsoft"
)
public class Fonctionnalite  implements java.io.Serializable {


     private Integer idfonctionnalite;
     private Fonctionnalite fonctionnalite;
     private String code;
     private String nom;
     private String servletName;
     private Set<UtilisateurProfilFonctionnalite> utilisateurProfilFonctionnalites = new HashSet<UtilisateurProfilFonctionnalite>(0);
     private Set<Fonctionnalite> fonctionnalites = new HashSet<Fonctionnalite>(0);

    public Fonctionnalite() {
    }

    public Fonctionnalite(Fonctionnalite fonctionnalite, String code, String nom, String servletName, Set<UtilisateurProfilFonctionnalite> utilisateurProfilFonctionnalites, Set<Fonctionnalite> fonctionnalites) {
       this.fonctionnalite = fonctionnalite;
       this.code = code;
       this.nom = nom;
       this.servletName = servletName;
       this.utilisateurProfilFonctionnalites = utilisateurProfilFonctionnalites;
       this.fonctionnalites = fonctionnalites;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idfonctionnalite", unique=true, nullable=false)
    public Integer getIdfonctionnalite() {
        return this.idfonctionnalite;
    }
    
    public void setIdfonctionnalite(Integer idfonctionnalite) {
        this.idfonctionnalite = idfonctionnalite;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idfonctionnalite_parente")
    public Fonctionnalite getFonctionnalite() {
        return this.fonctionnalite;
    }
    
    public void setFonctionnalite(Fonctionnalite fonctionnalite) {
        this.fonctionnalite = fonctionnalite;
    }

    
    @Column(name="code", length=45)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    
    @Column(name="nom")
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    
    @Column(name="servlet_name")
    public String getServletName() {
        return this.servletName;
    }
    
    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="fonctionnalite")
    public Set<UtilisateurProfilFonctionnalite> getUtilisateurProfilFonctionnalites() {
        return this.utilisateurProfilFonctionnalites;
    }
    
    public void setUtilisateurProfilFonctionnalites(Set<UtilisateurProfilFonctionnalite> utilisateurProfilFonctionnalites) {
        this.utilisateurProfilFonctionnalites = utilisateurProfilFonctionnalites;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="fonctionnalite")
    public Set<Fonctionnalite> getFonctionnalites() {
        return this.fonctionnalites;
    }
    
    public void setFonctionnalites(Set<Fonctionnalite> fonctionnalites) {
        this.fonctionnalites = fonctionnalites;
    }




}


