package projeto.filme;

public class Filme {
    private final String titulo;
    private final String dataLancamento;
    private final double nota;
    private final String sinopse;

    public Filme(String titulo, String dataLancamento, double nota, String sinopse) {
        this.titulo = titulo;
        this.dataLancamento = dataLancamento;
        this.nota = nota;
        this.sinopse = sinopse;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public double getNota() {
        return nota;
    }

    public String getSinopse() {
        return sinopse;
    }

    @Override
    public String toString() {
        String ano = dataLancamento != null && dataLancamento.length() >= 4
                ? dataLancamento.substring(0, 4)
                : "ano desconhecido";

        String sinopseFormatada = (sinopse == null || sinopse.isBlank())
                ? "Sinopse não disponível."
                : sinopse;

        return titulo + " (" + ano + ") — nota " + nota
                + "\n📝 " + sinopseFormatada;
    }
}
