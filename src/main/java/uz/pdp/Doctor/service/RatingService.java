package uz.pdp.Doctor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.Doctor.dto.RatingDTO;
import uz.pdp.Doctor.enums.RatingType;
import uz.pdp.Doctor.model.Arcticle;
import uz.pdp.Doctor.model.Doctor;
import uz.pdp.Doctor.model.Product;
import uz.pdp.Doctor.model.Rating;
import uz.pdp.Doctor.repository.ArcticleRepo;
import uz.pdp.Doctor.repository.DoctorRepo;
import uz.pdp.Doctor.repository.ProductRepo;
import uz.pdp.Doctor.repository.RatingRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepo ratingRepo;
    private final ProductRepo productRepo;
    private final DoctorRepo doctorRepo;
    private final ArcticleRepo arcticleRepo;

    public Optional<?> create(RatingDTO ratingDTO){
        switch (ratingDTO.type()){
            case MEDICINE -> {
                Product product = productRepo.findById(ratingDTO.fromId())
                        .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + ratingDTO.fromId()));
                product.setRatings(save(ratingDTO, product.getRatings()));
                productRepo.save(product);
                return Optional.of(product);
            }
            case DOCTOR -> {
                Doctor doctor = doctorRepo.findById(ratingDTO.fromId())
                        .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + ratingDTO.fromId()));
                doctor.setRatings(save(ratingDTO, doctor.getRatings()));
                doctorRepo.save(doctor);
                return Optional.of(doctor);
            }
            case ARCTICLES -> {
                Arcticle arcticle = arcticleRepo.findById(ratingDTO.fromId())
                        .orElseThrow(() -> new IllegalArgumentException("Arcticle not found with id: " + ratingDTO.fromId()));
                arcticle.setRatings(save(ratingDTO, arcticle.getRatings()));
                arcticleRepo.save(arcticle);
                return Optional.of(arcticle);
            }
        }
        return null;
    }

    public Double getRating(RatingType ratingType, String fromId){
        switch (ratingType){
            case MEDICINE ->{
                Product product = productRepo.findById(fromId)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + fromId));
                return product.calculateAverageRating();
            }
            case DOCTOR -> {
                Doctor doctor = doctorRepo.findById(fromId)
                        .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + fromId));
                return doctor.calculateAverageRating();
            }
            case ARCTICLES -> {
                Arcticle arcticle = arcticleRepo.findById(fromId)
                        .orElseThrow(() -> new IllegalArgumentException("Arcticle not found with id: " + fromId));
                return arcticle.calculateAverageRating();
            }
        }
        return null;
    }

    public Optional<?> remove(String id){
        Rating rating = ratingRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found with id: " + id));
        switch (rating.getType()) {
            case MEDICINE -> {
                Product product = productRepo.findById(rating.getProduct().getId()).get();
                product.setRatings(delete(product.getRatings(), rating));
                return Optional.of(product);
            }
            case DOCTOR -> {
                Doctor doctor = doctorRepo.findById(rating.getDoctor().getId()).get();
                doctor.setRatings(delete(doctor.getRatings(), rating));
                return Optional.of(doctor);
            }
            case ARCTICLES -> {
                Arcticle arcticle = arcticleRepo.findById(rating.getArcticle().getId()).get();
                arcticle.setRatings(delete(arcticle.getRatings(), rating));
                return Optional.of(arcticle);
            }
        }
        return null;
    }

    private List<Rating> save(RatingDTO ratingDTO, List<Rating> ratings){
        switch (ratingDTO.type()) {
            case MEDICINE -> {
                Rating build = Rating.builder().product(productRepo.findById(ratingDTO.fromId()).get())
                        .score(ratingDTO.score())
                        .type(ratingDTO.type()).build();
                ratings.add(ratingRepo.save(build));
                return ratings;
            }
            case DOCTOR -> {
                Rating build = Rating.builder().doctor(doctorRepo.findById(ratingDTO.fromId()).get())
                        .score(ratingDTO.score())
                        .type(ratingDTO.type()).build();
                ratings.add(ratingRepo.save(build));
                return ratings;
            }
            case ARCTICLES -> {
                Rating build = Rating.builder().arcticle(arcticleRepo.findById(ratingDTO.fromId()).get())
                        .score(ratingDTO.score())
                        .type(ratingDTO.type()).build();
                ratings.add(ratingRepo.save(build));
                return ratings;
            }
        }
        return null;
    }

    private List<Rating> delete(List<Rating> ratings, Rating rating){
        ratings.remove(rating);
        ratingRepo.delete(rating);
        return ratings;
    }
}
