package ru.skypro.hogwartsweb.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.hogwartsweb.exception.AvatarNotFoudException;
import ru.skypro.hogwartsweb.exception.AvatarProcessingException;
import ru.skypro.hogwartsweb.exception.StudentNotFoudException;
import ru.skypro.hogwartsweb.model.Avatar;
import ru.skypro.hogwartsweb.model.Student;
import ru.skypro.hogwartsweb.repository.AvatarRepository;
import ru.skypro.hogwartsweb.repository.StudentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AvatarServiceImpl {
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final Path pathToAvatarsDir;

    public AvatarServiceImpl(AvatarRepository avatarRepository,
                             StudentRepository studentRepository,
                             @Value("${application.avatars.dir}") String path) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.pathToAvatarsDir = Path.of(path);
    }

    public void upload(long studentId, MultipartFile image) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(StudentNotFoudException::new);

        try {
            String mediaType = image.getContentType();
            String extension = StringUtils.getFilenameExtension(image.getOriginalFilename());
            String fileName = studentId + "." + extension;
            Path pathToFile = pathToAvatarsDir.resolve(fileName);
            byte[] data = image.getBytes();
            Files.write(pathToFile, data);

            Avatar avatar = new Avatar();
            avatar.setStudent(student);
            avatar.setData(data);
            avatar.setFileSize(data.length);
            avatar.setMediaType(mediaType);
            avatar.setFilePath(pathToAvatarsDir.toString());

            avatarRepository.save(avatar);
        } catch (IOException e) {
            throw new AvatarProcessingException();
        }
    }
@Transactional
    public Pair<byte[], String> getAvatarFromFs(long studentId) {
        Avatar avatar = avatarRepository.findByStudent_Id(studentId)
                .orElseThrow(AvatarNotFoudException::new);
        try {
            return org.springframework.data.util.Pair.of(Files.readAllBytes(Path.of(avatar.getFilePath())),avatar.getMediaType());
        } catch (IOException e) {
            throw new AvatarProcessingException();

        }
    }
    @Transactional
    public Pair<byte[], String> getAvatarFromDb(long studentId) {
        Avatar avatar = avatarRepository.findByStudent_Id(studentId)
                .orElseThrow(AvatarNotFoudException::new);
            return org.springframework.data.util.Pair.of(avatar.getData(),avatar.getMediaType());
    }

}

