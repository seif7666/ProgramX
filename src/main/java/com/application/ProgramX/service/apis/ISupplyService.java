package com.application.ProgramX.service.apis;

import com.application.ProgramX.service.dtos.SupplyCategoryDTO;
import com.application.ProgramX.service.dtos.SupplyDTO;
import com.application.ProgramX.service.responses.ServiceResponse;

import java.util.List;

public interface ISupplyService {

    /**
     * Check that there is no supply having same name.
     * Less than 0 exception is done on UI side
     */
    ServiceResponse create(SupplyDTO supply);

    /**
     * Check that new supply name is unique.
     */
    ServiceResponse update(SupplyDTO supply);

    /**
     * Delete the supply
     */
    ServiceResponse delete(SupplyDTO supply);
    List<SupplyDTO> getSupplies();
    List<SupplyCategoryDTO> getCategories();
    List<SupplyDTO> getSuppliesByCategory(SupplyCategoryDTO categoryDTO);

    ServiceResponse openBag(SupplyDTO supplyDTO);
}
