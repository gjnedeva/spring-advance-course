package bg.softuni.rest.web;

import bg.softuni.rest.model.Author;
import bg.softuni.rest.model.Book;
import bg.softuni.rest.repository.AuthorRepository;
import bg.softuni.rest.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.text.html.Option;

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

  @PostMapping(path = "{authorId}/books")
  public ResponseEntity<Author> create(
          UriComponentsBuilder ucBuilder,
          @RequestBody Book book, @PathVariable Long authorId) {
    Optional<Author> findAuthor = authorRepository.findById(authorId);
    if (findAuthor.isPresent()) {

      Book book1 = new Book();
      book1.setAuthor(findAuthor.get());
      book1.setTitle(book.getTitle());
      bookRepository.save(book1);
      return ResponseEntity.
              created(ucBuilder.path("/books/{bookId}").buildAndExpand(book.getId()).toUri()).build();
    }
    return ResponseEntity.notFound().build();

  }

}