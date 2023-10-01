package ru.skypro.hogwartsweb.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.hogwartsweb.service.InfoService;

@RestController
@RequestMapping("info")
public class InfoController {
    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/getPort")
    public String getPort() {
        return infoService.getPost();
    }

    @GetMapping("/calculate")
    public void calculate(@RequestParam Integer limit) {
        infoService.calculateBySteam(limit);
    }
}
