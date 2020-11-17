$('#loadAuthors').click(() => {

    $('.authors-container').empty();
    $('.books-container').empty();

    fetch('http://localhost:8080/authors') // Fetch the data (GET request)
        .then((response) => response.json()) // Extract the JSON from the Response
        .then((json) => json.forEach((author, idx) => {// Render the JSON data to the HTML
            let id = author.id;
            debugger;
            let tableRow =
                '<tr>' +
                '<td>' + author.id + '</td>' +
                '<td>' + author.name + '</td>' +
                '<td><button class="book-btn" data-author-id="' + author.id + '" onclick="refreshBooks($(this).data(\'author-id\'))">Show books</button></td>' +
                '<td>' +
                '<button class="detele-btn-author" data-author-id="' + author.id + '">Delete</button>' +
                '</td>' +
                '</tr>';

            $('.authors-container').append(tableRow);
        }));
});

$('body').on('click', 'button.detele-btn-author', function () {
    let authorId = $(this).data('author-id');
    fetch('http://localhost:8080/authors/' + authorId,
        {
            method: 'DELETE',
        }).then((response) => response.json()).then($('#loadAuthors').click())// Extract the JSON from the Response
});

function refreshBooks(authorId) {
        // authorId = $(this).data('author-id');

        console.log("Author ID is " + authorId)

        $('.books-container').empty();

        fetch('http://localhost:8080/authors/' + authorId + '/books') // Fetch the data (GET request)
            .then((response) => response.json()) // Extract the JSON from the Response
            .then((json) => json.forEach((book, idx) => { // Render the JSON data to the HTML
                console.log(book.title);

                let tableRow =
                    '<tr>' +
                    '<td>' + book.title + '</td>' +
                    '<td>' +
                    '<button class="delete-btn-book" data-author-id="' + authorId + '" data-book-id="' + book.id + '">Delete</button>' +
                    '</td>' +
                    '</tr>';

                $('.books-container').append(tableRow);
            }));
}

$('body').on('click', 'button.delete-btn-book', function () {
    debugger;
    let authorId = $(this).data('author-id');
    let bookID = $(this).data('book-id');

    $('.books-container').empty();

    let td = document.getElementById("myBtn");

    fetch('http://localhost:8080/authors/' + authorId + '/books/' + bookID,
        {
            method: 'DELETE',
        },) // Fetch the data (GET request)
        .then((response) => response.json()).then(refreshBooks(authorId)) // Extract the JSON from the Response
});
