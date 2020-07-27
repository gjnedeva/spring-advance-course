package bg.softuni.rest.web;

import bg.softuni.rest.model.Author;
import bg.softuni.rest.repository.AuthorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@CrossOrigin("http://localhost")
@RestController
public class AuthorsController implements AuthorsNamespace {

  private final AuthorRepository authorRepository;

  public AuthorsController(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  @GetMapping
  public List<Author> getAuthors() {
    return authorRepository.findAll();
  }

  @GetMapping("/{authorId}")
  public ResponseEntity<Author> getAuthor(@PathVariable Long authorId) {
    Optional<Author> theAuthor = authorRepository.findById(authorId);

//    this will return 404 if not found Author
    return theAuthor.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Author> create(
          UriComponentsBuilder ucBuilder,
          @RequestBody Author author
  ) {
    Author newAuthor = authorRepository.save(author);

//    status 201 -> created
    return ResponseEntity.
            created(ucBuilder.path("/authors/{authorId}").buildAndExpand(newAuthor.getId()).toUri()).build();
  }

  @DeleteMapping("/{authorId}")
  public ResponseEntity<Author> delete(@PathVariable Long authorId) {
    authorRepository.deleteById(authorId);
    return ResponseEntity.noContent().build();
  }
}