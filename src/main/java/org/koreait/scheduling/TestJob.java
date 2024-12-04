package org.koreait.scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Scheduling
 *
 */
@Slf4j
@Service
public class TestJob {

    // @Scheduled : 주기적으로 실행할 메서드라는 Annotation
    // fixedRate = 3000L : 3초마다 반복 실행
    // @Scheduled(fixedRate = 3000L)
    // =
    // @Scheduled(fixedRate = 3L, timeUnit = TimeUnit.SECONDS)
    public void testScheduling() {

        log.info("3초 마다 실행!");
    }
}