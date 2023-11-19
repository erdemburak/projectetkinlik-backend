package com.techcareer.projectetkinlik.business.data.entity;

import com.techcareer.projectetkinlik.business.data.model.ETKINLIK_TYPE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "etkinlikentity")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EtkinlikEntity {

    @Id
    @Column(name = "etkinlik_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ad", nullable = false)
    private String ad;

    @Column(name = "aciklama", nullable = false)
    private String aciklama;

    @Column(name = "konum_adi", nullable = false)
    private String konumAdi;

    @Column(name = "adres", nullable = false)
    private String adres;

    @Column(name = "etkinlik_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ETKINLIK_TYPE etkinlikType;

    @Column(name = "baslangic_tarihi")
    private LocalDateTime etkinlikBaslangic;

    @Column(name = "bitis_tarihi")
    private LocalDateTime etkinlikBitis;

    @OneToMany(mappedBy = "etkinlik", cascade = CascadeType.ALL, targetEntity = EtkinlikUcretiEntity.class )
    private List<EtkinlikUcretiEntity> etkinlikUcretleri;

    @OneToMany(mappedBy = "etkinlik", cascade = CascadeType.ALL, targetEntity = EtkinlikResimEntity.class)
    private List<EtkinlikResimEntity> etkinlikResimleri;
}
