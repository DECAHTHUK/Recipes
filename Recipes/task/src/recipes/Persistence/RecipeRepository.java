package recipes.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.businesslayer.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAll();
}