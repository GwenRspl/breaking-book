import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Collection} from '../collection.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {TokenStorageService} from '../../authentication/services/token-storage.service';
import {BASE_URL_API} from '../../../environments/environment';

const BASE_URL = BASE_URL_API + '/collections';
const USER_ID_PARAM = '?userId=';
const BOOK_ID_PARAM = '?bookId=';
const HTTP_OPTIONS = {headers: new HttpHeaders({'Content-Type': 'application/json',}), responseType: 'text' as 'json'};


@Injectable({
  providedIn: 'root'
})
export class CollectionsService {
  private readonly userId: number;

  constructor(private httpClient: HttpClient,
              private tokenStorage: TokenStorageService) {
    this.userId = +this.tokenStorage.getUserId();
  }

  getCollections(): Observable<Collection[]> {
    const url = BASE_URL + USER_ID_PARAM + this.userId;
    return this.httpClient.get<Collection[]>(url);
  }

  saveCollection(collection: Collection): Observable<string> {
    return this.httpClient.post<string>(BASE_URL, collection, HTTP_OPTIONS);
  }

  deleteCollection(collectionId: number): Observable<string> {
    const url = BASE_URL + '/' + collectionId;
    return this.httpClient.delete<string>(url, HTTP_OPTIONS);
  }

  addBookToCollection(collectionId: number, bookId: number): Observable<string> {
    const url = BASE_URL + /add/ + collectionId;
    return this.httpClient.post<string>(url, bookId, HTTP_OPTIONS);
  }

  deleteBookFromCollection(collectionId: number, bookId: number): Observable<string> {
    const url = BASE_URL + '/remove/' + collectionId + BOOK_ID_PARAM + bookId;
    return this.httpClient.delete<string>(url, HTTP_OPTIONS);
  }

  renameCollection(collection: Collection): Observable<string> {
    const url = BASE_URL + '/' + collection.id;
    return this.httpClient.put<string>(url, collection, HTTP_OPTIONS);
  }
}
