package com.application.ProgramX.service.apis;

import com.application.ProgramX.service.dtos.trading.TradedSupplyDTO;
import com.application.ProgramX.service.responses.ServiceResponse;

import java.util.List;

public interface ITradeService {
    ServiceResponse trade(List<TradedSupplyDTO> tradedSupplyDTOList);
    float getTotalPrice(List<TradedSupplyDTO> tradedSupplyDTOList);
}
