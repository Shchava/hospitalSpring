package ua.training.hospital.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.entity.Medicine;
import ua.training.hospital.entity.Procedure;
import ua.training.hospital.entity.Surgery;
import ua.training.hospital.service.diagnosis.DiagnosisService;
import ua.training.hospital.service.medicine.MedicineService;
import ua.training.hospital.service.procedure.ProcedureService;
import ua.training.hospital.service.surgery.SurgeryService;

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

        model.addAttribute("newMedicine",new Medicine());
        return "doctor/showDiagnosis";
    }

    @ResponseBody
    @RequestMapping(value = "/getMedicine{idDiagnosis}",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Page<Medicine> getMedicine(@PathVariable long idDiagnosis,
                                        @RequestParam(defaultValue = "0") int pageNumber,
                                        @RequestParam(defaultValue = "10") int recordsPerPage,
                                        Model model) {
        Page<Medicine> page = medicineService.findMedicineByDiagnosisId(pageNumber,recordsPerPage,idDiagnosis);

        return page;
    }

    @ResponseBody
    @RequestMapping(value = "/getProcedures{idDiagnosis}",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Page<Procedure> getProcedures(@PathVariable long idDiagnosis,
                                         @RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = "10") int recordsPerPage,
                                         Model model) {
        Page<Procedure> page = procedureService.findProceduresByDiagnosisId(pageNumber,recordsPerPage,idDiagnosis);
        return page;
    }


    @ResponseBody
    @RequestMapping(value = "/getSurgeries{idDiagnosis}",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Page<Surgery> getSurgeries(@PathVariable long idDiagnosis,
                                      @RequestParam(defaultValue = "0") int pageNumber,
                                      @RequestParam(defaultValue = "10") int recordsPerPage,
                                      Model model) {
        Page<Surgery> page = surgeryService.findSurgeriesByDiagnosisId(pageNumber,recordsPerPage,idDiagnosis);
        return page;
    }
}
