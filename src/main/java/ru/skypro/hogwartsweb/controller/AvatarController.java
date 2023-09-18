package ru.skypro.hogwartsweb.controller;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.hogwartsweb.exception.AvatarNotFoudException;
import ru.skypro.hogwartsweb.exception.AvatarProcessingException;
import ru.skypro.hogwartsweb.exception.StudentNotFoudException;
import ru.skypro.hogwartsweb.service.impl.AvatarServiceImpl;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private AvatarServiceImpl avatarService;

    public AvatarController(AvatarServiceImpl avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadAvatar(@RequestParam long studentId,
                             @RequestParam MultipartFile avatar) {
        avatarService.upload(studentId, avatar);
    }

    @GetMapping("/from-fs")
    public ResponseEntity<byte[]> getFromFs(@RequestParam long studentId) {
        return buildResponseEntity(avatarService.getAvatarFromFs(studentId));
    }

    @GetMapping("/from-db")
    public ResponseEntity<byte[]> getFromDb(@RequestParam long studentId) {
        return buildResponseEntity(avatarService.getAvatarFromDb(studentId));
    }

    private ResponseEntity<byte[]> buildResponseEntity(Pair<byte[], String> pair) {
        byte[] responseBody = pair.getFirst();
        return ResponseEntity.ok()
                .contentLength(responseBody.length)
                .contentType(MediaType.parseMediaType(pair.getSecond()))
                .body(responseBody);
    }


    @ExceptionHandler({StudentNotFoudException.class, AvatarNotFoudException.class})

    public ResponseEntity<String> handleNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Студент или аватар не найден");
    }

    @ExceptionHandler(AvatarProcessingException.class)
    public ResponseEntity<String> handleAvatarProcessingException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка обратки аватара");
    }
}
