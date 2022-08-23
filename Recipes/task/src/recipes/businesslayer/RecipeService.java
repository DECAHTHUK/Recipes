package recipes.businesslayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import recipes.Gto.RecipeGto;
import recipes.Persistence.RecipeRepository;
import recipes.Response.RecipeResponce;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe findRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        return recipe;
    }

    public RecipeResponce save(Recipe toSave) {
        recipeRepository.save(toSave);
        return new RecipeResponce(toSave.getId());
    }

    public void updateRecipe(long id, RecipeGto newRecipe) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        recipe.setRecipeFields(newRecipe);
        recipeRepository.save(recipe);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public List<Recipe> findRecipesByParam(Optional<String> category, Optional<String> name) {
        List<Recipe> recipeList = recipeRepository.findAll();
        recipeList.sort(new Comparator<Recipe>() {
            @Override
            public int compare(Recipe o1, Recipe o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        if (category.isPresent()) {
            return recipeList.stream().filter(t -> Objects.equals(t.getCategory().toLowerCase(Locale.ROOT),
                    category.get().toLowerCase(Locale.ROOT))).collect(Collectors.toList());
        }
        else {
            String regex = ".*" + name.get().toLowerCase(Locale.ROOT) + ".*";
            return recipeList.stream().filter(t -> t.getName().toLowerCase(Locale.ROOT).matches(regex))
                    .collect(Collectors.toList());
        }
    }

    public boolean doWeHaveSomethingInDb() {
        return recipeRepository.count() > 0;
    }

    public void deleteReceipeById(Long id) {
        recipeRepository.delete(findRecipeById(id));
    }
}
