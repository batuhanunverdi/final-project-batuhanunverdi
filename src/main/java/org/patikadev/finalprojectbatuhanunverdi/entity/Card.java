package org.patikadev.finalprojectbatuhanunverdi.entity;

import lombok.Getter;
import lombok.Setter;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.CardType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Mert Batuhan UNVERDI
 * @since 16.05.2022
 */
@Entity
@Getter
@Setter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cardNumber;
    private int cardPassword;
    @OneToOne
    private Account account;
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    private int cvv;
    private Date expirationDate;
    private BigDecimal cardLimit;

}
