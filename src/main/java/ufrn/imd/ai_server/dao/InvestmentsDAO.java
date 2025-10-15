package ufrn.imd.ai_server.dao;

import java.util.List;

public interface InvestmentsDAO {
    void add(List<String> investments);

    List<String> findClosestMatches(String query, int numberOfMatches);

    String findClosestMatch(String query);
}
