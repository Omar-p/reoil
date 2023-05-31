package edu.tanta.fci.reoil.bootstrap;

import edu.tanta.fci.reoil.catalog.CatalogRepository;
import edu.tanta.fci.reoil.catalog.Item;
import edu.tanta.fci.reoil.domain.Charity;
import edu.tanta.fci.reoil.domain.Program;
import edu.tanta.fci.reoil.domain.security.Authority;
import edu.tanta.fci.reoil.domain.security.Role;
import edu.tanta.fci.reoil.repositories.CharityRepository;
import edu.tanta.fci.reoil.repositories.RoleRepository;
import edu.tanta.fci.reoil.repositories.UserRepository;
import edu.tanta.fci.reoil.user.entities.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Configuration
@Transactional

public class DataLoader {
  @Bean
  public CommandLineRunner loadder(RoleRepository roleRepository, CharityRepository charityRepository, UserRepository repository,
                                   PasswordEncoder encoder, CatalogRepository catalogRepository) {
    return (args) -> {

      if (catalogRepository.count() == 0) {
        var item = new Item();
        item.setPoints(100L);
        item.setName("زيت طعام");
        item.setQuantity(1.5);
        item.setUnit("لتر");
        item.setImageUrl("https://post.healthline.com/wp-content/uploads/2020/08/AN168-oil-frying-pan-732x549-Thumb-1-732x549.jpg");
        catalogRepository.save(item);
      }

      if (roleRepository.count() == 0) {
        final Authority userRead = Authority.Builder.anAuthority()
            .withPermission("user.info.read")
            .build();

        final Role userRole = Role.Builder.aRole()
            .authorities(Set.of(userRead))
            .name("ROLE_USER")
            .build();

        roleRepository.save(userRole);


        final Authority charityWrite = Authority.Builder.anAuthority()
            .withPermission("charity:write")
            .build();

        final Authority charityRead = Authority.Builder.anAuthority()
            .withPermission("charity:read")
            .build();

        final Role adminRole = Role.Builder.aRole()
            .authorities(Set.of(charityWrite, charityRead))
            .name("ROLE_ADMIN")
            .build();


        roleRepository.save(adminRole);


      }

      if (repository.count() == 0) {
        User user = new User(
            "user",
            "user@gmail.com",

            encoder.encode("user"),
            "01234456789",
            true);
        user.setPoints(100L);
        user = repository.save(user);
        final Role roleUser = roleRepository.findByName("ROLE_USER").get();
        user.addRole(roleUser);
        repository.save(user);

        User admin = new User(
            "admin",
            "admin@gmail.com",
            encoder.encode("admin"),
            "01234456789",
            true
        );
        admin = repository.save(admin);
        admin.addRole(roleRepository.findByName("ROLE_ADMIN").get());
        repository.save(admin);


      }
      if (charityRepository.count() == 0) {
        final Charity charity = new Charity(
            "مؤسسة مجدى يعقوب للقلب",
            "مؤسسة خيرية",
            "تعتمد مؤسسة مجدى يعقوب للقلب على التبرع والدعم المالي للقيام بمهمتها وانقاذ قلوب أطفال حيث اننا نطمح ونعمل على توسيع نطاق عملياتنا لتلبية الطلب الكبير والمتزايد على علاج القلب والأوعية الدموية للذين هم في أمس الحاجة إليه، وذلك إلى جانب تطوير العلاج والبحث واستثمار المواهب، بطريقة غير مسبوقة في المنطقة.",
            "email@email.com",
            "029876542"
        );
        var programs = List.of(
            "برنامج القلب الأول",
            "برنامج خيري",
            "برنامج خيري يهدف إلى توفير العلاج للأطفال الذين يعانون من أمراض القلب والأوعية الدموية"
        );
        programs.stream()
            .map(Program::new)
            .forEach(charity::addProgram);
        charityRepository.save(charity);
      }


    };
  }
}
