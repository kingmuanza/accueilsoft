package soft.accueil.entities;
// Generated 16 janv. 2018 13:43:22 by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Urgence generated by hbm2java
 */
@Entity
@Table(name="urgence"
    ,catalog="accueilsoft"
)
public class Urgence  implements java.io.Serializable {


     private Integer idurgence;
     private Employe employe;
     private Date date;
     private Boolean resolue;
     private String statut;

    public Urgence() {
    }

    public Urgence(Employe employe, Date date, Boolean resolue, String statut) {
       this.employe = employe;
       this.date = date;
       this.resolue = resolue;
       this.statut = statut;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idurgence", unique=true, nullable=false)
    public Integer getIdurgence() {
        return this.idurgence;
    }
    
    public void setIdurgence(Integer idurgence) {
        this.idurgence = idurgence;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idemploye")
    public Employe getEmploye() {
        return this.employe;
    }
    
    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date", length=19)
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    
    @Column(name="resolue")
    public Boolean getResolue() {
        return this.resolue;
    }
    
    public void setResolue(Boolean resolue) {
        this.resolue = resolue;
    }

    
    @Column(name="statut", length=45)
    public String getStatut() {
        return this.statut;
    }
    
    public void setStatut(String statut) {
        this.statut = statut;
    }




}

