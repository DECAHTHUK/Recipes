package recipes.businesslayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import recipes.Gto.RecipeGto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "recipies")
public class Recipe {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name, description;

    @ElementCollection
    private List<String> ingredients, directions;

    private String category;

    private LocalDateTime date;

    public void setRecipeFields(RecipeGto recipeGto) {
        this.name = recipeGto.getName();
        this.description = recipeGto.getDescription();
        this.ingredients = recipeGto.getIngredients();
        this.directions = recipeGto.getDirections();
        this.category = recipeGto.getCategory();
        this.date = LocalDateTime.now();
    }
}
