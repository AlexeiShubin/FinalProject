package project.hibernate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "medicine")
@Getter
@Setter
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "doctor_prescription")
    private boolean doctorPrescription;

    @Column(name = "availability")
    private boolean availability;

    @Column(name = "price")
    private double price;

}
