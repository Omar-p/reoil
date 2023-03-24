package edu.tanta.fci.reoil.model;

import java.util.List;

public record SelectedCharity(Long id, String name, Long currentPoints, Long numberOfDonors, String about, String site, String phone, List<String> programs) {
}
