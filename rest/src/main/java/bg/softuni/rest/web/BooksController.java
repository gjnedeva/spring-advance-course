package bg.softuni.rest.web;

import bg.softuni.rest.model.Author;
import bg.softuni.rest.model.Book;
import bg.softuni.rest.repository.AuthorRepository;
import bg.softuni.rest.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksController implements AuthorsNamespace {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;

  public BooksController(BookRepository bookRepository,
                         AuthorRepository authorRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
  }

  @GetMapping("/{authorId}/books")
  public ResponseEntity<List<Book>> getAuthorBooks(@PathVariable Long authorId) {
    Optional<Author> author = authorRepository.findById(authorId);

    return author.
            map(Author::getBooks).
            map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/{authorId}/books/{bookId}")
  public ResponseEntity<Book> getBook(@PathVariable Long authorId,
                                      @PathVariable Long bookId) {
    Optional<Book> theBook = bookRepository.findById(bookId);

//    only if th author is the same
    return theBook.filter(b -> b.getAuthor().getId() == authorId).
            map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }


}