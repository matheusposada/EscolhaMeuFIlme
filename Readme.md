# EscolhaMeuFilme

Aplicativo em Java que recomenda filmes com base na categoria escolhida pelo usuário, usando a API do [TMDB](https://www.themoviedb.org/).

## Funcionalidades

- Lista as categorias (gêneros) disponíveis, buscadas em tempo real na API
- Sorteia um filme aleatório da categoria escolhida
- Mostra título, ano, nota e sinopse do filme
- Permite sortear vários filmes seguidos, sem precisar reiniciar o programa
- Trata erros de conexão e de API de forma amigável

## Tecnologias

- Java 25
- Maven
- [Gson](https://github.com/google/gson) — parsing de JSON
- API do [TMDB](https://developer.themoviedb.org/docs) — dados dos filmes

## Como rodar

### Pré-requisitos
- JDK 17 ou superior
- Uma API key gratuita da TMDB ([como obter](https://www.themoviedb.org/settings/api))

### Passos

1. Clone o repositório
```bash
   git clone https://github.com/matheusposada/EscolhaMeuFilme.git
```

2. Copie o arquivo de configuração de exemplo:
```bash
   cp src/main/resources/config.properties.example src/main/resources/config.properties
```

3. Abra `config.properties` e preencha sua API key:
```properties
   tmdb.api.key=sua_chave_aqui
```

4. Rode a classe `Main`

## Estrutura do projeto
Main.java → fluxo principal do programa

TmdbClient.java → comunicação com a API do TMDB

ConfigLoader.java → carregamento de configurações

Filme.java → modelo de dados de um filme

## Roadmap

- [x] Nível 1 — Lista de filmes fixa no código
- [x] Nível 2 — Leitura de filmes via arquivo CSV
- [x] Nível 3 — Integração com API real (TMDB)
- [x] Nível 4 — Interface gráfica