import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Friend} from '../friend.model';
import {Observable} from 'rxjs';
import {Book} from '../../library/book.model';
import {TokenStorageService} from '../../authentication/services/token-storage.service';

const BASE_URL: string = 'http://localhost:8080/api/friends';
const BOOKS_URL: string = 'http://localhost:8080/api/books';
const USER_ID: string = '?userId=';
const HTTP_OPTIONS = {headers: new HttpHeaders({'Content-Type': 'application/json',}), responseType: 'text' as 'json'};

@Injectable({
  providedIn: 'root'
})
export class FriendsService {
  private readonly userId: number;

  constructor(private httpClient: HttpClient,
              private tokenStorage: TokenStorageService) {
    this.userId = +this.tokenStorage.getUserId()
    ;
  }

  getAllFriends(): Observable<Friend[]> {
    const url = BASE_URL + USER_ID + this.userId;
    return this.httpClient.get<Friend[]>(url);
  }

  getFriendById(friendId: number): Observable<Friend> {
    const url = BASE_URL + '/' + friendId;
    return this.httpClient.get<Friend>(url);
  }

  getAllLentBooks(): Observable<Book[]> {
    const url = BOOKS_URL + '/lent' + USER_ID + this.userId;
    return this.httpClient.get<Book[]>(url);
  }

  saveFriend(friend: Friend): Observable<number> {
    return this.httpClient.post<number>(BASE_URL, friend);
  }

  lendBookToFriend(friendId: number, bookId: number): Observable<string> {
    const url = BOOKS_URL + '/lend/' + bookId;
    return this.httpClient.put<string>(url, friendId, HTTP_OPTIONS);
  }

  getBackBookFromFriend(bookId: number): Observable<string> {
    const url = BOOKS_URL + '/get-back/' + bookId;
    return this.httpClient.get<string>(url, HTTP_OPTIONS);
  }

  deleteFriend(friendId: number): Observable<string> {
    const url = BASE_URL + '/' + friendId;
    return this.httpClient.delete<string>(url, HTTP_OPTIONS);
  }

}
