package edu.tanta.fci.reoil.catalog;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/catalog")
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
public class CatalogAdminController {

  private final CatalogAdminService catalogAdminService;


  @GetMapping
  public List<Item> getItems() {
    return catalogAdminService.getItems();
  }

  @GetMapping("/{id}")
  public Item getItem(@PathVariable("id") Long id) {
    return catalogAdminService.getItem(id);
  }

  @PostMapping
  public Item createItem(@RequestBody @Valid CreateItemRequest createItemRequest) {
    return catalogAdminService.createItem(createItemRequest);
  }

}
