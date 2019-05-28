import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Friend} from '../friend.model';
import {Observable} from 'rxjs';

const BASE_URL: string = 'http://localhost:8080/api/friends';
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
}
