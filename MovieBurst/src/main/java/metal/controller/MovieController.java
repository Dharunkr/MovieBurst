package metal.controller;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MovieController {

    private static final String API_KEY = "c3d7607b421179325006d7b92d01a136"; 
    private static final String TMDB_URL = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&query=";

    

    @GetMapping("/")
    public String showHomepage() {
        return "start";
    }

    @GetMapping("/search")
    public String searchMovies(@RequestParam("mood") String mood, Model model) {
        List<Map<String, String>> movies = fetchMovies(mood);
        model.addAttribute("movies", movies);

        return "print";
    }

    private List<Map<String, String>> fetchMovies(String mood) {
        List<Map<String, String>> movieList = new ArrayList<>();
        String url = TMDB_URL + mood;

        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray results = jsonResponse.optJSONArray("results");

            if (results != null) {
                for (int i = 0; i < Math.min(results.length(), 5); i++) {
                    JSONObject movie = results.getJSONObject(i);
                    
                    String title = movie.optString("title", "Unknown Title");
                    String description = movie.optString("overview", "No description available");
                    String posterPath = movie.optString("poster_path", "");

                    // Check if poster_path is valid
                    String posterUrl = posterPath.isEmpty() ? "https://via.placeholder.com/500x750?text=No+Image" 
                                                             : "https://image.tmdb.org/t/p/w500" + posterPath;

                    Map<String, String> movieData = new HashMap<>();
                    movieData.put("title", title);
                    movieData.put("poster", posterUrl);
                    movieData.put("description", description);

                    movieList.add(movieData);
                }
            }

        } catch (Exception e) {
            System.err.println("Error fetching movies: " + e.getMessage());
        }

        return movieList;
    }
}
