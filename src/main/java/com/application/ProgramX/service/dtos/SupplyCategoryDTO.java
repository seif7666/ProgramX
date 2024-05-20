package com.application.ProgramX.service.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplyCategoryDTO {
    long CategoryID;
    String CategoryName;
    List<SupplyDTO> supplies;
}
