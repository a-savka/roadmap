package ru.savka.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savka.demo.dto.StepDto;
import ru.savka.demo.service.StepService;

import java.util.List;

@RestController
@RequestMapping("/steps")
public class StepController {

    private final StepService stepService;

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping
    public List<StepDto> getAllSteps() {
        return stepService.getAllSteps();
    }

    @GetMapping("/{stepName}")
    public StepDto getStep(@PathVariable String stepName) {
        return stepService.getStepByName(stepName);
    }
}
