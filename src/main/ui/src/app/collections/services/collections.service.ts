import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Collection} from '../collection.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {TokenStorageService} from '../../authentication/services/token-storage.service';

const BASE_URL: string = 'http://localhost:8080/api/collections';
const USER_ID_PARAM = '?userId=';
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
}
