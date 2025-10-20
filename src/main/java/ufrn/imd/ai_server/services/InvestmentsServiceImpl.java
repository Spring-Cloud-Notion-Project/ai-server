package ufrn.imd.ai_server.services;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;
import ufrn.imd.ai_server.dao.InvestmentsDAO;

import java.util.List;

@Service
public class InvestmentsServiceImpl implements InvestmentsService{
    private final EmbeddingModel model;

    private final InvestmentsDAO investmentsDAO;

    public InvestmentsServiceImpl(EmbeddingModel model, InvestmentsDAO investmentsDAO) {
        this.model = model;
        this.investmentsDAO = investmentsDAO;
    }

    private List<String> investments = List.of(
            "Banco do Brasil CDB: O CDB é um investimento de renda fixa que, no BB, pode ser encontrado em 2 modalidades: a prefixada (com os juros definidos no momento da aplica��o) e a p�s-fixada (atrelada � varia��o do CDI e com a taxa definida no momento da aplica��o).\n" +
                    "Assim como em outros investimentos de renda fixa, o CDB possui um prazo de vencimento, ou seja, há uma data m�xima para manter o valor aplicado. Apesar deste prazo o CDB p�s-fixado pode ser resgatado a qualquer momento.\n" +
                    "Na data de vencimento, o valor que ainda n�o tiver sido resgatado, você recebe de volta diretamente em sua conta corrente, acrescido do rendimento."+
            "Quando posso resgatar o CBD no Banco do Brasil:" +
                    "Os t�tulos privados de renda fixa, como CDB, LCI, LCA e LC, possuem uma data de vencimento. No BB, as modalidades p�s-fixadas podem ser resgatadas a partir do dia seguinte a data da aplica��o e o dinheiro fica dispon�vel na sua conta imediatamente. J� as prefixadas s�o planejadas para o resgate no vencimento da opera��o.\n" +
                    "Para a modalidade CDB DI é possível cadastrar o resgate autom�tico. Em todos os casos, os resgates s�o feitos em grupos de R$500 somados com os seus rendimentos.",
            "Banco do Brasil LCA: A LCA é um investimento de renda fixa criado para dar lastro ao crédito agropecuário no país. Os títulos são emitidos pelo BB com o objetivo de captar recursos financeiros destinados a empréstimos, neste caso, para o setor agropecuário. Quando você compra um título, recebe em troca disso um rendimento pré-estabelecido.\n" +
                    "A LCA BB está disponível em 3 modalidades: a prefixada (com juros definidos antes da compra), a pós-fixada com resgate (atrelado à variação do CDI) e a pós-fixada sem resgate (também atrelada ao CDI). Elas são isentas de IR, não possuem taxa de administração, nem nenhuma outra forma de tributação sobre seu rendimento.\n" +
                    "No caso da pós-fixada sem resgate, havendo a necessidade de resgatar o seu investimento, você consegue fazer isso mediante uma redução da taxa aplicada, após 190 dias da data da aplicação."
    );

    public List<String> getInvestments() {
        return investments;
    }

    @Override
    public void save(List<String> investments) {
        investmentsDAO.add(investments);
    }

    @Override
    public List<String> findClosestMatches(String query) {
        return investmentsDAO.findClosestMatches(query, 1);
    }

    @Override
    public String findClosestMatch(String query) {
        return investmentsDAO.findClosestMatch(query);
    }
}
