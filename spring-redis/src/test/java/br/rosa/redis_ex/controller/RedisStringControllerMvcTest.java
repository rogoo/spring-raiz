package br.rosa.redis_ex.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RedisStringControllerMvcTest {

    private MockMvc mockMvc;

    private RedisTemplate<String, String> redisTemplate;

    private ValueOperations<String, String> valueOps;

    @BeforeEach
    void setUp() throws Exception {
        redisTemplate = Mockito.mock(RedisTemplate.class);
        valueOps = Mockito.mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOps);

        RedisStringController controller = new RedisStringController();
        // inject the private RedisTemplate field via reflection
        Field f = RedisStringController.class.getDeclaredField("redisTemplate");
        f.setAccessible(true);
        f.set(controller, redisTemplate);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void setString_SemTimer_Retorna() throws Exception {
        mockMvc.perform(post("/api/redis-string/set").param("key", "k1").param("value", "v1"))
                .andExpect(status().isOk());

        verify(valueOps).set("redis_ex:string:k1", "v1");
    }

    @Test
    void setString_ComTimer_Retorna() throws Exception {
        mockMvc.perform(post("/api/redis-string/set").param("key", "kt").param("value", "vt")
                .param("timer", "10")).andExpect(status().isOk());

        verify(valueOps).set("redis_ex:string:kt", "vt", 10L, TimeUnit.SECONDS);
    }

    @Test
    void getString_Encontrado() throws Exception {
        when(valueOps.get("redis_ex:string:mykey")).thenReturn("myval");

        mockMvc.perform(get("/api/redis-string/get/mykey")).andExpect(status().isOk());
    }

    @Test
    void getString_NaoEncontrado_404() throws Exception {
        when(valueOps.get("redis_ex:string:nope")).thenReturn(null);

        mockMvc.perform(get("/api/redis-string/get/nope")).andExpect(status().isNotFound());
    }

    @Test
    void listKeys_ListaVazia() throws Exception {
        when(redisTemplate.keys("redis_ex:string:*")).thenReturn(new HashSet<>());

        mockMvc.perform(get("/api/redis-string/list-keys")).andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    void listKeys_RetornaLista() throws Exception {
        Set<String> keys = new HashSet<>();
        keys.add("redis_ex:string:a");
        keys.add("redis_ex:string:b");
        when(redisTemplate.keys("redis_ex:string:*")).thenReturn(keys);
        when(valueOps.get("redis_ex:string:a")).thenReturn("VA");
        when(valueOps.get("redis_ex:string:b")).thenReturn("VB");

        mockMvc.perform(get("/api/redis-string/list-keys")).andExpect(status().isOk())
                .andExpect(jsonPath("$.a").value("VA")).andExpect(jsonPath("$.b").value("VB"));
    }

    @Test
    void deleteAllKeys_RetornaZeroPoisVazio() throws Exception {
        when(redisTemplate.keys("redis_ex:string:*")).thenReturn(new HashSet<>());

        mockMvc.perform(delete("/api/redis-string/keys")).andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    void deleteAllKeys_RetornaNumeroRemovido() throws Exception {
        Set<String> keys = new HashSet<>();
        keys.add("redis_ex:string:x");
        keys.add("redis_ex:string:y");
        when(redisTemplate.keys("redis_ex:string:*")).thenReturn(keys);
        when(redisTemplate.delete(keys)).thenReturn(2L);

        mockMvc.perform(delete("/api/redis-string/keys")).andExpect(status().isOk())
                .andExpect(content().string("2"));
    }
}
