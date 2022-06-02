package org.patikadev.finalprojectbatuhanunverdi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Mert Batuhan UNVERDI
 * @since 21.05.2022
 */
@Getter
@Setter
@Entity
public class TransferLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String senderIBan;
    private String receiverIBan;
    private String receiverName;
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendingTime;
    private BigDecimal balance;
}
