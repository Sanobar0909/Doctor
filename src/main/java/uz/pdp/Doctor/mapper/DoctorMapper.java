package uz.pdp.Doctor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uz.pdp.Doctor.dto.DoctorDTO;
import uz.pdp.Doctor.model.Doctor;

@Mapper
public interface DoctorMapper {
    DoctorMapper DOCTOR_MAPPER = Mappers.getMapper(DoctorMapper.class);

    DoctorDTO toDTO(Doctor Doctor);
    Doctor toEntity(DoctorDTO DoctorDTO);
}
