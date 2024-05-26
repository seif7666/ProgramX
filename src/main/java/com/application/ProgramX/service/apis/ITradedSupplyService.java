package com.application.ProgramX.service.apis;

import com.application.ProgramX.service.dtos.SupplyDTO;
import com.application.ProgramX.service.dtos.trading.TradedSupplyDTO;
import com.application.ProgramX.service.responses.ServiceResponse;
import org.javatuples.Pair;
import org.javatuples.Tuple;

public interface ITradedSupplyService {

    /**
     * Check that the new  element.supply.bags-numberOfBags > 0
     * If yes set the supply numberOfBags to the new if trading occured.
     */
    ServiceResponse setNumberOfBags(Pair<TradedSupplyDTO,SupplyDTO>element, int numberOfBags);

    /**
     * Calculate the quantity and 3 options may occur
     * 1: The amount found is sufficient.
     * 2: The amount could be satisfied if a new bag was opened.
     * 3: The amount is not sufficient.
     */
    ServiceResponse setQuantity(Pair<TradedSupplyDTO,SupplyDTO>element, float quantity);

}
