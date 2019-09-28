import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Book} from '../book.model';
import {GoogleApiQueryResult} from '../search-via-api/googleApiQueryResult.model';
import {Collection} from '../../collections/collection.model';
import {Observable} from 'rxjs';
import {TokenStorageService} from '../../authentication/services/token-storage.service';
import {BASE_URL_API} from '../../../environments/environment';

const BASE_URL = BASE_URL_API + '/books';
const COLLECTIONS_URL = BASE_URL_API + '/collections';
const GOOGLE_API_URL = 'https://www.googleapis.com/books/v1/volumes?q=';
const GOOGLE_API_FIELDS = '&fields=totalItems,items(selfLink,volumeInfo(title,subtitle,authors,publisher,publishedDate,description,industryIdentifiers,pageCount,imageLinks,language))';
const GOOGLE_API_MAX_RESULTS = '&maxResults=40';
const USER_ID_PARAM = '?userId=';
const HTTP_OPTIONS = {headers: new HttpHeaders({'Content-Type': 'application/json'}), responseType: 'text' as 'json'};

@Injectable({
  providedIn: 'root'
})
export class BooksService {
  private userId: number;
  private googleApiKey: string;
  private selectedBook: Book;
  private bookReadyToPopulate = false;

  constructor(private httpClient: HttpClient,
              private tokenStorage: TokenStorageService) {
    this.setUserId();
    this.getGoogleApiKey().subscribe(
        (data:string) => { this.googleApiKey = data;
        console.log(this.googleApiKey);
        })
  }

  setUserId() {
    this.userId = +this.tokenStorage.getUserId();
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


  getBooks() {
    const url = BASE_URL + USER_ID_PARAM + this.userId;
    return this.httpClient.get<Book[]>(url);
  }

  saveBook(book: Book) {
    return this.httpClient.post<number>(BASE_URL, book);
  }

  getBookById(bookId: number) {
    const url = BASE_URL + '/' + bookId;
    return this.httpClient.get<Book>(url);
  }

  getGoogleApiKey() {
    const url= BASE_URL_API + "/key";
    return this.httpClient.get<String>(url, HTTP_OPTIONS);
  }

  searchBookViaGoogleApi(mode: string, searchInput: string) {
    const url = GOOGLE_API_URL + mode + ':' + searchInput + this.googleApiKey + GOOGLE_API_FIELDS + GOOGLE_API_MAX_RESULTS;
    return this.httpClient.get<GoogleApiQueryResult>(url);
  }

  getCollections(): Observable<Collection[]> {
    const url = COLLECTIONS_URL + USER_ID_PARAM + this.userId;
    return this.httpClient.get<Collection[]>(url);
  }

  deleteBook(bookId: number): Observable<string> {
    const url = BASE_URL + '/' + bookId;
    return this.httpClient.delete<string>(url, HTTP_OPTIONS);
  }

  updateBook(book: Book): Observable<string> {
    const url = BASE_URL + '/' + book.id;
    return this.httpClient.put<string>(url, book, HTTP_OPTIONS);
  }


}
