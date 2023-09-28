package ru.skypro.hogwartsweb.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skypro.hogwartsweb.service.InfoService;

@Service
public class InfoServiceImpl implements InfoService {
@Value("${service.port}")
    private String port;
    @Override
    public String getPost() {
        return port;
    }
}
