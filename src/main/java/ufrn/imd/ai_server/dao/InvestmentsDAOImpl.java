package ufrn.imd.ai_server.dao;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvestmentsDAOImpl implements InvestmentsDAO{

    VectorStore vectorStore;

    public InvestmentsDAOImpl(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void add(List<String> investments) {
        List<Document> documents = investments.stream()
                .map(Document::new)
                .toList();
        vectorStore.add(documents);
    }

    @Override
    public List<String> findClosestMatches(String query, int numberOfMatches) {
        SearchRequest request = SearchRequest.builder()
                .query(query)
                .topK(numberOfMatches)
                .build();
        List<Document> results = vectorStore.similaritySearch(request);
        if (results == null) {
            return List.of();
        }
        return results.stream()
                .map((Document doc) -> doc.getText())
                .toList();
    }

    @Override
    public String findClosestMatch(String query) {
        List<String> closestMatches = findClosestMatches(query, 2);

        if (closestMatches != null && !closestMatches.isEmpty()) {
            return closestMatches.get(0);
        } else {
            return null;
        }
    }
}
