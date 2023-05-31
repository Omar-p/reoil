package edu.tanta.fci.reoil.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@PreAuthorize("hasAuthority('SCOPE_USER')")
@RequiredArgsConstructor
public class CatalogUserController {

    private final CatalogUserService catalogUserService;


    @GetMapping("/items")
    public List<Item> getItems() {
        return catalogUserService.getItems();
    }
}
