# Spring
Projeto Spring focado em testes unitários/integração.

### Teste Unitário/Integração
O teste completo foi realizado em algumas classes.

Para as *Controller*, temos:
- na classe **AuthorControllerSpringBootTest** o teste foi completo. Nesta temos um teste de integração completo utilizando a anotação **@SpringBootTest**, na qual foi utilizado o profile de testes (application-test.properties). Criei um arquivo para inicializar as tabelas do banco a cada inicio de execução dos testes.
- na classe **AuthorControllerWebMvcTest** o teste foi parcial com o intuito de se divertir com a anotação **@WebMvcTest** que inicia o contexto do controller menos os outros, o que leva a utilização do mock através do **@MockitoBean**.
- na classe **AuthorControllerApenasJunitTest** o teste foi parcial e utilizo apenas teste unitário, sem a subida de nenhum contexto do Spring (diferente de ambos acima). O **MockMvc** é inicializado com ***MockMvcBuilders.standaloneSetup***.

Para as *Service*, fiz da classe **AuthorService**.

### Banco de Dados
Utilizei o banco MySQL e docker. Abaixo o script de criação do banco.
```
CREATE DATABASE `teste`;

USE `teste`;

CREATE TABLE `author` (
  `id` int AUTO_INCREMENT NOT NULL,
  `first_name` varchar(255) not NULL,
  `last_name` varchar(255) not NULL,
  `birthday` date default (current_date) not NULL,
  constraint `PK_AUTHOR` PRIMARY KEY (id)
);

insert into author(`first_name`, `last_name`, `birthday`)values('Rodrigo', 'rosa Rogoo', '1978-01-14');
insert into author(`first_name`, `last_name`, `birthday`)values('Asdf', 'de coisa', '1980-08-22');
insert into author(`first_name`, `last_name`, `birthday`)values('Padawan', 'Rosa', '1995-10-07');
insert into author(`first_name`, `last_name`, `birthday`)values('Nica', 'Star Warssss', '2021-01-22');

CREATE TABLE `post` (
  `id` int AUTO_INCREMENT NOT NULL,
  `title` varchar(155) not NULL,
  `description` varchar(1250) not NULL,
  `id_author` int NOT NULL,
  `time_created` datetime not NULL,
  `time_disabled` datetime NULL,
  constraint `PK_POST` PRIMARY KEY (id),
  constraint `FK_POST_AUTHOR` FOREIGN KEY (id_author) REFERENCES author(id)
);

insert into post(`title`, `description`, `id_author`, `time_created`, `time_disabled`)values('Motocas', 'São muito legais de legais de legal e de legalzaozao', 1, now(), date_add(now(),interval 1 day));
insert into post(`title`, `description`, `id_author`, `time_created`)values('Peixe', 'Todos bem lindoes', 1, now());
insert into post(`title`, `description`, `id_author`, `time_created`)values('Carros sao legais ou nao?', 'Carros sao bem legais e vamos que vamos', 1, now());
insert into post(`title`, `description`, `id_author`, `time_created`, `time_disabled`)values('Nuvem', 'Formada pro agua e mais paranaues', 2, now(), date_add(now(),interval 2 day));
insert into post(`title`, `description`, `id_author`)values('Testando', 'Falando sobre testes e mais testes', 3);
```

### Mapper
Utilizei dois tipos de conversão entre os objetos:
1. a clássica direta criação dos objetos (como em AuthorMapper e ErroDTOMapper);
2. utilizei a biblioteca mapStruct (em PostMapper).

Por estar utilizando Spring, para a _injeção de depedência_ do objeto mapeado utilizando a biblioteca ***MapStruct*** "cafuncionar", não esquecer de especificar o mapeamento abaixo.
```
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
```
### Exceção
Preferi trabalhar com modos de exceção do Spring que me possibilitem retornar mensagem ao cliente, e não apenas o Http Status.

Claro que existem mensagens bastantes óbvias, como HttpStatus.NOT_FOUND.

#### ResponseStatusException
Neste método podemos especificar a mensagem negocial, contudo a mensagem (campo ***message*** do retorno) só é exibida se:
- a propriedade ***server.error.include-message*** estiver preenchida com "always" ou "on_param" (neste caso a URL vai precisar ter o parâmetro "?message=true")
- colocar o Spring Boot DevTools no classpath
```
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-devtools</artifactId>
</dependency>
```

#### Criar Objeto Para Retorno
O controller AuthorController possui baixa coesão, retornando objetos DTO (Data Transfer Object). Aqui criei um objeto DTO base de resposta, RespostaDTO (um pequeno trecho abaixo).
```
@JsonInclude(Include.NON_NULL)
public class RespostaDTO<T> {

   private static final int HTTP_CODE_SUCCESS = 200;
   private static final int HTTP_CODE_BAD_REQUEST = 400;
   private int codigo;
   private T retorno;
   private List<ErroDTO> listaErro;
   private int totalRegistros;

   public static <T> RespostaDTO<T> criarRespostaSucesso(T retorno) {
      RespostaDTO<T> resposta = new RespostaDTO<T>();

      resposta.setCodigo(HTTP_CODE_SUCCESS);
      resposta.setRetorno(retorno);

      return resposta;
   }
   ... <RESTANTE DA CLASSE VER NO CÓDIGO>
}
```

Por exemplo, enviando a solicitação abaixo na qual não existe Author no banco.
```
http://localhost:8080/v1/authors/657
```
Retorno é.
```
{
    "codigo": 400,
    "listaErro": [
        {
            "message": "Não existe o Author com identificador 657"
        }
    ],
    "totalRegistros": 0
}
```
Já em caso de sucesso.
```
{
    "codigo": 200,
    "retorno": {
        "id": 1,
        "firstName": "Dani",
        "lastName": "rosa Rogoo",
        "birthday": "1995-03-14T03:00:00.000+00:00"
    },
    "totalRegistros": 0
}
```

### Model
Criei uma classe base com a super ajuda da anotação @MappedSuperclass, definindo alguns campos básicos para evitar duplicação, como:
- equals
- hashCode
- toString

Alguns campos estão comentados, que poderiam ser utilizados, como a version (lock optimista), data da criação e de atualização. Aqui poderia ter sido criado outras classes bases, justamente podendo conter este campos, como ***BaseModelVersion*** e ***BaseModelTimeCreated***.

Como a entidade ***Post*** eu quis colocar como retorno na controller (sei que é ruim por gerar alta coesão no projeto fiz por diversão mesmo), tive que colocar o ***@JsonIgnore*** no campo mapeando com a entidade ***Author*** para evitar retorno com ciclo infinito devido ao relacionamento entre ambas.

### Paginação
Como no exemplo da classe **AuthorController**, no método _listaAuthor_, é possível colocar um **Pageable** (org.springframework.data.domain) e receber como parametros da URL size, page e sort.

Se retornar a classe Page ao cliente, vão existir váriasssss propriedades.
```
{
    "content": [],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 5,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 4,
    "size": 5,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 4,
    "empty": false
}
```

Lembrando que o **sort**, se for utilizar múltiplos campos, tem que colocar vários para parametros **sort**, um para cada campo a ser ordenado.
```
http://localhost:8080/v1/author?size=5&page=0&sort=firstName,asc&sort=lastName,desc
```
