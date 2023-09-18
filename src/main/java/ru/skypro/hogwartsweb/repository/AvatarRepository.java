package ru.skypro.hogwartsweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.hogwartsweb.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar,Long> {

    Optional<Avatar> findByStudent_Id (long studentId);
}
