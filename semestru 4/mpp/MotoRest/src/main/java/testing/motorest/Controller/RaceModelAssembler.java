package testing.motorest.Controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import testing.motorest.model.Race;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RaceModelAssembler implements RepresentationModelAssembler<Race, EntityModel<Race>> {

    @Override
    public EntityModel<Race> toModel(Race race) {
        return EntityModel.of(race,
                linkTo(methodOn(RaceController.class).getRaceById(race.getId())).withSelfRel(),
                linkTo(methodOn(RaceController.class).getAllRaces()).withRel("races"));
    }
}
