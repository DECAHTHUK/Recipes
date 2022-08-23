package recipes.Presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.Gto.RecipeGto;
import recipes.Mappers.RecipeMapper;
import recipes.Persistence.UserRepository;
import recipes.Response.RecipeResponce;
import recipes.businesslayer.Recipe;
import recipes.businesslayer.RecipeService;
import recipes.businesslayer.User;
import recipes.businesslayer.UserDetailsServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api")
@RestController
public class RecipeController {
    long idCounter = 1;
    @Autowired
    RecipeService recipeService;
    @Autowired
    RecipeMapper recipeMapper;
    @Autowired
    UserDetailsServiceImpl userService;

    @PostMapping(value = "/recipe/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public RecipeResponce postRecipe(@Valid @RequestBody RecipeGto recipe, @AuthenticationPrincipal UserDetails details) {
        RecipeResponce responce = recipeService.save(recipeMapper.toRecipe(recipe));
        userService.updateUserByEmail(details.getUsername(), responce.getId());
        return responce;
    }

    @GetMapping(value = "/recipe/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Recipe getRecipe(@PathVariable long id) {
        return recipeService.findRecipeById(id);
    }

    @DeleteMapping("/recipe/{id}")
    public void deleteRecipe(@PathVariable long id, @AuthenticationPrincipal UserDetails details) {
        if (recipeService.findRecipeById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        else if (!userService.findUserByEmail(details.getUsername()).isIdValid(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipeService.deleteReceipeById(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/recipe/{id}")
    public void updateRecipe(@PathVariable(value = "id") long id, @Valid @RequestBody RecipeGto recipe,
                             @AuthenticationPrincipal UserDetails details) {
        System.out.println("PUT: " + userService.findUserByEmail(details.getUsername()));
        if (!userService.findUserByEmail(details.getUsername()).isIdValid(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipeService.updateRecipe(id, recipe);
    }

    @GetMapping(value = "/recipe/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Recipe> searchRecipe(@RequestParam Optional<String> category, @RequestParam Optional<String> name) {
        if ((category.isEmpty() && name.isEmpty()) || (category.isPresent() && name.isPresent())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else {
            return recipeService.findRecipesByParam(category, name);
        }
    }
}
