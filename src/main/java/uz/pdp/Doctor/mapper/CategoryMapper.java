package uz.pdp.Doctor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uz.pdp.Doctor.dto.CategoryDTO;
import uz.pdp.Doctor.dto.ProductDTO;
import uz.pdp.Doctor.model.Category;
import uz.pdp.Doctor.model.Product;

@Mapper
public interface CategoryMapper {
    CategoryMapper CATEGORY_MAPPER = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toDTO(Category category);
    Category toEntity(CategoryDTO categoryDTO);
}
