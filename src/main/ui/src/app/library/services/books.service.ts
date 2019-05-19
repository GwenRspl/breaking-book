import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Book} from '../book.model';
import {GoogleApiQueryResult} from '../search-via-api/googleApiQueryResult.model';

@Injectable({
  providedIn: 'root'
})
export class BooksService {

  private baseURL: string = 'http://localhost:8080/api/books';
  private googleApiUrl: string = 'https://www.googleapis.com/books/v1/volumes?q=';
  private googleApiKey = '&key=AIzaSyDYsB7DhlSW_l-Mn9WjmdPYm4dafvL1ly0';

  private selectedBook: Book;
  private bookReadyToPopulate = false;

  constructor(private httpClient: HttpClient) {
  }

  getSelectedBook() {
    return this.selectedBook;
  }

  setSelectedBook(book: Book) {
    this.selectedBook = book;
    this.bookReadyToPopulate = true;
  }

  isBookReadyToPopulate() {
    return this.bookReadyToPopulate;
  }

  setIsBookReadyToPopulate(bool: boolean) {
    this.bookReadyToPopulate = bool;
  }


  getBooks(userId: number) {
    const url = this.baseURL + '?userId=' + userId;
    return this.httpClient.get<Book[]>(url);
  }

  saveBook(book: Book) {
    return this.httpClient.post<number>(this.baseURL, book);
  }

  getBookById(bookId: number) {
    const url = this.baseURL + '/' + bookId;
    return this.httpClient.get<Book>(url);
  }

  searchBookViaGoogleApi(mode: string, searchInput: string) {
    const url = this.googleApiUrl + mode + ':' + searchInput + this.googleApiKey;
    console.log(url);
    return this.httpClient.get<GoogleApiQueryResult>(url);
  }
}
