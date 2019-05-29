import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Friend} from '../friend.model';
import {Observable} from 'rxjs';
import {Book} from '../../library/book.model';
import {TokenStorageService} from '../../authentication/services/token-storage.service';

const BASE_URL: string = 'http://localhost:8080/api/friends';
const LENT_BOOKS: string = 'http://localhost:8080/api/books/lent';
const USER_ID: string = '?userId=';

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

  getFriendById(friendId: number) {
    const url = BASE_URL + '/' + friendId;
    return this.httpClient.get<Friend>(url);
  }

  getAllLentBooks(): Observable<Book[]> {
    const url = LENT_BOOKS + USER_ID + this.userId;
    return this.httpClient.get<Book[]>(url);
  }

  saveFriend(friend: Friend) {
    return this.httpClient.post<number>(BASE_URL, friend);
  }

}
