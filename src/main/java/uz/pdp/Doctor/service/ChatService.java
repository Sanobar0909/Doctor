package uz.pdp.Doctor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.Doctor.dto.ChatDTO;
import uz.pdp.Doctor.dto.MessageDTO;
import uz.pdp.Doctor.enums.MessageType;
import uz.pdp.Doctor.mapper.CallMapper;
import uz.pdp.Doctor.mapper.ChatMapper;
import uz.pdp.Doctor.model.Call;
import uz.pdp.Doctor.model.Chat;
import uz.pdp.Doctor.model.Files;
import uz.pdp.Doctor.model.Message;
import uz.pdp.Doctor.repository.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepo chatRepo;
    private final MessageRepo messageRepo;
    private final CallRepo callRepo;
    private final DoctorRepo doctorRepo;
    private final S3StorageService s3StorageService;
    private final FilesRepo filesRepo;
    private final String AWS_PUBLIC = "public";
    private final String AWS_URL = "https://medicsg40website.s3.ap-northeast-1.amazonaws.com/";

    public Chat createChat(ChatDTO chatDTO){
        Chat entity = ChatMapper.CHAT_MAPPER.toEntity(chatDTO);
        return chatRepo.save(entity);
    }

    public Message sendMessage(MessageDTO messageDTO, MultipartFile file){
        Chat chat = chatRepo.findById(messageDTO.chatId())
                .orElseThrow(() -> new IllegalArgumentException("Chat not found with id: " + messageDTO.chatId()));
        switch (messageDTO.messageType()){
            case MESSAGE -> {
                Message build = Message.builder().chat(chat)
                        .for_id(messageDTO.for_id())
                        .from_id(messageDTO.from_id())
                        .messageType(MessageType.MESSAGE)
                        .time(LocalTime.now())
                        .date(LocalDate.now()).build();
                return messageRepo.save(build);
            }
            case CALL -> {
                Call entity = CallMapper.CALL_MAPPER.toEntity(messageDTO.callDTO());
                entity.setChat(chat);
                Call save = callRepo.save(entity);
                Message build = Message.builder().chat(save.getChat())
                        .call(save)
                        .date(LocalDate.now())
                        .time(LocalTime.now())
                        .for_id(messageDTO.for_id())
                        .from_id(messageDTO.from_id())
                        .messageType(MessageType.CALL).build();
                return messageRepo.save(build);
            }
            case FILE -> {
                Files files = s3StorageService.save(file,AWS_PUBLIC);
                files.setUrl(AWS_URL + files.getPath());
                Files save = filesRepo.save(files);
                Message build = Message.builder().chat(chat)
                        .messageType(MessageType.FILE)
                        .for_id(messageDTO.for_id())
                        .from_id(messageDTO.from_id())
                        .date(LocalDate.now())
                        .time(LocalTime.now())
                        .files(save).build();
                return messageRepo.save(build);
            }
        }
        return null;
    }

    public boolean remove(String messageId){
        Message message = messageRepo.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found with id: " + messageId));
        switch (message.getMessageType()){
            case MESSAGE -> {
                messageRepo.delete(message);
                return true;
            }
            case CALL -> {
                callRepo.delete(message.getCall());
                messageRepo.delete(message);
                return true;
            }
            case FILE -> {
                filesRepo.delete(message.getFiles());
                s3StorageService.delete(message.getFiles().getPath());
                messageRepo.delete(message);
                return true;
            }
        }
        return false;
    }

    public List<Message> getAllMessageByChatId(String chatId){
        Chat chat = chatRepo.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Chat not found with id: " + chatId));
        return messageRepo.findAllByChatIs(chat);
    }
}
