package com.techcareer.projectetkinlik.business.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.techcareer.projectetkinlik.business.data.model.KATEGORI_TYPE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "etkinlik_resim")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EtkinlikResimEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etkinlik_id")
    @JsonBackReference
    private EtkinlikEntity etkinlik;

    @Column(name = "resim_ad")
    private String resimAd;
}
