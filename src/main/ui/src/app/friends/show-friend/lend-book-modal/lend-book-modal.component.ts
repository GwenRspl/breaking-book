import {Component, OnInit} from '@angular/core';
import {Book} from '../../../library/book.model';
import {BooksService} from '../../../library/services/books.service';
import {ModalController} from '@ionic/angular';

@Component({
  selector: 'app-lend-book-modal',
  templateUrl: './lend-book-modal.component.html',
  styleUrls: ['./lend-book-modal.component.scss'],
})
export class LendBookModalComponent implements OnInit {
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
      data => this._books = data.filter(book => book.owned == true && book.friendId == null),
      error => console.log(error)
    );

  }

  chooseBook(bookId: number) {
    this.modalCtrl.dismiss(bookId, 'book');
  }
}
