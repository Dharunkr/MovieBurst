package metal.service;

import org.springframework.stereotype.Service;
import org.json.JSONObject;

@Service  
public class AIService {

    public String getStructuredQuery(String query) {
        // Simulating AI processing (Replace this with OpenAI/GPT API call)
        JSONObject structuredQuery = new JSONObject();
        
        if (query.toLowerCase().contains("telugu")) {
            structuredQuery.put("language", "te");
        } else if (query.toLowerCase().contains("tamil")) {
            structuredQuery.put("language", "ta");
        } else {
            structuredQuery.put("language", "en");
        }

        if (query.toLowerCase().contains("action")) {
            structuredQuery.put("genre", "action");
        } else if (query.toLowerCase().contains("comedy")) {
            structuredQuery.put("genre", "comedy");
        } else {
            structuredQuery.put("genre", "drama");
        }

        if (query.toLowerCase().contains("latest") || query.toLowerCase().contains("2024")) {
            structuredQuery.put("year", "2024");
        } else if (query.toLowerCase().contains("2023")) {
            structuredQuery.put("year", "2023");
        } else {
            structuredQuery.put("year", "");
        }

        return structuredQuery.toString();
    }
}
