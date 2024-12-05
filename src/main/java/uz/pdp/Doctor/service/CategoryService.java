package uz.pdp.Doctor.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.Doctor.dto.CategoryDTO;
import uz.pdp.Doctor.mapper.CategoryMapper;
import uz.pdp.Doctor.model.Category;
import uz.pdp.Doctor.model.Files;
import uz.pdp.Doctor.repository.CategotyRepo;
import uz.pdp.Doctor.repository.FilesRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategotyRepo categotyRepo;
    private final FilesRepo filesRepo;
    private final S3StorageService s3StorageService;
    private final String AWS_PUBLIC = "public";
    private final String AWS_URL = "https://medicsg40website.s3.ap-northeast-1.amazonaws.com/";

    public Category create(CategoryDTO categoryDTO, MultipartFile file){
        Category entity = CategoryMapper.CATEGORY_MAPPER.toEntity(categoryDTO);
        Files files = s3StorageService.save(file, AWS_PUBLIC);
        files.setUrl(AWS_URL + files.getPath());
        Files savedFile = filesRepo.save(files);
        entity.setIcon(savedFile);
        return categotyRepo.save(entity);
    }

    public Category getByName(String name){
        Category category = categotyRepo.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category name"));
        return category;
    }

    public Category get(String id){
        Category category = categotyRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id"));
        return category;
    }

    public Category update(String id, CategoryDTO categoryDTO, MultipartFile file){
        Category category = categotyRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id"));
        category.setName(categoryDTO.name());
        Files files = s3StorageService.save(file, AWS_PUBLIC);
        files.setUrl(AWS_URL + files.getPath());
        Files savedFile = filesRepo.save(files);
        category.setIcon(savedFile);
        return categotyRepo.save(category);
    }

    public List<Category> allCategory(){
        List<Category> all = categotyRepo.findAll();
        return all;
    }

    public void remove(String id){
        Category category = categotyRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id"));
        categotyRepo.delete(category);
    }
}
