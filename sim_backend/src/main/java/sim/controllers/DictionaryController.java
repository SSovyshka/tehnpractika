package sim.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sim.data.domain.utils.MessageReturnPojo;
import sim.data.pg.tables.dictionary.Filial;
import sim.data.pg.tables.dictionary.Grupa;
import sim.data.pg.tables.dictionary.OfficialRank;
import sim.services.DictionaryDaoService;

import java.math.BigInteger;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/app/dictionary")
@Slf4j
@Tag(name = "Dictionary", description = "Dictionary controller API")
public class DictionaryController {
    @Autowired
    private DictionaryDaoService dictionaryDaoService;

    //*****************************************************************************
    // CRUD Fillial
    //*****************************************************************************

    @Operation(
            summary = "Get all filials",
            description = "Return list of filials",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved a list of filials",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Filial.class)))
                    )
            }
    )
    @GetMapping(value = "/filials",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<Filial>> fillials() {
        return ResponseEntity.ok(dictionaryDaoService.getFilials());
    }

    //------

    @Operation(
            summary = "Get filtered filials",
            description = "Returns a list of filtered filials",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = Filial.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully retrieved a list of filtered groups",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Filial.class)))
                    )
            }
    )
    @PostMapping(value = "/filial/search",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<Filial>> filialSearch(@RequestBody Filial model) {
        return ResponseEntity.ok(dictionaryDaoService.filialSearch(model));
    }

    //------

    @Operation(
            summary = "Create new filial",
            description = "Create a new filial entry in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = Filial.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully creation a new entry",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Filial.class))
                    )
            }
    )
    @PostMapping(value = "/filial",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<Filial> filialCreate(@RequestBody Filial model) {
        return ResponseEntity.ok(dictionaryDaoService.createFilial(model));
    }

    //------

    @Operation(
            summary = "Update a filial",
            description = "Update a filial entry in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = Filial.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully update a entry",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageReturnPojo.class))
                    )
            }
    )
    @PutMapping(value = "/filial",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<MessageReturnPojo> filialUpdate(@RequestBody Filial model) {
        return ResponseEntity.ok(dictionaryDaoService.updateFilial(model));
    }

    //------

    @Operation(
            summary = "Delete filial",
            description = "Delete the filial in the database",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The id of the filial to be deleted",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully delete the filial",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageReturnPojo.class))
                    )
            }
    )
    @DeleteMapping(value = "/filial/{id}",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<MessageReturnPojo> filialDelete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(dictionaryDaoService.deleteFilial(id));
    }

    
    //*****************************************************************************
    // CRUD Group
    //*****************************************************************************
    
    @Operation(summary = "Get all groups", description = "Returns a list of all groups")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Successfully retrieved a list of groups",
                     content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Grupa.class)))
        )
    })
    @GetMapping(value = "/groups",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<Grupa>> groups() {
        return ResponseEntity.ok(dictionaryDaoService.getGrups());
    }

    //------
    
    @Operation(summary = "Get filtered groups",
               description = "Returns a list of filtered groups",
               requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = Grupa.class))
               ),
               responses = {
                    @ApiResponse(responseCode = "200", 
                                 description = "Successfully retrieved a list of filtered groups",
                                 content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Grupa.class)))
                    )
               }
    )
    @PostMapping(value = "/group/search",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<Grupa>> groupSearch(@RequestBody Grupa model) {
        return ResponseEntity.ok(dictionaryDaoService.groupSearch(model));
    }

    //------

    @Operation(summary = "Create new group",
               description = "Create a new group entry in database",
               requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                       content = @Content(mediaType = "application/json", schema = @Schema(implementation = Grupa.class))
               ),
               responses = {
                    @ApiResponse(responseCode = "200",
                                 description = "Successfully creation a new entry",
                                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Grupa.class))
                    ),
               }
    )
    @PostMapping(value = "/group",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<Grupa> groupsCreate(@RequestBody Grupa model) {
        return ResponseEntity.ok(dictionaryDaoService.createGrupa(model));
    }

    //------

    @Operation(summary = "Update group",
               description = "Update the group in the database",
               requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                       content = @Content(mediaType = "application/json", schema = @Schema(implementation = Grupa.class))
               ),
               responses = {
                    @ApiResponse(responseCode = "200",
                                 description = "Successfully update group",
                                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageReturnPojo.class))
                    )
               }
    )
    @PutMapping(value = "/group",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<MessageReturnPojo> groupsUpdate(@RequestBody Grupa model) {
        return ResponseEntity.ok(dictionaryDaoService.updateGrupa(model));
    }

    //------

    @Operation(summary = "Delete group by id",
               description = "Delete the group in the database",
               parameters = {
                        @Parameter(
                               name = "id",
                               description = "The id of the group to be deleted",
                               required = true,
                               in = ParameterIn.PATH,
                               schema = @Schema(type = "integer")
                       )
               },
               responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully delete the group",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageReturnPojo.class))
                    )
               }

    )
    @DeleteMapping(value = "/group/{id}",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<MessageReturnPojo> groupsDelete(@PathVariable BigInteger id) {
        return ResponseEntity.ok(dictionaryDaoService.deleteGrupa(id));
    }
    
    //*****************************************************************************
    // CRUD Official Rank
    //*****************************************************************************


    @Operation(
            summary = "Get all official ranks",
            description = "Returns a list of all official ranks",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully retrieved a list of official ranks",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OfficialRank.class)))
                    )
            }
    )
    @GetMapping(value = "/officialranks",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<OfficialRank>> officialranks() {
        return ResponseEntity.ok(dictionaryDaoService.getOfficialRanks());
    }

    //------

    @Operation(
            summary = "Get all official ranks like name",
            description = "Return all official ranks like name",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OfficialRank.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved a list of filtered official ranks",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OfficialRank.class)))
                    )
            }
    )
    @PostMapping(value = "/officialrank/search",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<OfficialRank>> officialranksByName(@RequestBody OfficialRank model) {
        return ResponseEntity.ok(dictionaryDaoService.getOfficialRankByName(model));
    }

    //------

    @Operation(
            summary = "Create official rank",
            description = "Create a new official rank entry in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OfficialRank.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully creation a new official rank entry in database"
                    )
            }
    )
    @PostMapping(value = "/officialrank",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<OfficialRank> officialranksCreate(@RequestBody OfficialRank model) {
        return ResponseEntity.ok(dictionaryDaoService.createOfficialRank(model));
    }

    //------

    @Operation(
            summary = "Update official rank",
            description = "Update a official rank in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OfficialRank.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully update a official rank in database"
                    )
            }
    )
    @PutMapping(value = "/officialrank",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<MessageReturnPojo> officialranksUpdate(@RequestBody OfficialRank model) {
        return ResponseEntity.ok(dictionaryDaoService.updateOfficialRank(model));
    }

    //------

    @Operation(
            summary = "Delete official rank",
            description = "Delete the official rank in the database",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The id of the official rank to be deleted",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully delete the group",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageReturnPojo.class))
                    )
            }

    )
    @DeleteMapping(value = "/officialranks/{id}",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<MessageReturnPojo> officialranksDelete(@PathVariable Integer id) {
        return ResponseEntity.ok(dictionaryDaoService.deleteOfficialRank(id));
    }
}
