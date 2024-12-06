package uz.pdp.Doctor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.Doctor.dto.RatingDTO;
import uz.pdp.Doctor.enums.RatingType;
import uz.pdp.Doctor.repository.DoctorRepo;
import uz.pdp.Doctor.service.ProductService;
import uz.pdp.Doctor.service.RatingService;

import java.util.Optional;

@RestController
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService, ProductService productService, DoctorRepo doctorRepo) {
        this.ratingService = ratingService;
    }

    @PostMapping("/leaveRating")
    public ResponseEntity<?> leaveRating(RatingDTO ratingDTO){
        Optional<?> create = ratingService.create(ratingDTO);
        return ResponseEntity.ok(create);
    }

    @GetMapping("/getRatingWithFromId/{fromId}")
    public ResponseEntity<Double> getRating(@PathVariable("fromId") String fromId, @RequestParam RatingType ratingType){
        Double rating = ratingService.getRating(ratingType, fromId);
        return ResponseEntity.ok(rating);
    }

    @DeleteMapping("/remove/{ratingId}")
    public ResponseEntity<?> remove(@PathVariable("ratingId") String ratingId){
        Optional<?> remove = ratingService.remove(ratingId);
        return ResponseEntity.ok(remove);
    }

}
