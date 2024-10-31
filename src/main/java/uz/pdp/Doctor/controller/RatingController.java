package uz.pdp.Doctor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.Doctor.dto.RatingDTO;
import uz.pdp.Doctor.service.RatingService;

import java.util.Optional;

@RestController
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/leaveRating")
    public ResponseEntity<?> leaveRating(RatingDTO ratingDTO){
        Optional<?> create = ratingService.create(ratingDTO);
        return ResponseEntity.ok(create);
    }

    @DeleteMapping("/remove/{ratingId}")
    public ResponseEntity<?> remove(@PathVariable("ratingId") String ratingId){
        Optional<?> remove = ratingService.remove(ratingId);
        return ResponseEntity.ok(remove);
    }

}
