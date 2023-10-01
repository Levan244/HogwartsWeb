package ru.skypro.hogwartsweb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.hogwartsweb.exception.AvatarNotFoudException;
import ru.skypro.hogwartsweb.exception.AvatarProcessingException;
import ru.skypro.hogwartsweb.exception.StudentNotFoudException;
import ru.skypro.hogwartsweb.model.Avatar;
import ru.skypro.hogwartsweb.service.impl.AvatarServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/avatar")
@Tag(name = "API для работы с аватарами")
public class AvatarController {
    private AvatarServiceImpl avatarService;

    public AvatarController(AvatarServiceImpl avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрить аватар студента")
    public ResponseEntity<String> uploadAvatar(@PathVariable long studentId,
                                               @RequestParam MultipartFile avatar) throws IOException {

        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar-from-db")
    @Operation(summary = "Скаяать аватар")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable long id) {
        Avatar avatar = avatarService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "{id}/avatar-from-file")
    @Operation(summary = "Скачать аватар студента")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping
    @Operation(summary = "Список аватаров")
    public ResponseEntity<List<Avatar>> getAvatarPage(@RequestParam Integer pageSize,
                                                      @RequestParam Integer pageNumber) {
        List<Avatar> avatars = avatarService.getPage(pageNumber, pageSize);
        return ResponseEntity.ok(avatars);
    }

}
