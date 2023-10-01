package ru.skypro.hogwartsweb.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skypro.hogwartsweb.service.InfoService;

import java.util.stream.LongStream;
import java.util.stream.Stream;

@Service
public class InfoServiceImpl implements InfoService {

    private final Logger logger = LoggerFactory.getLogger(InfoServiceImpl.class);

@Value("${service.port}")
    private String port;
    @Override
    public String getPost() {
        return port;
    }

    @Override
    public void calculateBySteam(int limit) {
        calculate1(limit);
        calculate2(limit);
        calculate3(limit);
    }

    private void calculate1(int limit) {
        long start = System.currentTimeMillis();

        long result = Stream.iterate(1, a -> a + 1)
                .limit(limit)
                .reduce(0, (a, b) -> a + b);

        long end = System.currentTimeMillis();

        logger.info("Time 1: " + (end - start));
    }

    private void calculate2(int limit) {
        long start = System.currentTimeMillis();

        long result = Stream.iterate(1, a -> a + 1)
                .parallel()
                .limit(limit)
                .reduce(0, (a, b) -> a + b);

        long end = System.currentTimeMillis();

        logger.info("Time 2: " + (end - start));
    }
    private void calculate3(int limit) {
        long start = System.currentTimeMillis();

        long result = LongStream
                .range(1, limit)
                .sum();
        long end = System.currentTimeMillis();

        logger.info("Time 3: " + (end - start));
    }
}
