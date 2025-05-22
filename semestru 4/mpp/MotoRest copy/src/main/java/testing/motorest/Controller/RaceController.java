package testing.motorest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testing.motorest.model.Race;
import testing.motorest.repository.RaceRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/races")
public class RaceController {

    private final RaceRepository raceRepository;
    private final RaceModelAssembler assembler;

    @Autowired
    public RaceController(RaceRepository raceRepository, RaceModelAssembler assembler) {
        this.raceRepository = raceRepository;
        this.assembler = assembler;
    }

    // GET /races
    @GetMapping
    public CollectionModel<EntityModel<Race>> getAllRaces() {
        List<EntityModel<Race>> races = raceRepository.findAll().values().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(races,
                linkTo(methodOn(RaceController.class).getAllRaces()).withSelfRel());
    }

    // GET /races/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Race>> getRaceById(@PathVariable Integer id) {
        Optional<Race> race = raceRepository.findOne(id);
        return race.map(r -> ResponseEntity.ok(assembler.toModel(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /races
    @PostMapping
    public ResponseEntity<EntityModel<Race>> addRace(@RequestBody Race race) {
        Race saved = raceRepository.save(race).get();
        return ResponseEntity.ok(assembler.toModel(saved));
    }

    // PUT /races/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Race>> updateRace(@PathVariable Integer id, @RequestBody Race updatedRace) {
        return raceRepository.findOne(id)
                .map(existing -> {
                    existing.setEngineType(updatedRace.getEngineType());
                    existing.setNoPlayers(updatedRace.getNoPlayers());
                    existing.setPlayers(updatedRace.getPlayers());
                    Race saved = raceRepository.update(existing).get();
                    return ResponseEntity.ok(assembler.toModel(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /races/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRace(@PathVariable Integer id) {
        if (raceRepository.findOne(id).isPresent()) {
            raceRepository.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
