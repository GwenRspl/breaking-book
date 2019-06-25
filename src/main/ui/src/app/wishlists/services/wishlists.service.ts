import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TokenStorageService} from '../../authentication/services/token-storage.service';
import {Wishlist} from '../wishlist.model';
import {BASE_URL_API} from '../../../environments/environment';

const BASE_URL = BASE_URL_API + '/wishlists';
const USER_ID_PARAM = '?userId=';
const BOOK_ID_PARAM = '?bookId=';
const HTTP_OPTIONS = {headers: new HttpHeaders({'Content-Type': 'application/json',}), responseType: 'text' as 'json'};


@Injectable({
  providedIn: 'root'
})
export class WishlistsService {
  private userId: number;

  constructor(private httpClient: HttpClient,
              private tokenStorage: TokenStorageService) {
    this.setUserId();
  }

  setUserId() {
    this.userId = +this.tokenStorage.getUserId();
  }

  getWishlists(): Observable<Wishlist[]> {
    const url = BASE_URL + USER_ID_PARAM + this.userId;
    return this.httpClient.get<Wishlist[]>(url);
  }

  saveWishlist(wishlist: Wishlist): Observable<string> {
    return this.httpClient.post<string>(BASE_URL, wishlist, HTTP_OPTIONS);
  }

  deleteWishlist(wishlistId: number): Observable<string> {
    const url = BASE_URL + '/' + wishlistId;
    return this.httpClient.delete<string>(url, HTTP_OPTIONS);
  }

  addBookToWishlist(wishlistId: number, bookId: number): Observable<string> {
    const url = BASE_URL + /add/ + wishlistId;
    return this.httpClient.post<string>(url, bookId, HTTP_OPTIONS);
  }

  deleteBookFromWishlist(wishlistId: number, bookId: number): Observable<string> {
    const url = BASE_URL + '/remove/' + wishlistId + BOOK_ID_PARAM + bookId;
    return this.httpClient.delete<string>(url, HTTP_OPTIONS);
  }

  renameWishlist(wishlist: Wishlist): Observable<string> {
    const url = BASE_URL + '/' + wishlist.id;
    return this.httpClient.put<string>(url, wishlist, HTTP_OPTIONS);
  }
}
