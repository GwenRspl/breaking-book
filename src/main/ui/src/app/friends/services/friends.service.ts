import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Friend} from '../friend.model';

const BASE_URL: string = 'http://localhost:8080/api/friends';

@Injectable({
  providedIn: 'root'
})
export class FriendsService {

  constructor(private httpClient: HttpClient) {
  }

  getFriendById(friendId: number) {
    const url = BASE_URL + '/' + friendId;
    return this.httpClient.get<Friend>(url);
  }
}
