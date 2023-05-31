package edu.tanta.fci.reoil.catalog;

import edu.tanta.fci.reoil.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogAdminService {

  private final CatalogRepository catalogRepository;

  public Item createItem(CreateItemRequest createItemRequest) {
    Item item = toItem(createItemRequest);
    return catalogRepository.save(item);
  }

  public Item addImageUrl(Long id, String imageUrl) {
    Item item = catalogRepository.findById(id).orElseThrow(
        () -> new NotFoundException("No item with that id")
    );
    item.setImageUrl(imageUrl);
    return catalogRepository.save(item);
  }

  private Item toItem(CreateItemRequest createItemRequest) {
    return new Item(createItemRequest.name(), createItemRequest.quantity(), createItemRequest.points(), createItemRequest.unit());
  }

  public List<Item> getItems() {
    return catalogRepository.findAll();
  }

  public Item getItem(Long id) {
    return catalogRepository.findById(id).orElseThrow(
        () -> new NotFoundException("No item with that id")
    );
  }
}
