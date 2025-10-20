package ufrn.imd.ai_server.services;

import java.util.List;

public interface InvestmentsService {
    void save(List<String> investments);
    List<String> findClosestMatches(String query);
    String findClosestMatch(String query);

    public List<String> getInvestments();
}
