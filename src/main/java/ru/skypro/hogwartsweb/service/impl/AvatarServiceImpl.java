package ru.skypro.hogwartsweb.service.impl;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.hogwartsweb.exception.AvatarProcessingException;
import ru.skypro.hogwartsweb.exception.StudentNotFoudException;
import ru.skypro.hogwartsweb.model.Avatar;
import ru.skypro.hogwartsweb.model.Student;
import ru.skypro.hogwartsweb.repository.AvatarRepository;
import ru.skypro.hogwartsweb.repository.StudentRepository;
import ru.skypro.hogwartsweb.service.StudentService;

import javax.swing.text.html.parser.Entity;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static io.swagger.v3.core.util.AnnotationsUtils.getExtensions;
import static java.nio.file.StandardOpenOption.CREATE_NEW;


@Service
public abstract class AvatarServiceImpl implements AvatarRepository{
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    @Value("${avatars.dir.path}")
    private String avatarDir;

    public AvatarServiceImpl(StudentService studentService,
                             AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public Avatar findAvatar(Long studentId) {
        return avatarRepository.findById(studentId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Avatar findOrCreateAvatar(Long studentId) {
        return avatarRepository.findById(studentId).orElse(new Avatar());
    }

    public void uploadAvatar(long studentId, MultipartFile avatarFile) throws IOException {
        if (avatarFile != null) {
            Student student = studentService.get(studentId);
            Path filePath = buildFilePath(student, avatarFile.getOriginalFilename());
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            try {
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedInputStream bos = new BufferedInputStream(is, 1024);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Avatar avatar = findOrCreateAvatar(studentId);
            avatar.setStudent(student);
            avatar.setFilePath(filePath.toString());
            avatar.setFileSize(avatarFile.getSize());
            avatar.setMediaType(avatarFile.getContentType());
            avatar.setData(avatarFile.getBytes());
            avatarRepository.save(avatar);
        }

    }
@Override
    public List<Avatar> getPage(Integer pageNumber, Integer pageSize) {
        return avatarRepository.findAll(QPageRequest.of(pageNumber - 1, pageSize)).getContent();
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private Path buildFilePath(Student student, String fileName) {
        return Path.of(avatarDir, student.getId() + "-" + student.getName() + "." +
                getExtensions(Objects.requireNonNull(fileName)));
    }
}

