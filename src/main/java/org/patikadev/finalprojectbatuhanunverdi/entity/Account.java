package org.patikadev.finalprojectbatuhanunverdi.entity;

import lombok.Getter;
import lombok.Setter;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.AccountCurrency;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.AccountType;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Mert Batuhan UNVERDI
 * @since 16.05.2022
 */
@Entity
@Getter
@Setter
@Table
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String IBAN;
    @Enumerated
    private AccountType accountType;
    private BigDecimal balance;
    @Enumerated
    private AccountCurrency accountCurrency;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

}
