package recipes.Gto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeGto {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotEmpty
    @Size(min = 1)
    private List<String> ingredients, directions;

    @NotBlank
    private String category;
}
