package ua.training.hospital.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.training.hospital.controller.dto.*;
import ua.training.hospital.entity.Medicine;
import ua.training.hospital.entity.Procedure;
import ua.training.hospital.entity.Surgery;
import ua.training.hospital.service.diagnosis.DiagnosisService;
import ua.training.hospital.service.medicine.MedicineService;
import ua.training.hospital.service.procedure.ProcedureService;
import ua.training.hospital.service.surgery.SurgeryService;

import java.security.Principal;
import java.util.Optional;

@Controller
public class ShowDiagnosisController {
    private static final Logger logger = LogManager.getLogger(ShowDiagnosisController.class);

    @Autowired
    DiagnosisService diagnosisService;

    @Autowired
    MedicineService medicineService;

    @Autowired
    ProcedureService procedureService;

    @Autowired
    SurgeryService surgeryService;

    @RequestMapping(value = "patient{idPatient}/diagnosis{idDiagnosis}", method = RequestMethod.GET)
    public String getDoctorPage(@PathVariable long idPatient,
                                @PathVariable long idDiagnosis,
                                Model model) {
        logger.debug("requested patient" + idPatient + "/diagnosis" + idDiagnosis);
        diagnosisService.getDiagnosis(idDiagnosis).ifPresent(diagnosis -> {
            model.addAttribute("diagnosis",diagnosis);
        });
        logger.debug("returning showDiagnosis.jsp page");
        return "showDiagnosis";
    }

    @ResponseBody
    @RequestMapping(value = "/getMedicine{idDiagnosis}",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Page<Medicine> getMedicine(@PathVariable long idDiagnosis,
                                        @RequestParam(defaultValue = "0") int pageNumber,
                                        @RequestParam(defaultValue = "10") int recordsPerPage) {
        logger.debug("requested /getMedicine" + idDiagnosis);
        Page<Medicine> page = medicineService.findMedicineByDiagnosisId(pageNumber,recordsPerPage,idDiagnosis);
        logger.debug("returning org.springframework.data.domain.Page<Medicine> with size: " + page.getNumberOfElements());
        return page;
    }

    @ResponseBody
    @RequestMapping(value = "/getProcedures{idDiagnosis}",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Page<Procedure> getProcedures(@PathVariable long idDiagnosis,
                                         @RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = "10") int recordsPerPage) {
        logger.debug("requested /getProcedures" + idDiagnosis);
        Page<Procedure> page = procedureService.findProceduresByDiagnosisId(pageNumber,recordsPerPage,idDiagnosis);
        logger.debug("returning org.springframework.data.domain.Page<Procedure> with size: " + page.getNumberOfElements());
        return page;
    }


    @ResponseBody
    @RequestMapping(value = "/getSurgeries{idDiagnosis}",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Page<Surgery> getSurgeries(@PathVariable long idDiagnosis,
                                      @RequestParam(defaultValue = "0") int pageNumber,
                                      @RequestParam(defaultValue = "10") int recordsPerPage) {
        logger.debug("requested /getSurgeries" + idDiagnosis);
        Page<Surgery> page = surgeryService.findSurgeriesByDiagnosisId(pageNumber,recordsPerPage,idDiagnosis);
        logger.debug("returning org.springframework.data.domain.Page<Surgery> with size: " + page.getNumberOfElements());
        return page;
    }

    @ResponseBody
    @RequestMapping(value = "/doctor/diagnosis{idDiagnosis}/addMedicine",
            method = RequestMethod.POST)
    public ResponseEntity<CreationResponse> addMedicine(
            @PathVariable long idDiagnosis,
            @Validated @RequestBody MedicineDTO medicineDto,
            BindingResult result,
            Principal principal){
        logger.debug("requested /doctor/diagnosis" + idDiagnosis + "/addMedicine post method");
        if(result.hasErrors()){
            logger.debug("medicineDTO contains errors returning \"wrongData\" creation response");
            return new ResponseEntity<>(new CreationResponse("wrongData",result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }

        Optional<Medicine> created =  medicineService.createMedicine(medicineDto,idDiagnosis,principal.getName());
        if(!created.isPresent()){
            logger.info("medicineService.createMedicine returned empty optional, returning \"cant create entity\" creation response");
            result.reject("{medicine.cannotCreate}");
            return new ResponseEntity<>(new CreationResponse("cant create entity",result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        logger.debug("creation of medicine successful returning  \"created\" creation response\"");
        return new ResponseEntity<>(new CreationResponse("created",result.getAllErrors()), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/doctor/diagnosis{idDiagnosis}/addProcedure",
            method = RequestMethod.POST)
    public ResponseEntity<CreationResponse> addProcedure(
            @PathVariable long idDiagnosis,
            @Validated @RequestBody ProcedureDTO procedureDto,
            BindingResult result,
            Principal principal){
        logger.debug("requested /doctor/diagnosis" + idDiagnosis + "/addProcedure post method");
        if(result.hasErrors()){
            logger.debug("ProcedureDTO contains errors returning \"wrongData\" creation response");
            return new ResponseEntity<>(new CreationResponse("wrongData",result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }

        Optional<Procedure> created =  procedureService.createProcedure(procedureDto,idDiagnosis,principal.getName());
        if(!created.isPresent()){
            logger.info("procedureService.createProcedure returned empty optional, returning \"cant create entity\" creation response");
            result.reject("{procedure.cannotCreate}");
            return new ResponseEntity<>(new CreationResponse("cant create entity",result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        logger.debug("creation of procedure successful returning  \"created\" creation response\"");
        return new ResponseEntity<>(new CreationResponse("created",result.getAllErrors()), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/doctor/diagnosis{idDiagnosis}/addSurgery",
            method = RequestMethod.POST)
    public ResponseEntity<CreationResponse> addSurgery(
            @PathVariable long idDiagnosis,
            @Validated @RequestBody SurgeryDTO surgeryDto,
            BindingResult result,
            Principal principal){
        logger.debug("requested /doctor/diagnosis" + idDiagnosis + "/addSurgery post method");
        if(result.hasErrors()){
            logger.debug("SurgeryDTO contains errors returning \"wrongData\" creation response");
            return new ResponseEntity<>(new CreationResponse("wrongData",result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }

        Optional<Surgery> created = surgeryService.createSurgery(surgeryDto,idDiagnosis,principal.getName());
        if(!created.isPresent()){
            logger.info("surgeryService.createSurgery returned empty optional, returning \"cant create entity\" creation response");
            result.reject("{surgery.cannotCreate}");
            return new ResponseEntity<>(new CreationResponse("cant create entity",result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        logger.debug("creation of surgery successful returning  \"created\" creation response\"");
        return new ResponseEntity<>(new CreationResponse("created",result.getAllErrors()), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/doctor/diagnosis{idDiagnosis}/closeDiagnosis",
            method = RequestMethod.PATCH)
    public ResponseEntity<ClosingResponse> closeDiagnosis(
            @PathVariable long idDiagnosis,
            Model model){
        logger.debug("requested /doctor/diagnosis" + idDiagnosis + "/closeDiagnosis patch method");
        if(diagnosisService.closeDiagnosis(idDiagnosis)){
            logger.info("diagnosis with id: " + idDiagnosis + " closed");
            return new ResponseEntity<>(new ClosingResponse("closed"),HttpStatus.OK);
        }else{
            logger.info("cant close diagnosis with id: "+ idDiagnosis);
            return new ResponseEntity<>(new ClosingResponse("cantClose"),HttpStatus.BAD_REQUEST);
        }
    }
}
