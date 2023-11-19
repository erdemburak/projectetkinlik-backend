package com.techcareer.projectetkinlik.business.services;

import com.techcareer.projectetkinlik.business.dao.EtkinlikDao;
import com.techcareer.projectetkinlik.business.data.entity.EtkinlikEntity;
import com.techcareer.projectetkinlik.business.data.entity.EtkinlikResimEntity;
import com.techcareer.projectetkinlik.business.data.entity.EtkinlikUcretiEntity;
import com.techcareer.projectetkinlik.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/etkinlik")
public class EtkinlikRestController {

    @Autowired
    EtkinlikDao etkinlikDao;

    // LIST
    // http://localhost:8080/api/v1/etkinlik/list
    @GetMapping("/list")
    public List<EtkinlikEntity> getAllEtkinlik() {
        return etkinlikDao.findAll();
    }

    // Save
    // http://localhost:8080/api/v1/etkinlik/create
    @PostMapping("/create")
    public ResponseEntity<String> createEtkinlik(@RequestBody EtkinlikEntity etkinlik) {
        try {
            EtkinlikEntity newEtkinlik = etkinlik;

            // JSON verisini Java nesnesine çevirme
            // Etkinlik ücretlerini oluşturun ve ilişkilendirin

            if (!etkinlik.getEtkinlikUcretleri().isEmpty()) {
                List<EtkinlikUcretiEntity> newEtkinlikUcretleri = new ArrayList<>();
                for (int i =0; i< etkinlik.getEtkinlikUcretleri().toArray().length ;i++) {
                    EtkinlikUcretiEntity ucretler = new EtkinlikUcretiEntity();
                    ucretler.setEtkinlik(etkinlik);
                    ucretler.setKategoriType(etkinlik.getEtkinlikUcretleri().get(i).getKategoriType());
                    ucretler.setFiyat(etkinlik.getEtkinlikUcretleri().get(i).getFiyat());
                    newEtkinlikUcretleri.add(ucretler);
                }
                newEtkinlik.setEtkinlikUcretleri(newEtkinlikUcretleri);
            } else {
                System.out.println("Liste boş");
            }


            // Etkinlik resimlerini oluşturun ve ilişkilendirin
            List<EtkinlikResimEntity> etkinlikResimleri = new ArrayList<>();
            for (EtkinlikResimEntity resim : etkinlik.getEtkinlikResimleri()) {
                resim.setEtkinlik(etkinlik);
                etkinlikResimleri.add(resim);
            }
            newEtkinlik.setEtkinlikResimleri(etkinlikResimleri);

            // Etkinliği kaydedin
            etkinlikDao.save(newEtkinlik);
            // Burada gerekli işlemleri gerçekleştirin (örneğin, etkinliği veritabanına kaydetme)

            return new ResponseEntity<>("Etkinlik başarıyla oluşturuldu.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Etkinlik oluşturma işlemi başarısız. Hata: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Find
    // http://localhost:8080/api/v1/etkinlik/find/id
    @GetMapping("/find/{id}")
    public EtkinlikEntity getEtkinlikById(@PathVariable Long id) throws Throwable {

        return etkinlikDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not exist with id --> " + id));

    }
    // Update
    // http://localhost:8080/api/v1/etkinlik/update/id
    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<String> updateEtkinlik(@RequestBody EtkinlikEntity yeniEtkinlik , @PathVariable Long id) throws Throwable {
        try {
            // Veritabanından etkinliği al
            Optional<EtkinlikEntity> existingEtkinlikOptional = etkinlikDao.findById(id);



            if (existingEtkinlikOptional.isPresent()) {
                EtkinlikEntity existingEtkinlik = existingEtkinlikOptional.get();
                for (EtkinlikUcretiEntity eskiUcret : existingEtkinlik.getEtkinlikUcretleri()) {
                    eskiUcret.setEtkinlik(null);
                }
                for (EtkinlikResimEntity eskiResim : existingEtkinlik.getEtkinlikResimleri()) {
                    eskiResim.setEtkinlik(null);
                }

                // Etkinlik özelliklerini güncelle
                existingEtkinlik.setAd(yeniEtkinlik.getAd());
                existingEtkinlik.setAciklama(yeniEtkinlik.getAciklama());
                existingEtkinlik.setAdres(yeniEtkinlik.getAdres());
                existingEtkinlik.setKonumAdi(yeniEtkinlik.getKonumAdi());
                existingEtkinlik.setEtkinlikBaslangic(yeniEtkinlik.getEtkinlikBaslangic());
                existingEtkinlik.setEtkinlikType(yeniEtkinlik.getEtkinlikType());
                existingEtkinlik.setEtkinlikBitis(yeniEtkinlik.getEtkinlikBitis());

                // Diğer özellikleri de güncelle

                // Etkinlik ücretlerini güncelle
                List<EtkinlikUcretiEntity> updatedUcretler = new ArrayList<>();
                for (EtkinlikUcretiEntity ucret : yeniEtkinlik.getEtkinlikUcretleri()) {
                    ucret.setEtkinlik(existingEtkinlik);
                    updatedUcretler.add(ucret);
                }
                existingEtkinlik.setEtkinlikUcretleri(updatedUcretler);

                // Etkinlik resimlerini güncelle
                List<EtkinlikResimEntity> updatedResimler = new ArrayList<>();
                for (EtkinlikResimEntity resim : yeniEtkinlik.getEtkinlikResimleri()) {
                    resim.setEtkinlik(existingEtkinlik);
                    updatedResimler.add(resim);
                }
                existingEtkinlik.setEtkinlikResimleri(updatedResimler);

                // Güncellenmiş etkinliği kaydet
                etkinlikDao.save(existingEtkinlik);

                return new ResponseEntity<>("Etkinlik başarıyla güncellendi.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Belirtilen ID'ye sahip etkinlik bulunamadı.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Etkinlik güncelleme işlemi başarısız. Hata: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Delete
    // http://localhost:8080/api/v1/etkinlik/delete/id
    @DeleteMapping("/delete/{id}")
    public Boolean deleteEtkinlik(@PathVariable(name = "id") Long id) throws Throwable {
        etkinlikDao.deleteById(id);
        return true;
    }

}