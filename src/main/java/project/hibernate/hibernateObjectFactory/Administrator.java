package project.hibernate.hibernateObjectFactory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "administrator")
@Getter
@Setter
public class Administrator {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;
}
