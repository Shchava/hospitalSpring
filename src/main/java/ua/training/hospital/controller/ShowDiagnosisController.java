package ua.training.hospital.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.training.hospital.entity.Diagnosis;
import ua.training.hospital.entity.Medicine;
import ua.training.hospital.service.medicine.MedicineService;

@Controller
public class ShowDiagnosisController {
    @Autowired
    MedicineService medicineService;

    @RequestMapping(value = "/doctor/patient{idPatient}/diagnosis{idDiagnosis}", method = RequestMethod.GET)
    public String getDoctorPage(@PathVariable long idPatient,
                                @PathVariable long idDiagnosis,
                                Model model) {
        Diagnosis diag = new Diagnosis();
        diag.setIdDiagnosis(3);
        model.addAttribute("diagnosis",diag);
        return "doctor/showDiagnosis";
    }

    @ResponseBody
    @RequestMapping(value = "/getMedicine{idDiagnosis}",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Page<Medicine> getDoctorPage(@PathVariable long idDiagnosis,
                                        @RequestParam(defaultValue = "0") int pageNumber,
                                        @RequestParam(defaultValue = "10") int recordsPerPage,
                                        Model model) {
        Page<Medicine> page = medicineService.findMedicineByDiagnosisId(pageNumber,recordsPerPage,idDiagnosis);

        model.addAttribute("diagnosis",new Diagnosis());
        return page;
//        return Json()
    }
}
