package project.hibernate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role{

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "role_name")
    private String role;
}
