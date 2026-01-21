## Spring/Redis
Projeto Spring com Redis. O Redis foi configurado no docker sem persistencia (seja AOF ou RDB).

Em duas controllers, ***RedisStringController*** e ***RedisProdutoController***, apenas salvo o objeto no cache, e na controller ***RedisDatabaseAuthorController*** o cache no redis foi feito para evitar constantes consultas ao banco. Na classe ***DefaultConfig*** define um timer específico para o cache *"authorCache"*.

### Testes
Para não passar batido, gerei dois testes em duas controllers: um teste unitario (RedisDatabaseAuthorControllerTest) e um usando MockMvc (*RedisStringControllerMvcTest*).

Isso ai. Até a próxima pessoarrrrrrrr.