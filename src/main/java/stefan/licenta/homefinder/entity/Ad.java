package stefan.licenta.homefinder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
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

}
