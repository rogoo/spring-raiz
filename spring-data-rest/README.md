# spring-data-rest
Simples aplicação em REST e utilizando o banco H2. Também temos maven.

Para listar todos endoints, acessar este link.
```
http://localhost:8080/apir
```


Os endpoints são. Os endpoints são automaticamente gerados pelo Spring.
- http://localhost:8080/apir/person (GET) - lists all
- http://localhost:8080/apir/person/{id} (GET) - search by person by id
- http://localhost:8080/apir/person (POST) - includes a person. Send the data in JSON format.
- http://localhost:8080/apir/person (PUT) - updates all information of a person. Send the data in JSON format.
- http://localhost:8080/apir/person (PATCH) - updates some information of a person. Send the data in JSON format.
- http://localhost:8080/apir/person/{id} (DELETE) - removes the person by id

## Chaging Base URI
O padrão é utilizar a URL **"/"**, mas troquei para **/apir** utilizando o caminho abaixo.
```
server.servlet.context-path=/apir
```

## REST api
Spring analizando os Repositories vai gerar, automaticamente, todos endpoints, sem caixa alta e no plural.

Neste projeto só temos um Repositorio, consequentemente apenas uma URL **/persons**.
```
public interface PerRepository extends CrudRepository<Person, Integer>
```
Mas para testar se funciona mesmo, fiz a troca retirando o plural, utilizando @RepositoryRestResource.
```
@RepositoryRestResource(path = "person")
public interface PerRepository extends CrudRepository<Person, Integer>
```

## Date JSON Format
Alterei o padrão utilizando pelo Spring para **"dd/MM/yyyY"** utilizando uma anotação da lib Jackson: **@JsonFormat(pattern = "dd/MM/yyyy")**.

## H2 Console
Por padrão o banco H2 é salvo apenas na memória, mas você pode salvar em um arquivo se bem desejar.

Se quiser acessar o bonito GUI que o H2 tem, lembre de habilitar no spring.
```
spring.h2.console.enabled=true*
```
Então acesse na URL **/h2-console**. você pode alterar esta URL padrão.
```
spring.h2.console.path=/h2
```
## Database Preloading
Na classe ConfigurationEntry estou carregando algumas informações.

## Use of Spring Devtools
Esta dependência permite a automática reinicialização do servidor Spring com mudanças
#### Maven
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-devtools</artifactId>
	<scope>runtime</scope>
	<optional>true</optional>
</dependency>
```

#### Gradle
```
dependencies {
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}
```

Isso ra-pa-zi-a-da!!!!
