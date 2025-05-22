package testing.motorest;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import testing.motorest.model.Race;


import java.util.List;
import java.util.Map;

public class RaceClient {

    private final RestClient restClient;

    public RaceClient(String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public List<Map<String, Object>> getAllRaces() {
        return restClient.get()
                .uri("/races")
                .retrieve()
                .body(List.class);
    }

    public Race getRaceById(int id) {
        return restClient.get()
                .uri("/races/{id}", id)
                .retrieve()
                .body(Race.class);
    }

    public Race createRace(Race race) {
        return restClient.post()
                .uri("/races")
                .body(race)
                .retrieve()
                .body(Race.class);
    }

    public Race updateRace(int id, Race race) {
        return restClient.put()
                .uri("/races/{id}", id)
                .body(race)
                .retrieve()
                .body(Race.class);
    }

    public void deleteRace(int id) {
        restClient.delete()
                .uri("/races/{id}", id)
                .retrieve();
    }

    public static void main(String[] args) {
        RaceClient client = new RaceClient("http://localhost:8080");

        // Example usage:
        Race newRace = new Race();
        newRace.setEngineType(250);
        newRace.setNoPlayers(2);


        Race created = client.createRace(newRace);
        System.out.println("Created Race: " + created);

        Race fetched = client.getRaceById(created.getId());
        System.out.println("Fetched Race: " + fetched);

        fetched.setEngineType(300);
        Race updated = client.updateRace(fetched.getId(), fetched);
        System.out.println("Updated Race: " + updated);

        client.deleteRace(updated.getId());
        System.out.println("Deleted Race.");
    }
}
