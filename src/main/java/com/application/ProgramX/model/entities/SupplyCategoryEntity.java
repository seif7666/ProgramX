package com.application.ProgramX.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "CATEGORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplyCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long CategoryID;
    @Column(nullable = false, unique = true)
    String CategoryName;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "supplyCategory", cascade = CascadeType.ALL)
    List<SupplyEntity> supplies;
}
