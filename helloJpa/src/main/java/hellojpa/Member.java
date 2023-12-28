package hellojpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Member {
    @Id
    private Long id;

    @Column(name = "name")
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDateTime;

    @Lob
    private String description;

    @Transient
    private int temp;

    public Member() {}


}
