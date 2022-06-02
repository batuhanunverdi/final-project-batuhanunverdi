package org.patikadev.finalprojectbatuhanunverdi.model;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

/**
 * @author Mert Batuhan UNVERDI
 * @since 21.05.2022
 */
@Getter
@Setter
public class ExchangeModel {
   private String base;
   private Rates rates;
   private boolean success;
   private Timestamp timestamp;


}
