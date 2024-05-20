package com.application.ProgramX.service.apis;

import com.application.ProgramX.service.dtos.SupplyCategoryDTO;
import com.application.ProgramX.service.responses.ServiceResponse;

import java.util.List;

public interface ICategoryService {
    /**
     * Check that there is no category having same name.
     */
     ServiceResponse create(SupplyCategoryDTO supplyCategory);

    /**
     * Check that new category name is unique.
     */
     ServiceResponse update(SupplyCategoryDTO supplyCategory);

     ServiceResponse delete(SupplyCategoryDTO supplyCategoryDTO);
     List<SupplyCategoryDTO> getCategories();
}
