package uz.pdp.Doctor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.Doctor.repository.*;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepo chatRepo;
    private final MessageRepo messageRepo;
    private final CallRepo callRepo;
    private final DoctorRepo doctorRepo;
    private final String AWS_URL = "https://medicsg40website.s3.ap-northeast-1.amazonaws.com/";


}
