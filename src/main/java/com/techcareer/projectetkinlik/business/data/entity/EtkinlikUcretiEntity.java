package com.techcareer.projectetkinlik.business.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.techcareer.projectetkinlik.business.data.model.KATEGORI_TYPE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "etkinlik_ucreti")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EtkinlikUcretiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etkinlik_id")
    @JsonBackReference
    private EtkinlikEntity etkinlik;

    @Column(name = "kategori" )
    @Enumerated(EnumType.STRING)
    private KATEGORI_TYPE kategoriType;

    @Column(name = "fiyat")
    private double fiyat;
}
