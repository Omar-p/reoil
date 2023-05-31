package edu.tanta.fci.reoil.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogUserService {

    private final CatalogRepository catalogRepository;

    public List<Item> getItems() {
        return catalogRepository.findAll();
    }
}
