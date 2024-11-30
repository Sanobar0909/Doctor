package uz.pdp.Doctor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.Doctor.dto.ChatDTO;
import uz.pdp.Doctor.dto.MessageDTO;
import uz.pdp.Doctor.model.Chat;
import uz.pdp.Doctor.model.Message;
import uz.pdp.Doctor.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/doctorConsultation")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/createChat")
    public ResponseEntity<Chat> createChat(ChatDTO chatDTO){
        Chat chat = chatService.createChat(chatDTO);
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/getAllChatUser")
    public ResponseEntity<List<Chat>> getAllChatUser(){
        List<Chat> chatList = chatService.getAllChatUser();
        return ResponseEntity.ok(chatList);
    }

    @GetMapping("/getAllChatDoctor")
    public ResponseEntity<List<Chat>> getAllChatDoctor(){
        List<Chat> chatList = chatService.getAllChatDoctor();
        return ResponseEntity.ok(chatList);
    }

    @PostMapping(value = "/sendMessage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Message>> sendMessage(MessageDTO messageDTO, @RequestParam("files") MultipartFile files){
        Message message = chatService.sendMessage(messageDTO, files).get();
        List<Message> messageList = chatService.getAllMessageByChatId(messageDTO.chatId());
        return ResponseEntity.ok(messageList);
    }

    @GetMapping("/getAllMessageByChatId/{chatId}")
    public ResponseEntity<List<Message>> getAllMessageByChatId(@PathVariable("chatId") String chatId){
        List<Message> messageList = chatService.getAllMessageByChatId(chatId);
        return ResponseEntity.ok(messageList);
    }

    @DeleteMapping("/removeChat/{chatId}")
    public ResponseEntity<String> removeChat(@PathVariable("chatId") String chatId){
        try {
            chatService.removeChat(chatId);
            return ResponseEntity.ok("Delete chat");
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to remove chat: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/removeMessage/{messageId}")
    public ResponseEntity<String> removeMessage(@PathVariable("messageId") String messageId){
        boolean removed = chatService.removeMessage(messageId);
        if (removed){
            return ResponseEntity.ok("Delete message");
        }
        return ResponseEntity.ok("Failed to delete message, try again or later");
    }
}