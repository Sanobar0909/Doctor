package uz.pdp.Doctor.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.Doctor.dto.CategoryDTO;
import uz.pdp.Doctor.model.Category;
import uz.pdp.Doctor.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/create_category", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Category> create(CategoryDTO categoryDTO, @RequestParam("files")MultipartFile file){
        Category category = categoryService.create(categoryDTO, file);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/getByName")
    public ResponseEntity<Category> getByName(@RequestParam("name") String name){
        Category byName = categoryService.getByName(name);
        return ResponseEntity.ok(byName);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Category> get(@PathVariable("id") String id){
        Category category = categoryService.get(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Category>> getAll(){
        List<Category> categories = categoryService.allCategory();
        return ResponseEntity.ok(categories);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Category> update(@PathVariable("id") String id, CategoryDTO categoryDTO, @RequestParam("files") MultipartFile files){
        Category update = categoryService.update(id, categoryDTO, files);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") String id){
        categoryService.remove(id);
        return ResponseEntity.ok("Deleted appointment");
    }
}
