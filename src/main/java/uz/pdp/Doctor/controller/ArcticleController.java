package uz.pdp.Doctor.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.Doctor.dto.ArcticleDTO;
import uz.pdp.Doctor.model.Arcticle;
import uz.pdp.Doctor.service.ArcticleService;

import java.util.List;

@RestController
@RequestMapping("/onlinePharamcy")
public class ArcticleController {

    private final ArcticleService arcticleService;

    public ArcticleController(ArcticleService arcticleService) {
        this.arcticleService = arcticleService;
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> create(ArcticleDTO arcticleDTO, @PathVariable("files") MultipartFile files){
        String get = arcticleService.create(arcticleDTO, files);
        return ResponseEntity.ok(get);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Arcticle>> getAll(){
        List<Arcticle> arcticleList = arcticleService.getAll();
        return ResponseEntity.ok(arcticleList);
    }

    @GetMapping("/getAllByReytingStar")
    public ResponseEntity<List<Arcticle>> getAllByReytingStar(@RequestParam("star") float star){
        List<Arcticle> allByReytingStar = arcticleService.getAllByReytingStar(star);
        return ResponseEntity.ok(allByReytingStar);
    }
}
