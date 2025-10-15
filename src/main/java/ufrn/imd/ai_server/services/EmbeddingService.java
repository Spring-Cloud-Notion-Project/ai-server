package ufrn.imd.ai_server.services;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbeddingService {
    private final EmbeddingModel model;

    public EmbeddingService(EmbeddingModel model) {
        this.model = model;
    }

    private List<String> investments = List.of(
            "Banco do Brasil CDB: O CDB é um investimento de renda fixa que, no BB, pode ser encontrado em 2 modalidades: a prefixada (com os juros definidos no momento da aplicação) e a pós-fixada (atrelada à variação do CDI e com a taxa definida no momento da aplicação).\n" +
                    "Assim como em outros investimentos de renda fixa, o CDB possui um prazo de vencimento, ou seja, há uma data máxima para manter o valor aplicado. Apesar deste prazo o CDB pós-fixado pode ser resgatado a qualquer momento.\n" +
                    "Na data de vencimento, o valor que ainda não tiver sido resgatado, você recebe de volta diretamente em sua conta corrente, acrescido do rendimento.",
            "Quando posso resgatar o CBD no Banco do Brasil:" +
                    "Os títulos privados de renda fixa, como CDB, LCI, LCA e LC, possuem uma data de vencimento. No BB, as modalidades pós-fixadas podem ser resgatadas a partir do dia seguinte a data da aplicação e o dinheiro fica disponível na sua conta imediatamente. Já as prefixadas são planejadas para o resgate no vencimento da operação.\n" +
                    "Para a modalidade CDB DI é possível cadastrar o resgate automático. Em todos os casos, os resgates são feitos em grupos de R$500 somados com os seus rendimentos."
    );

    public String findInvestments(String query){
        List<float[]> investmentEmbeddings = null;
        float[] queryEmbedding = null;

        investmentEmbeddings = model.embed(investments);
        queryEmbedding = model.embed(query);

        int mostSimilarIndex = -1;
        mostSimilarIndex = findClosestMatch(queryEmbedding, investmentEmbeddings);

        if(mostSimilarIndex < 0) {
            return "Nenhuma informação encontrada para a consulta: " + query;
        } else {
            return investments.get(mostSimilarIndex);
        }
    }

    public static int findClosestMatch(float[] query, List<float[]> cursos) {
        int mostSimilarIndex = -1;
        double mostSimilarScore = -1;
        for (int i = 0; i < cursos.size(); i++) {
            float[] investmentEmbedding = cursos.get(i);
            double similarity = cosineSimilarity(query, investmentEmbedding);
            if (similarity > mostSimilarScore) {
                mostSimilarScore = similarity;
                mostSimilarIndex = i;
            }
        }
        return mostSimilarIndex;
    }

    public static double cosineSimilarity(float[] vectorA, float[] vectorB) {
        if (vectorA.length != vectorB.length) {
            throw new IllegalArgumentException("Vectors must have the same length");
        }

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vectorA.length; i++) {
            double a = vectorA[i];
            double b = vectorB[i];
            dotProduct += a * b;
            normA += a * a;
            normB += b * b;
        }


        if (normA == 0 || normB == 0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
