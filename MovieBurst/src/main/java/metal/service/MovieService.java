package metal.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service  
public class MovieService {

    private static final String API_KEY = "c3d7607b421179325006d7b92d01a136";  
    private static final String TMDB_URL = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY;
    
 // Maps genre names to TMDB genre IDs
    private String getGenreId(String genre) {
        Map<String, String> genreMap = new HashMap<>();
        genreMap.put("action", "28");
        genreMap.put("adventure", "12");
        genreMap.put("animation", "16");
        genreMap.put("comedy", "35");
        genreMap.put("crime", "80");
        genreMap.put("documentary", "99");
        genreMap.put("drama", "18");
        genreMap.put("family", "10751");
        genreMap.put("fantasy", "14");
        genreMap.put("history", "36");
        genreMap.put("horror", "27");
        genreMap.put("music", "10402");
        genreMap.put("mystery", "9648");
        genreMap.put("romance", "10749");
        genreMap.put("sci-fi", "878");
        genreMap.put("thriller", "53");
        genreMap.put("war", "10752");
        genreMap.put("western", "37");

        return genreMap.getOrDefault(genre.toLowerCase(), "");
    }

    
    
    

    public List<Map<String, String>> searchMovies(String language, String genre, String year, String movieName) {
        List<Map<String, String>> movieList = new ArrayList<>();
        String tmdbBaseUrl = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY;
        
        
        if (language.equalsIgnoreCase("tamil")) {
            language = "ta";
        } else {
            language = "en"; 
        }

        // Fetch genre ID
        String genreId = getGenreId(genre);

        // Construct API URL
        String url = tmdbBaseUrl + "&language=" + language;
        if (!genreId.isEmpty()) url += "&with_genres=" + genreId;
        if (!year.isEmpty()) url += "&primary_release_year=" + year;

        System.out.println("Fetching movies from: " + url); 

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

                    String posterUrl = posterPath.isEmpty() ? 
                        "https://via.placeholder.com/500x750?text=No+Image" : 
                        "https://image.tmdb.org/t/p/w500" + posterPath;

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
    
    
    


    public List<Map<String, String>> searchMoviesByTitle(String movieName) {
        List<Map<String, String>> movieList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&query=" + movieName;

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

                    String posterUrl = posterPath.isEmpty() ? 
                        "https://via.placeholder.com/500x750?text=No+Image" : 
                        "https://image.tmdb.org/t/p/w500" + posterPath;

                    Map<String, String> movieData = new HashMap<>();
                    movieData.put("title", title);
                    movieData.put("poster", posterUrl);
                    movieData.put("description", description);

                    movieList.add(movieData);
                }
            }

        } catch (Exception e) {
            System.err.println("Error fetching movie by title: " + e.getMessage());
        }

        return movieList;
    }

}