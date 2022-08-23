package recipes.businesslayer;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    @Pattern(regexp = ".+@.+\\..+")
    private String email;

    @NotBlank
    @Length(min = 8)
    private String password;

    @ElementCollection
    private List<Long> recipesList;

    private String role;

    public void addId(Long idd) {
        recipesList.add(idd);
    }

    public boolean isIdValid(Long id) {
        for (Long idd : recipesList) {
            System.out.println(idd);
            if (Objects.equals(idd, id)) {
                return true;
            }
        }
        return false;
    }
}
