package jpql.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "company")
@NamedQuery(
        name = "Client.findByClientname",
        query = "select c from Client c where c.clientname = :clientname"
)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIENT_ID")
    private Long id;
    private String clientname;
    private int age;

    @Enumerated(EnumType.STRING)
    private ClientType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    public void changeCompany(Company company) {
        this.company = company;
        company.getClients().add(this);
    }

}
