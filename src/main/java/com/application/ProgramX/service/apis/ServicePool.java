package com.application.ProgramX.service.apis;

import com.application.ProgramX.service.apis.ICategoryService;
import com.application.ProgramX.service.apis.ISupplyService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class ServicePool {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ISupplyService supplyService;
}
