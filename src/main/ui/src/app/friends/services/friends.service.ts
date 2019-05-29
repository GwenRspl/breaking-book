import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Friend} from '../friend.model';
import {Observable} from 'rxjs';
import {Book} from '../../library/book.model';

const BASE_URL: string = 'http://localhost:8080/api/friends';
const LENT_BOOKS: string = 'http://localhost:8080/api/books/lent';
const USER_ID: string = '?userId=';

@Injectable({
  providedIn: 'root'
})
export class FriendsService {

  constructor(private httpClient: HttpClient) {
  }

  getAllFriends(userId: number): Observable<Friend[]> {
    const url = BASE_URL + USER_ID + userId;
    return this.httpClient.get<Friend[]>(url);
  }

  getFriendById(friendId: number) {
    const url = BASE_URL + '/' + friendId;
    return this.httpClient.get<Friend>(url);
  }

  getAllLentBooks(userId: number): Observable<Book[]> {
    const url = LENT_BOOKS + USER_ID + userId;
    return this.httpClient.get<Book[]>(url);
  }

  saveFriend(friend: Friend) {
    return this.httpClient.post<number>(BASE_URL, friend);
  }

}
