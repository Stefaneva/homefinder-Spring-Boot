package stefan.licenta.homefinder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "anunturi")
public class Ad {

    @Id
    @Column(name = "idanunt")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long adId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "iduser", nullable = false,
            foreignKey = @ForeignKey(name = "fk_iduser"))
    private User userId;

    @Column(name = "tipanunt")
    private String adType;

    @Column(name = "tiptranzactie")
    private String adTranzactionType;

    @Column(name = "titlu")
    private String title;

    @Column(name = "nrcamere")
    private Integer roomNumber;

    @Column(name = "suprafata")
    private Integer surface;

    @Column(name = "pret")
    private Integer price;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "descriere")
    private String description;
    //For sorting date of ads
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date date;

    @Column(name = "suprafatateren")
    private Integer areaSurface;

    @Column(name = "confort")
    private Integer comfort;

    @Column(name = "etaj")
    private String floorLevel;

    @Column(name = "compartimentare")
    private String partitioning;

    @Column(name = "an_constructie")
    private Integer yearBuilt;

    @Column(name = "tip_imobil")
    private String furnished;

    @JsonIgnore
    @OneToMany(mappedBy = "ad",cascade ={CascadeType.ALL}, orphanRemoval = true)
    private Set<Favorite> favorites;
}
