# Spring Boot Cache com Redis

Projeto simples para demonstrar o uso de cache com Spring Boot e Redis.

## Tecnologias

* Java 21
* Spring Boot
* Spring Cache
* Spring Data Redis
* Maven
* Docker
* Redis

## Como funciona

O método `getById` utiliza a anotação `@Cacheable`.

Na primeira busca, o produto é carregado normalmente e salvo no Redis.

Nas próximas buscas pelo mesmo ID, o resultado é retornado diretamente do cache.

```java
@Cacheable(value = "products", key = "#id")
public Product getById(Long id) {
    System.out.println("Searching product ID " + id + "...");

    return products.get(id);
}
```

## Executando o Redis

Com o Docker instalado, execute:

```bash
docker run --name my-redis -p 6379:6379 -d redis
```

## Configuração

Arquivo `application.properties`:

```properties
spring.application.name=cache

spring.cache.type=redis
spring.cache.cache-names=products
spring.cache.redis.time-to-live=10m
spring.cache.redis.cache-null-values=false

spring.data.redis.host=localhost
spring.data.redis.port=6379
```

## Executando o projeto

```bash
mvn spring-boot:run
```

Na primeira execução, os produtos serão buscados e armazenados no Redis.

As próximas consultas pelo mesmo ID serão retornadas diretamente do cache.

## Verificando as chaves

```bash
docker exec -it my-redis redis-cli KEYS "*"
```

Exemplo de retorno:

```text
products::1
products::2
```

## Autor

Desenvolvido por [FelipePLM](https://github.com/FelipePLM).
