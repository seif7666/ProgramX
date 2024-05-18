package com.application.ProgramX.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CATEGORY")
@Data
public class SupplyCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long CategoryID;
    @Column(nullable = false, unique = true)
    String CategoryName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supplyCategory")
    Set<Supply> supplies;
}
