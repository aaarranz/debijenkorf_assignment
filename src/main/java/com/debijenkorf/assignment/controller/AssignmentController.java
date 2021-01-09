package com.debijenkorf.assignment.controller;

import com.debijenkorf.assignment.services.AssignmentMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AssignmentController {

    private AssignmentMainService service;

    @Autowired
    public AssignmentController(AssignmentMainService service) {
        this.service = service;
    }

    @GetMapping(value = {"/image/show/{pred_type_name}/", "/image/show/{pred_type_name}/{dummy_seo_name}/"})
    public @ResponseBody byte[] getImage(@PathVariable(name = "pred_type_name") String pred_type_name,
                                         @PathVariable(name = "dummy_seo_name") Optional<String> dummy_seo_name,
                                         @RequestParam(defaultValue = "") String reference) {

        return service.getImage(pred_type_name, reference);
    }

    @DeleteMapping(value = {"/image/flush/{pred_type_name}/"})
    public void deleteImage(@PathVariable(name = "pred_type_name") String pred_type_name,
                            @RequestParam(defaultValue = "") String reference) {

        service.deleteOneOrAllImages(pred_type_name, reference);
    }
}
