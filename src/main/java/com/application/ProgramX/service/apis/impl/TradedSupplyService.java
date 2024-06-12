package com.application.ProgramX.service.apis.impl;

import com.application.ProgramX.service.apis.ITradedSupplyService;
import com.application.ProgramX.service.dtos.SupplyDTO;
import com.application.ProgramX.service.dtos.trading.TradedSupplyDTO;
import com.application.ProgramX.service.message.MessageRetriever;
import com.application.ProgramX.service.responses.ServiceResponse;
import com.application.ProgramX.service.responses.commands.DecisionCommand;
import com.application.ProgramX.service.responses.dialogs.DecisionDialogue;
import com.application.ProgramX.service.responses.dialogs.EmptyDialogue;
import com.application.ProgramX.service.responses.dialogs.ErrorDialogue;
import com.application.ProgramX.service.responses.dialogs.IDialogue;
import lombok.extern.java.Log;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class TradedSupplyService implements ITradedSupplyService {
    @Autowired
    private MessageRetriever retriever;

    @Override
    public ServiceResponse setNumberOfBags(Pair<TradedSupplyDTO, SupplyDTO> element, int numberOfBags) {
        SupplyDTO supplyDTO= element.getValue1();
        TradedSupplyDTO tradedSupplyDTO=element.getValue0();
        int addedBagFromQuantity= tradedSupplyDTO.isNewBagOpened() ? 1 : 0;
        IDialogue dialogue;
        if(numberOfBags > supplyDTO.getNumberOfBags())
            dialogue=new ErrorDialogue(retriever.getMessage().getTradedSupplyMessage().youHaveInsufficientNumberOfBags());
        else if(numberOfBags+addedBagFromQuantity > supplyDTO.getNumberOfBags())
            dialogue=new ErrorDialogue(retriever.getMessage().getTradedSupplyMessage().totalAmountIsNotValid());
        else {
            tradedSupplyDTO.setNumberOfBags(numberOfBags);
            setSupplyBags(tradedSupplyDTO,supplyDTO.getNumberOfBags());
            dialogue=new EmptyDialogue();
        }
        return new ServiceResponse(dialogue);
    }

    private void setSupplyBags(TradedSupplyDTO tradedSupplyDTO, int currentBags) {
        tradedSupplyDTO.getSupplyEntity().setNumberOfBags(currentBags-tradedSupplyDTO.getNumberOfBags()-(tradedSupplyDTO.isNewBagOpened()? 1: 0));
    }

    @Override
    public ServiceResponse setQuantity(Pair<TradedSupplyDTO,SupplyDTO> element, float quantity) {
        SupplyDTO supplyDTO= element.getValue1();
        TradedSupplyDTO tradedSupplyDTO= element.getValue0();
        float currentQuantity= supplyDTO.getQuantity();
        int previous= tradedSupplyDTO.isNewBagOpened() ? 1: 0;
        if(quantity<=currentQuantity){
            tradedSupplyDTO.setQuantity(quantity);
            tradedSupplyDTO.getSupplyEntity().setQuantity(tradedSupplyDTO.getSupplyEntity().getQuantity()-quantity);
            return new ServiceResponse(new EmptyDialogue());
        }
        if(tradedSupplyDTO.getNumberOfBags()==0){
            return new ServiceResponse(new ErrorDialogue(this.retriever.getMessage().getTradedSupplyMessage().insufficientQuantity()));
        }
        boolean hasExtraBags= tradedSupplyDTO.getSupplyEntity().getNumberOfBags()+previous>0;
        if(!hasExtraBags)
            return  new ServiceResponse(new ErrorDialogue(this.retriever.getMessage().getTradedSupplyMessage().totalAmountIsNotValid()));
        if(quantity-currentQuantity >=25.0f){
            return new ServiceResponse(new ErrorDialogue(this.retriever.getMessage().getTradedSupplyMessage().thisNeedsOpeningMoreThanOneBag()));
        }
        DecisionCommand command = new AddBagCommand(element,quantity);
        return new ServiceResponse(
                new DecisionDialogue(
                        command,
                        this.retriever.getMessage().getTradedSupplyMessage().thereIsNoSufficientQuantityDoYouWantToOpenNewBag(),
                        this.retriever.getMessage().getTradedSupplyMessage().newBagWasOpened()
                )
        );
    }

    private record AddBagCommand(Pair<TradedSupplyDTO,SupplyDTO>element,float quantity) implements DecisionCommand{

        @Override
        public void executeOnAccept() {
            SupplyDTO supplyDTO= element.getValue1();
            TradedSupplyDTO tradedSupplyDTO= element.getValue0();
            int previous= tradedSupplyDTO.isNewBagOpened() ? 1: 0;
            int currentAvailableBags= tradedSupplyDTO.getSupplyEntity().getNumberOfBags() + previous;
            tradedSupplyDTO.getSupplyEntity().setNumberOfBags(1+currentAvailableBags);
            tradedSupplyDTO.setNewBagOpened(true);
            tradedSupplyDTO.getSupplyEntity().setQuantity(supplyDTO.getQuantity()+25.0f-quantity);
            tradedSupplyDTO.setQuantity(quantity);
        }

        @Override
        public void executeOnDecline() {

        }
    }
}
