import {Component, Input, OnInit} from '@angular/core';
import {Book} from '../../book.model';
import {ModalController} from '@ionic/angular';
import {BooksService} from '../../services/books.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-google-api-book-modal',
  templateUrl: './google-api-book-modal.component.html',
  styleUrls: ['./google-api-book-modal.component.scss'],
})
export class GoogleApiBookModalComponent implements OnInit {
  @Input() book: Book;

  constructor(private modalCtrl: ModalController,
              private booksService: BooksService,
              private router: Router) {
  }


  ngOnInit() {
  }

  selectBook() {
    this.booksService.setSelectedBook(this.book);
    this.modalCtrl.dismiss();
    this.router.navigateByUrl('/library/new');
  }

  dismiss() {
    this.modalCtrl.dismiss();
  }
}
