package pairproject.foodmap.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pairproject.foodmap.domain.Grade;
import pairproject.foodmap.service.GradeService;

@RestController
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;

    @PostMapping("/grade")
    public ResponseEntity<String> gradeCreate(@RequestBody Grade grade) {
        gradeService.createGrade(grade);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/grade/{gradeId}")
    public ResponseEntity<String> gradeCreate(@PathVariable long gradeId) {
        gradeService.deleteGrade(gradeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
