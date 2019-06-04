import {Component, Input, OnInit} from '@angular/core';
import {ModalController} from '@ionic/angular';
import {Book} from '../../../library/book.model';
import {BooksService} from '../../../library/services/books.service';
import {Collection} from '../../../collections/collection.model';
import {Wishlist} from '../../../wishlists/wishlist.model';

@Component({
  selector: 'app-lend-book-modal',
  templateUrl: './choose-book-modal.component.html',
  styleUrls: ['./choose-book-modal.component.scss'],
})
export class ChooseBookModalComponent implements OnInit {
  @Input() modalMode: string;
  @Input() collection: Collection;
  @Input() wishlist: Wishlist;
  private _books: Book[] = [];

  constructor(private booksService: BooksService,
              private modalCtrl: ModalController) {
  }


  get books(): Book[] {
    return this._books;
  }

  ngOnInit() {
    this.retrieveBooks();
  }

  retrieveBooks() {
    this.booksService.getBooks().subscribe(
      data => {
        switch (this.modalMode) {
          case 'lendBook':
            this._books = data.filter(book => book.owned == true && book.friendId == null);
            break;
          case 'collection':
            if (this.collection.booksIds != null) {
              this._books = data.filter(book => !this.collection.booksIds.includes(book.id));
            } else {
              this._books = data;
            }
            break;
          case 'wishlist':
            if (this.wishlist.booksIds != null) {
              this._books = data.filter(book => book.owned == false && !this.wishlist.booksIds.includes(book.id));
            } else {
              this._books = data.filter(book => book.owned == false);
            }
            break;
        }
      },
      error => console.log(error)
    );

  }

  chooseBook(bookId: number) {
    this.modalCtrl.dismiss(bookId, 'book');
  }
}
