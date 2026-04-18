package ru.savka.demo.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savka.demo.entity.Country;
import ru.savka.demo.service.AdminCountryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/countries")
public class AdminCountryController {

    private final AdminCountryService adminCountryService;

    public AdminCountryController(AdminCountryService adminCountryService) {
        this.adminCountryService = adminCountryService;
    }

    @GetMapping
    public List<Country> getAllCountries() {
        return adminCountryService.getAllCountries();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Country> getCountry(@PathVariable String code) {
        return adminCountryService.getCountryByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Country createCountry(@RequestBody Map<String, String> body) {
        String code = body.get("code");
        String name = body.get("name");
        return adminCountryService.createCountry(code, name);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Country> updateCountry(@PathVariable String code, @RequestBody Map<String, String> body) {
        String name = body.get("name");
        return adminCountryService.updateCountry(code, name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCountry(@PathVariable String code) {
        if (adminCountryService.deleteCountry(code)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
