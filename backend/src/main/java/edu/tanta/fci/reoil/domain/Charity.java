package edu.tanta.fci.reoil.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Charity {

  @SequenceGenerator(
      name = "charity_id_generator",
      allocationSize = 1,
      sequenceName = "charity_id_seq"
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "charity_id_generator"
  )
  @Id
  private Long id;

  private String name;

  private String description;

 @Column(length = 2048)
  private String about;

  private String site;

  private String phone;

  private String imageUriId;

  @Formula("(SELECT count(distinct d.user_id)  FROM donation d WHERE d.charity_id = id GROUP BY d.charity_id)")
  private Long numberOfDonors;

  @Formula("(SELECT SUM(d.amount) FROM donation d WHERE d.charity_id = id)")
  private Long points;

  @OneToMany(mappedBy = "charity", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Program> programs = new ArrayList<>();

  public Charity(String name, String description, String about, String site, String phone) {
    this.name = name;
    this.description = description;
    this.about = about;
    this.site = site;
    this.phone = phone;
  }

  public void addProgram(Program program) {
    programs.add(program);
    program.setCharity(this);
  }
}
