package ua.training.hospital.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.training.hospital.controller.dto.CreationResponse;
import ua.training.hospital.controller.dto.MedicineDTO;
import ua.training.hospital.controller.dto.ProcedureDTO;
import ua.training.hospital.controller.dto.SurgeryDTO;
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
    @Autowired
    DiagnosisService diagnosisService;

    @Autowired
    MedicineService medicineService;

    @Autowired
    ProcedureService procedureService;

    @Autowired
    SurgeryService surgeryService;

    @RequestMapping(value = "/doctor/patient{idPatient}/diagnosis{idDiagnosis}", method = RequestMethod.GET)
    public String getDoctorPage(@PathVariable long idPatient,
                                @PathVariable long idDiagnosis,
                                Model model) {
        diagnosisService.getDiagnosis(idDiagnosis).ifPresent(diagnosis -> {
            model.addAttribute("diagnosis",diagnosis);
        });

        return "doctor/showDiagnosis";
    }

    @ResponseBody
    @RequestMapping(value = "/getMedicine{idDiagnosis}",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Page<Medicine> getMedicine(@PathVariable long idDiagnosis,
                                        @RequestParam(defaultValue = "0") int pageNumber,
                                        @RequestParam(defaultValue = "10") int recordsPerPage) {
        Page<Medicine> page = medicineService.findMedicineByDiagnosisId(pageNumber,recordsPerPage,idDiagnosis);

        return page;
    }

    @ResponseBody
    @RequestMapping(value = "/getProcedures{idDiagnosis}",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Page<Procedure> getProcedures(@PathVariable long idDiagnosis,
                                         @RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = "10") int recordsPerPage) {
        Page<Procedure> page = procedureService.findProceduresByDiagnosisId(pageNumber,recordsPerPage,idDiagnosis);
        return page;
    }


    @ResponseBody
    @RequestMapping(value = "/getSurgeries{idDiagnosis}",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Page<Surgery> getSurgeries(@PathVariable long idDiagnosis,
                                      @RequestParam(defaultValue = "0") int pageNumber,
                                      @RequestParam(defaultValue = "10") int recordsPerPage) {
        Page<Surgery> page = surgeryService.findSurgeriesByDiagnosisId(pageNumber,recordsPerPage,idDiagnosis);
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

        if(result.hasErrors()){
            return new ResponseEntity<>(new CreationResponse("wrongData",result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }

        Optional<Medicine> created =  medicineService.createMedicine(medicineDto,idDiagnosis,principal.getName());
        if(!created.isPresent()){
            result.reject("{medicine.cannotCreate}");
            return new ResponseEntity<>(new CreationResponse("cant create entity",result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
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

        if(result.hasErrors()){
            return new ResponseEntity<>(new CreationResponse("wrongData",result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }

        Optional<Procedure> created =  procedureService.createProcedure(procedureDto,idDiagnosis,principal.getName());
        if(!created.isPresent()){
            result.reject("{procedure.cannotCreate}");
            return new ResponseEntity<>(new CreationResponse("cant create entity",result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
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

        if(result.hasErrors()){
            return new ResponseEntity<>(new CreationResponse("wrongData",result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }

        Optional<Surgery> created =  surgeryService.createSurgery(surgeryDto,idDiagnosis,principal.getName());
        if(!created.isPresent()){
            result.reject("{surgery.cannotCreate}");
            return new ResponseEntity<>(new CreationResponse("cant create entity",result.getAllErrors()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new CreationResponse("created",result.getAllErrors()), HttpStatus.OK);
    }
}
