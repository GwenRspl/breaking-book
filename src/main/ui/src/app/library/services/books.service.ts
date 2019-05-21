import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Book} from '../book.model';
import {GoogleApiQueryResult} from '../search-via-api/googleApiQueryResult.model';
import {Collection} from '../../collections/collection.model';
import {Observable} from 'rxjs';

const BASE_URL: string = 'http://localhost:8080/api/books';
const COLLECTIONS_URL: string = 'http://localhost:8080/api/collections';
const GOOGLE_API_URL: string = 'https://www.googleapis.com/books/v1/volumes?q=';
const GOOGLE_API_KEY = '&key=AIzaSyDYsB7DhlSW_l-Mn9WjmdPYm4dafvL1ly0';
const GOOGLE_API_FIELDS = '&fields=totalItems,items(selfLink,volumeInfo(title,subtitle,authors,publisher,publishedDate,description,industryIdentifiers,pageCount,imageLinks,language))';
const GOOGLE_API_MAX_RESULTS = '&maxResults=40';
const USER_ID_PARAM = '?userId=';

@Injectable({
  providedIn: 'root'
})
export class BooksService {

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
    const url = BASE_URL + USER_ID_PARAM + userId;
    return this.httpClient.get<Book[]>(url);
  }

  saveBook(book: Book) {
    return this.httpClient.post<number>(BASE_URL, book);
  }

  getBookById(bookId: number) {
    const url = BASE_URL + '/' + bookId;
    return this.httpClient.get<Book>(url);
  }

  searchBookViaGoogleApi(mode: string, searchInput: string) {
    const url = GOOGLE_API_URL + mode + ':' + searchInput + GOOGLE_API_KEY + GOOGLE_API_FIELDS + GOOGLE_API_MAX_RESULTS;
    console.log(url);
    return this.httpClient.get<GoogleApiQueryResult>(url);
  }

  getCollections(userId: number): Observable<Collection[]> {
    const url = COLLECTIONS_URL + USER_ID_PARAM + userId;
    return this.httpClient.get<Collection[]>(url);
  }
}
