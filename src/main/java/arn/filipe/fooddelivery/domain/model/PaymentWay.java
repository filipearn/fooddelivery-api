package arn.filipe.fooddelivery.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PaymentWay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;
}
