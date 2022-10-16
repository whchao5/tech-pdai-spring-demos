package pdai.tech.springboot.logback.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pdai
 */
@Slf4j
@RestController
@RequestMapping("/index")
public class LogTestController {

    @GetMapping("")
    public ResponseEntity<String> testLog() {
        log.trace("trace log @ {}", LocalDateTime.now());
        log.debug("debug log @ {}", LocalDateTime.now());
        log.info("info log @ {}", LocalDateTime.now());
        log.warn("warn log @ {}", LocalDateTime.now());
        log.error("error log @ {}", LocalDateTime.now());
        log.error("error2 log @ {}", LocalDateTime.now());
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("ooo", "oooo");
        return ResponseEntity.ok(hashMap.toString());
    }
}
