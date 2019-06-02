import {Component, Input, OnInit} from '@angular/core';
import {Book} from '../../../library/book.model';
import {BooksService} from '../../../library/services/books.service';
import {ModalController} from '@ionic/angular';
import {Collection} from '../../../collections/collection.model';

@Component({
  selector: 'app-lend-book-modal',
  templateUrl: './choose-book-modal.component.html',
  styleUrls: ['./choose-book-modal.component.scss'],
})
export class ChooseBookModalComponent implements OnInit {
  @Input() modalMode: string;
  @Input() collection: Collection;
  @Input() wishlist: number;
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
        if (this.modalMode == 'lendBook') {
          this._books = data.filter(book => book.owned == true && book.friendId == null);
        } else if (this.modalMode == 'collection') {
          if (this.collection.booksIds != null) {
            this._books = data.filter(book => !this.collection.booksIds.includes(book.id));
          } else {
            this._books = data;
          }
        } else {
          this._books = data.filter(book => book.owned == true && book.friendId == null);
        }
      },
      error => console.log(error)
    );

  }

  chooseBook(bookId: number) {
    this.modalCtrl.dismiss(bookId, 'book');
  }
}
