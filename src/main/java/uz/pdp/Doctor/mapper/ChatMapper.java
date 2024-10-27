package uz.pdp.Doctor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uz.pdp.Doctor.dto.ChatDTO;
import uz.pdp.Doctor.model.Chat;

@Mapper
public interface ChatMapper {
    ChatMapper CHAT_MAPPER = Mappers.getMapper(ChatMapper.class);

    ChatDTO toDTO(Chat chat);
    Chat toEntity(ChatDTO chatDTO);
}
