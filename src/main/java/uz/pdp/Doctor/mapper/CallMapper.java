package uz.pdp.Doctor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uz.pdp.Doctor.dto.CallDTO;
import uz.pdp.Doctor.model.Call;

@Mapper
public interface CallMapper {
    CallMapper CALL_MAPPER = Mappers.getMapper(CallMapper.class);

    CallDTO toDTO(Call call);
    Call toEntity(CallDTO callDTO);
}

