import {Component, OnInit} from '@angular/core';
import {Book} from '../library/book.model';
import {Wishlist} from './wishlist.model';
import {BooksService} from '../library/services/books.service';
import {Router} from '@angular/router';
import {TokenStorageService} from '../authentication/services/token-storage.service';
import {WishlistsService} from './services/wishlists.service';

@Component({
  selector: 'app-wishlists',
  templateUrl: './wishlists.page.html',
  styleUrls: ['./wishlists.page.scss'],
})
export class WishlistsPage implements OnInit {
  userInput: string = '';
  private _wishlists: Wishlist[] = [];
  private _booksWishlistsMap: Map<number, Book[]> = new Map<number, Book[]>();

  constructor(private wishlistsService: WishlistsService,
              private booksService: BooksService,
              private router: Router,
              private tokenStorage: TokenStorageService) {
  }


  get wishlists(): Wishlist[] {
    return this._wishlists;
  }

  get booksWishlistsMap(): Map<number, Book[]> {
    return this._booksWishlistsMap;
  }

  ngOnInit() {
    this.retrieveWishlists();
  }

  retrieveWishlists() {
    this.wishlistsService.getWishlists().subscribe(
      data => {
        console.log(data);
        this._wishlists = data;
        this.booksService.getBooks().subscribe(
          data => {
            this.initMap(data);
          }
        )
      },
      error => console.log(error)
    );
  }

  initMap(books: Book[]) {
    this._booksWishlistsMap = new Map<number, Book[]>();
    this.wishlists.forEach(wishlist => {
      if (wishlist.booksIds != null) {
        let bookList: Book[] = [];
        books.forEach(book => {
          if (wishlist.booksIds.includes(book.id)) {
            bookList.push(book);
          }
        });
        this._booksWishlistsMap.set(wishlist.id, bookList);
      }
    });
  }

  createNewWishlist() {
    if (this.userInput == '') {
      return;
    }
    let wishlist = new Wishlist(this.userInput, +this.tokenStorage.getUserId());
    this.wishlistsService.saveWishlist(wishlist).subscribe(
      () => this.retrieveWishlists(),
      error => console.log(error)
    );
  }

}
