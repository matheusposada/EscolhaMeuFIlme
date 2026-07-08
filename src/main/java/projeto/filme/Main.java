package projeto.filme;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            String apiKey = ConfigLoader.carregarApiKey();
            TmdbClient tmdbClient = new TmdbClient(apiKey);

            Map<String, Integer> generos = tmdbClient.buscarGeneros();

            System.out.println("Qual categoria de filme você quer? " + generos.keySet());
            String categoria = scanner.nextLine().trim().toLowerCase();

            if (!generos.containsKey(categoria)) {
                System.out.println("Categoria não encontrada. Tente: " + generos.keySet());
                return;
            }

            int idGenero = generos.get(categoria);
            List<Filme> filmes = tmdbClient.buscarFilmesPorGenero(idGenero);

            if (filmes.isEmpty()) {
                System.out.println("Nenhum filme encontrado para essa categoria.");
                return;
            }

            boolean continuar = true;
            Random random = new Random();

            while (continuar) {
                Filme filmeEscolhido = filmes.get(random.nextInt(filmes.size()));
                System.out.println("\n🎬 Recomendação: " + filmeEscolhido);

                System.out.println("\nQuer sortear outro filme dessa categoria? (s/n)");
                String resposta = scanner.nextLine().trim().toLowerCase();
                continuar = resposta.equals("s");
            }

            System.out.println("Até a próxima! 🍿");

        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}