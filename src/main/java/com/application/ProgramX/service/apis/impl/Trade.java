package com.application.ProgramX.service.apis.impl;

import com.application.ProgramX.model.entities.SupplyCategoryEntity;
import com.application.ProgramX.model.entities.SupplyEntity;
import com.application.ProgramX.model.entities.trading.TradedSupplyEntity;
import com.application.ProgramX.model.repository.DAOPool;
import com.application.ProgramX.model.repository.TradeRepository;
import com.application.ProgramX.service.apis.ITradeService;
import com.application.ProgramX.service.dtos.SupplyDTO;
import com.application.ProgramX.service.dtos.trading.TradedSupplyDTO;
import com.application.ProgramX.service.message.MessageRetriever;
import com.application.ProgramX.service.responses.ServiceResponse;
import com.application.ProgramX.service.responses.commands.DecisionCommand;
import com.application.ProgramX.service.responses.dialogs.DecisionDialogue;
import com.application.ProgramX.service.responses.dialogs.ErrorDialogue;
import com.application.ProgramX.service.responses.dialogs.IDialogue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class Trade implements ITradeService {

    @Autowired
    MessageRetriever retriever;
    @Autowired
    DAOPool pool;
    @Override
    public ServiceResponse trade(List<TradedSupplyDTO> tradedSupplyDTOList) {
        List<TradedSupplyEntity> entities=new LinkedList<>();
        for(TradedSupplyDTO dto : tradedSupplyDTOList) {
            TradedSupplyEntity entity=new TradedSupplyEntity();
            SupplyDTO supplyDTO= dto.getSupplyEntity();
            SupplyEntity supplyEntity= SupplyEntity.builder()
                    .SupplyID(supplyDTO.getSupplyID())
                    .supplyCategory(
                            SupplyCategoryEntity.builder()
                                    .CategoryName(supplyDTO.getSupplyCategory().getCategoryName())
                                    .CategoryID(supplyDTO.getSupplyCategory().getCategoryID())
                                    .build()
                    )
                    .SupplyName(supplyDTO.getSupplyName())
                    .numberOfBags(supplyDTO.getNumberOfBags())
                    .quantity(supplyDTO.getQuantity())
                    .pricePerBag(supplyDTO.getPricePerBag())
                    .pricePerKilo(supplyDTO.getPricePerKilo())
                    .build();
            entity.setSupply(supplyEntity);
            entity.setQuantity(dto.getQuantity());
            entity.setNumberOfBags(dto.getNumberOfBags());
            entity.setTotalPrice(dto.getTotalPrice());
            entities.add(entity);
        }
        TradeRepository repository= new TradeRepository(pool,entities);
        ServiceResponse response;
        DecisionCommand command=new DecisionCommand() {
            @Override
            public void executeOnAccept() {
                try{
                    repository.trade();
                }catch (Exception e){
                    new ErrorDialogue(e.getMessage()).executeDialogue();
                }
            }
            @Override
            public void executeOnDecline() {
            }
        };
        IDialogue dialogue= new DecisionDialogue(command, retriever.getMessage().getTradedSupplyMessage().completePayment(),retriever.getMessage().getTradedSupplyMessage().paymentCompleted());
        response=new ServiceResponse(dialogue);
        return  response;
    }

    @Override
    public float getTotalPrice(List<TradedSupplyDTO> tradedSupplyDTOList) {
        return 0;
    }
}
