package com.application.ProgramX.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CATEGORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplyCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long CategoryID;
    @Column(nullable = false, unique = true)
    String CategoryName;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "supplyCategory", cascade = CascadeType.ALL)
    List<Supply> supplies;
}
