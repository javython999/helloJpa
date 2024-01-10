package jpql.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {

    private String clientname;
    private int age;

    public ClientDTO(String clientname, int age) {
        this.clientname = clientname;
        this.age = age;
    }
}
