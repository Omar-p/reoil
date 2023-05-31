package edu.tanta.fci.reoil.catalog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Item, Long> {

}
