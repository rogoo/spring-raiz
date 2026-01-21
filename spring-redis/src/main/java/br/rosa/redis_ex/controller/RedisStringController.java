package br.rosa.redis_ex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/redis-string")
public class RedisStringController {
    private static final String STRING_KEY_PREFIX = "redis_ex:string:";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/set")
    public Map.Entry<String, String> setString(@RequestParam String key,
                                               @RequestParam String value,
                                               @RequestParam(required = false) Long timer) {
        if (timer != null) {
            redisTemplate.opsForValue()
                    .set(STRING_KEY_PREFIX + key, value, timer, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(STRING_KEY_PREFIX + key, value);
        }
        return Map.entry(key, value);
    }

    @GetMapping("/get/{key}")
    public Map.Entry<String, String> getString(@PathVariable("key") String key) {
        String redisKey = STRING_KEY_PREFIX + key;
        String value = redisTemplate.opsForValue().get(redisKey);
        if (value == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Key not found" + " in Redis");
        }
        return Map.entry(key, value);
    }

    @GetMapping("/list-keys")
    public Map<String, String> listKeys() {
        Set<String> keys = redisTemplate.keys(STRING_KEY_PREFIX + "*");
        if (keys == null || keys.isEmpty()) {
            return Map.of();
        }

        return keys.stream().collect(Collectors.toMap(
                k -> k.startsWith(STRING_KEY_PREFIX) ? k.substring(
                        STRING_KEY_PREFIX.length()) : k, k -> {
                    String v = redisTemplate.opsForValue().get(k);
                    return v == null ? "" : v;
                }, (oldVal, newVal) -> newVal));
    }

    @DeleteMapping("/keys")
    public int deleteAllKeys() {
        Set<String> keys = redisTemplate.keys(STRING_KEY_PREFIX + "*");
        if (keys == null || keys.isEmpty()) {
            return 0;
        }
        Long removed = redisTemplate.delete(keys);
        return removed == null ? 0 : removed.intValue();
    }
}
