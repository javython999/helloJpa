package jpql.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_ID")
    private Long id;

    private int jobAmount;

    @Embedded
    private Office office;

    @ManyToOne
    @JoinColumn(name = "DESK_ID")
    private Desk desk;

}
