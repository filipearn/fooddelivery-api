package arn.filipe.fooddelivery.api.controller;

import arn.filipe.fooddelivery.api.assembler.TeamInputDisassembler;
import arn.filipe.fooddelivery.api.assembler.TeamModelAssembler;
import arn.filipe.fooddelivery.api.model.TeamModel;
import arn.filipe.fooddelivery.api.model.input.TeamInput;
import arn.filipe.fooddelivery.domain.model.Team;
import arn.filipe.fooddelivery.domain.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamInputDisassembler TeamInputDisassembler;

    @Autowired
    private TeamModelAssembler TeamModelAssembler;

    @GetMapping
    public List<TeamModel> listAll(){
        return TeamModelAssembler.toCollectionModel(teamService.listAll());
    }

    @GetMapping("/{id}")
    public TeamModel findById(@PathVariable Long id){
        return TeamModelAssembler.toModel(teamService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamModel save(@RequestBody @Valid TeamInput TeamInput){
        Team team = TeamInputDisassembler.toDomainObject(TeamInput);

        return TeamModelAssembler.toModel(teamService.save(team));
    }

    @PutMapping("/{id}")
    public TeamModel update(@PathVariable Long id, @RequestBody @Valid TeamInput teamInput){
        Team team = teamService.verifyIfExistsOrThrow(id);

        TeamInputDisassembler.copyToDomainObject(teamInput, team);

        return TeamModelAssembler.toModel(teamService.save(team));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        teamService.delete(id);
    }
}
