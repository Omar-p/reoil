package edu.tanta.fci.reoil.controller;

import edu.tanta.fci.reoil.model.Charity;
import edu.tanta.fci.reoil.model.SelectedCharity;
import edu.tanta.fci.reoil.service.CharityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/charities")
public class CharityController {

  private final CharityService charityService;

  @GetMapping
  public List<Charity> getCharities() {
    return charityService.getCharities();
  }

  @GetMapping("/{id}")
  public SelectedCharity getCharity(@PathVariable long id) {
    return charityService.getCharity(id);

  }
}
