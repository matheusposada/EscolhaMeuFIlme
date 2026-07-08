package projeto.filme;

import com.google.gson.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class TmdbClient {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String apiKey;

    public TmdbClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public Map<String, Integer> buscarGeneros() {
        String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + apiKey + "&language=pt-BR";
        String corpo = fazerRequisicao(url);

        JsonObject json = JsonParser.parseString(corpo).getAsJsonObject();
        JsonArray listaGeneros = json.getAsJsonArray("genres");

        Map<String, Integer> generos = new HashMap<>();
        for (JsonElement el : listaGeneros) {
            JsonObject genero = el.getAsJsonObject();
            String nome = genero.get("name").getAsString().toLowerCase();
            int id = genero.get("id").getAsInt();
            generos.put(nome, id);
        }
        return generos;
    }

    public List<Filme> buscarFilmesPorGenero(int idGenero) {
        List<Filme> filmes = new ArrayList<>();

        for (int pagina = 1; pagina <= 3; pagina++) {
            String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + apiKey
                    + "&language=pt-BR"
                    + "&with_genres=" + idGenero
                    + "&sort_by=popularity.desc"
                    + "&vote_count.gte=100"
                    + "&page=" + pagina;

            String corpo = fazerRequisicao(url);
            JsonObject json = JsonParser.parseString(corpo).getAsJsonObject();
            JsonArray resultados = json.getAsJsonArray("results");

            for (JsonElement el : resultados) {
                JsonObject filmeJson = el.getAsJsonObject();
                String titulo = filmeJson.get("title").getAsString();

                if (pareceTerCaracteresNaoLatinos(titulo)) {
                    continue;
                }

                String dataLancamento = filmeJson.has("release_date")
                        ? filmeJson.get("release_date").getAsString()
                        : null;
                double nota = filmeJson.has("vote_average")
                        ? filmeJson.get("vote_average").getAsDouble()
                        : 0.0;
                String sinopse = filmeJson.has("overview")
                        ? filmeJson.get("overview").getAsString()
                        : null;

                filmes.add(new Filme(titulo, dataLancamento, nota, sinopse));
            }
        }

        return filmes;
    }

    private boolean pareceTerCaracteresNaoLatinos(String titulo) {
        for (char c : titulo.toCharArray()) {
            Character.UnicodeScript script = Character.UnicodeScript.of(c);
            if (script != Character.UnicodeScript.LATIN
                    && script != Character.UnicodeScript.COMMON) {
                return true;
            }
        }
        return false;
    }

    private String fazerRequisicao(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("A API retornou um erro (código " + response.statusCode() + "). Verifique sua API key.");
            }

            return response.body();

        } catch (IOException e) {
            throw new RuntimeException("Não foi possível conectar à internet. Verifique sua conexão e tente novamente.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("A requisição foi interrompida.");
        }
    }
}
