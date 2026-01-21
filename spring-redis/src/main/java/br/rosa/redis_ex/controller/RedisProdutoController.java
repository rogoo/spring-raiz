package br.rosa.redis_ex.controller;

import br.rosa.redis_ex.vo.Produto;
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
@RequestMapping("/api/redis-objeto/produto")
public class RedisProdutoController {
    private static final String PRODUCE_ID_KEY_PREFIX = "produto_id:";

    private final RedisTemplate<String, Produto> produtoRedisTemplateAux;

    @Autowired
    public RedisProdutoController(RedisTemplate<String, Produto> produtoRedisTemplate) {
        this.produtoRedisTemplateAux = produtoRedisTemplate;
    }

    @PostMapping("/set")
    public Map.Entry<String, Produto> setString(@RequestBody Produto produto,
                                                @RequestParam(required = false) Long timer) {
        String redisKey = PRODUCE_ID_KEY_PREFIX + produto.getId();
        if (timer != null) {
            produtoRedisTemplateAux.opsForValue()
                    .set(redisKey, produto, timer, TimeUnit.SECONDS);
        } else {
            produtoRedisTemplateAux.opsForValue().set(redisKey, produto);
        }

        return Map.entry(redisKey, produto);
    }

    @GetMapping("/get/{produtoId}")
    public Map.Entry<String, Produto> getString(@PathVariable("produtoId") Integer produtoId) {
        String redisKey = PRODUCE_ID_KEY_PREFIX + produtoId;
        Produto produto = produtoRedisTemplateAux.opsForValue().get(redisKey);
        if (produto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Key not found" + " in Redis. (produtoId Key: " + redisKey + ")");
        }
        return Map.entry(redisKey, produto);
    }

    @GetMapping("/list-keys")
    public Map<String, Produto> listKeys() {
        Set<String> keys = produtoRedisTemplateAux.keys(PRODUCE_ID_KEY_PREFIX + "*");
        if (keys == null || keys.isEmpty()) {
            return Map.of();
        }

        return keys.stream().collect(Collectors.toMap(
                k -> k.startsWith(PRODUCE_ID_KEY_PREFIX) ? k.substring(
                        PRODUCE_ID_KEY_PREFIX.length()) : k, k -> {
                    Produto v = produtoRedisTemplateAux.opsForValue().get(k);
                    return v == null ? new Produto() : v;
                }, (oldVal, newVal) -> newVal));
    }

    @DeleteMapping("/keys")
    public int deleteAllKeys() {
        Set<String> keys = produtoRedisTemplateAux.keys(PRODUCE_ID_KEY_PREFIX + "*");
        if (keys == null || keys.isEmpty()) {
            return 0;
        }
        Long removed = produtoRedisTemplateAux.delete(keys);
        return removed == null ? 0 : removed.intValue();
    }
}
