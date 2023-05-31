package edu.tanta.fci.reoil.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewCharity {

  @NotNull
  @NotBlank
  @Length(min = 3, max = 50)
  private String name;

  @NotNull
  @NotBlank
  @Size(max = 1000)
  private String description;

  @NotNull
  @NotBlank
  @Size(max = 1000)
  private String about;

  @NotNull
  @NotBlank
  @Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?$", message = "Invalid URL")
  private String site;

  @NotNull
  @NotBlank
  private String phone;

  @Size(min = 1)
  private List<String> programs;


}
