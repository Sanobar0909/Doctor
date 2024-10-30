package uz.pdp.Doctor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.Doctor.dto.ArticleDTO;
import uz.pdp.Doctor.model.Article;
import uz.pdp.Doctor.model.Doctor;
import uz.pdp.Doctor.model.Files;
import uz.pdp.Doctor.repository.ArticleRepo;
import uz.pdp.Doctor.repository.DoctorRepo;
import uz.pdp.Doctor.repository.FilesRepo;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ArticleService {private final FilesRepo filesRepo;
    private final ArticleRepo articleRepo;
    private final DoctorRepo doctorRepo;
    private final S3StorageService s3StorageService;
    private final String AWS_PUBLIC = "public";
    private final String AWS_URL = "https://sanobar.s3.ap-northeast-1.amazonaws.com/";

    public String create(ArticleDTO arcticleDTO, MultipartFile file){
        Doctor doctor = doctorRepo.findById(arcticleDTO.author()).get();
        Article build = Article.builder().author(doctor)
                .name(arcticleDTO.name())
                .desc(arcticleDTO.desc())
                .date(LocalDate.now()).build();
        Files files = s3StorageService.saveImage(file, AWS_PUBLIC);
        files.setUrl(AWS_URL + files.getPath());
        Files savedFile = filesRepo.save(files);
        build.setImage(savedFile);
        articleRepo.save(build);
        return "Successfully posted arcticle";
    }

    public String update(String articleId, ArticleDTO arcticleDTO, MultipartFile file){
        Doctor doctor = doctorRepo.findById(arcticleDTO.author()).get();
        Files files = s3StorageService.saveImage(file, AWS_PUBLIC);
        files.setUrl(AWS_URL + files.getPath());
        Files savedFile = filesRepo.save(files);
        Article arcticle = articleRepo.findById(articleId).get();
        arcticle.setAuthor(doctor);
        arcticle.setName(arcticleDTO.name());
        arcticle.setImage(files);
        arcticle.setImage(savedFile);
        articleRepo.save(arcticle);
        return "Successfully posted arcticle";
    }

    public String remove(String arcticleId){
        Article arcticle = articleRepo.findById(arcticleId).get();
        filesRepo.delete(arcticle.getImage());
        articleRepo.delete(arcticle);
        return "Remove posted arcticle";
    }
}
