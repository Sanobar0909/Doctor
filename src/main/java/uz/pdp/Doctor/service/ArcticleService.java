package uz.pdp.Doctor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.Doctor.dto.ArcticleDTO;
import uz.pdp.Doctor.model.Arcticle;
import uz.pdp.Doctor.model.Doctor;
import uz.pdp.Doctor.model.Files;
import uz.pdp.Doctor.repository.ArcticleRepo;
import uz.pdp.Doctor.repository.DoctorRepo;
import uz.pdp.Doctor.repository.FilesRepo;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ArcticleService {
    private final FilesRepo filesRepo;
    private final ArcticleRepo arcticleRepo;
    private final DoctorRepo doctorRepo;
    private final S3StorageService s3StorageService;
    private final String AWS_PUBLIC = "public";
    private final String AWS_URL = "https://sanobar.s3.ap-northeast-1.amazonaws.com/";

    public String create(ArcticleDTO arcticleDTO, MultipartFile file){
        Doctor doctor = doctorRepo.findById(arcticleDTO.author()).get();
        Arcticle build = Arcticle.builder().author(doctor)
                .name(arcticleDTO.name())
                .desc(arcticleDTO.desc())
                .date(LocalDate.now()).build();
        Files files = s3StorageService.save(file, AWS_PUBLIC);
        files.setUrl(AWS_URL + files.getPath());
        Files savedFile = filesRepo.save(files);
        build.setImage(savedFile);
        arcticleRepo.save(build);
        return "Successfully posted arcticle";
    }

    public String update(String arcticleId, ArcticleDTO arcticleDTO, MultipartFile file){
        Doctor doctor = doctorRepo.findById(arcticleDTO.author()).get();
        Files files = s3StorageService.save(file, AWS_PUBLIC);
        files.setUrl(AWS_URL + files.getPath());
        Files savedFile = filesRepo.save(files);
        Arcticle arcticle = arcticleRepo.findById(arcticleId).get();
        arcticle.setAuthor(doctor);
        arcticle.setName(arcticleDTO.name());
        arcticle.setImage(files);
        arcticle.setImage(savedFile);
        arcticleRepo.save(arcticle);
        return "Successfully posted arcticle";
    }

    public String remove(String arcticleId){
        Arcticle arcticle = arcticleRepo.findById(arcticleId).get();
        filesRepo.delete(arcticle.getImage());
        arcticleRepo.delete(arcticle);
        return "Remove posted arcticle";
    }
}
