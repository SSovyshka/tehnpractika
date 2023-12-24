package sim.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sim.data.domain.SimcardSearchModel;
import sim.data.domain.utils.MessageReturnPojo;
import sim.data.pg.tables.dbo.Contract;
import sim.data.pg.tables.dbo.Simcard;
import sim.services.AppDaoService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import sim.data.domain.InvoiceSearchModel;
import sim.data.pg.tables.ParsedContract;

@CrossOrigin
@RestController
@RequestMapping(value = "/app")
@Slf4j
@Tag(name = "App", description = "App controller API")
public class AppController {

    //*****************************************************************************
    // CRUD Simcard
    //*****************************************************************************

    @Autowired
    private AppDaoService appDaoService;

    @Operation(
            summary = "Get all contracts",
            description = "Return list of contracts",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved a list of contracts",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Contract.class)))
                    )
            }
    )
    @GetMapping(value = "/simcard")
    @ResponseBody
    public ResponseEntity<List<Simcard>> getSimcards(){
        return ResponseEntity.ok(appDaoService.getAllSims());
    }

//------------------------------------------------------

    @Operation(
            summary = "",
            description = "",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved a list of contractss",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Simcard.class)))
                    )
            }
    )
    @PostMapping(value = "/simcard/search",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<Simcard>> getSims(@RequestBody SimcardSearchModel model) {
        return ResponseEntity.ok(appDaoService.getSims(model));
    }

//------------------------------------------------------

    @Operation(
            summary = "Update a simcard.",
            description = "Update a simcard entry in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = Simcard.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully update a entry",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageReturnPojo.class))
                    )
            }
    )
    @PutMapping(value = "/simcard",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<MessageReturnPojo> simcardUpdate(@RequestBody Simcard model) {
        return ResponseEntity.ok(appDaoService.updateSim(model));
    }

//------------------------------------------------------

    @Operation(
            summary = "Create new simcard",
            description = "Create a new simcard entry in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = Simcard.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully creation a new entry",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageReturnPojo.class))
                    )
            }
    )
    @PostMapping("/simcard")
    @ResponseBody
    public ResponseEntity<MessageReturnPojo> simCreate(@RequestBody Simcard model){
        return ResponseEntity.ok(appDaoService.simCreate(model));
    }

//------------------------------------------------------
    @Operation(
            summary = "Delete simcard",
            description = "Delete simcard",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully delete the contracts",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageReturnPojo.class))
                )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The id of the contracts to be deleted",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer")
                    )
            }
    )
    @DeleteMapping("/simcard/{id}")
    @ResponseBody
    public ResponseEntity<MessageReturnPojo> simDelete(@PathVariable("id") Integer id){
        try {
            return ResponseEntity.ok(appDaoService.simDelete(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    //*****************************************************************************
    // CRUD Invoices
    //*****************************************************************************

    @Operation(
            summary = "Upload Invoice File",
            description = "Uploads an invoice file and associates it with a specific date.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful upload",
                            content = @Content(
                                schema = @Schema(implementation = MessageReturnPojo.class)
                            )
                    )
            }
    )
    @PostMapping(value = "/invoice/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public MessageReturnPojo uploadFile(@RequestPart("file") MultipartFile file, @RequestParam("date") Integer date) {
        if (file.isEmpty()){
            return new MessageReturnPojo(0, "File is empty");
        }

        try (InputStream inputStream = file.getInputStream()){
            ResponseEntity.ok(appDaoService.uploadFile(inputStream, date));
            return new MessageReturnPojo(1, "Upload successful");
        } catch (IOException e) {
            return new MessageReturnPojo(0, "Error upload");
        }
    }

//------------------------------------------------------

     @Operation(
            summary = "Get invoice detail",
            description = "Returns the invoice detail of period",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = InvoiceSearchModel.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully retrieved the invoice detail of period",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ParsedContract.class)))
                    )
            }
    )
    @PostMapping(value = "/invoice/search",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<ParsedContract>> invoiceSearch(@RequestBody InvoiceSearchModel model) {
        return ResponseEntity.ok(appDaoService.invoiceSearch(model));
    }


    //*****************************************************************************
    // CRUD Contract
    //*****************************************************************************

    @Operation(
            summary = "Get all contracts",
            description = "Return list of contracts",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved a list of contracts",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Contract.class)))
                    )
            }
    )
    @GetMapping(value = "/contract")
    @ResponseBody
    public ResponseEntity<List<Contract>> getContracts(){
        return ResponseEntity.ok(appDaoService.getContracts());
    }

//------------------------------------------------------

    @Operation(
            summary = "Get filtered contracts",
            description = "Returns a list of filtered contracts",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = Contract.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully retrieved a list of filtered contracts",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Contract.class)))
                    )
            }
    )
    @PostMapping(value = "/contract/search",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<Contract>> contractsSearch(@RequestBody Contract model) {
        return ResponseEntity.ok(appDaoService.contractSearch(model));
    }

//------------------------------------------------------

    @Operation(
            summary = "Create new contracts",
            description = "Create a new contracts entry in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = Contract.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully creation a new entry",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Contract.class))
                    )
            }
    )
    @PostMapping(value = "/contract",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<Contract> contractsCreate(@RequestBody Contract model) {
        return ResponseEntity.ok(appDaoService.createContract(model));
    }

//------------------------------------------------------

    @Operation(
            summary = "Update a contracts",
            description = "Update a contracts entry in database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = Contract.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully update a entry",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageReturnPojo.class))
                    )
            }
    )
    @PutMapping(value = "/contract",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<MessageReturnPojo> contractsUpdate(@RequestBody Contract model) {
        return ResponseEntity.ok(appDaoService.updateContract(model));
    }

//------------------------------------------------------

    @Operation(
            summary = "Delete contracts",
            description = "Delete the contracts in the database",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The id of the contracts to be deleted",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully delete the contracts",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageReturnPojo.class))
                    )
            }
    )
    @DeleteMapping(value = "/contract/{id}",  headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<MessageReturnPojo> contractsDelete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(appDaoService.deleteContract(id));
    }






}


