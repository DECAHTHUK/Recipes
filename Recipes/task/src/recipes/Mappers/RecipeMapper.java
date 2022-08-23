package recipes.Mappers;

import org.springframework.stereotype.Component;
import recipes.Gto.RecipeGto;
import recipes.businesslayer.Recipe;

import java.time.LocalDateTime;

@Component
public class RecipeMapper {
    public Recipe toRecipe(RecipeGto gto) {
        Recipe recipe = new Recipe();
        recipe.setName(gto.getName());
        recipe.setDescription(gto.getDescription());
        recipe.setIngredients(gto.getIngredients());
        recipe.setDirections(gto.getDirections());
        recipe.setCategory(gto.getCategory());
        recipe.setDate(LocalDateTime.now());
        return recipe;
    }
}
