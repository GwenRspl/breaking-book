import {Component, Input, OnInit} from '@angular/core';
import {Book} from '../../book.model';
import {ModalController} from '@ionic/angular';

@Component({
  selector: 'app-google-api-book-modal',
  templateUrl: './google-api-book-modal.component.html',
  styleUrls: ['./google-api-book-modal.component.scss'],
})
export class GoogleApiBookModalComponent implements OnInit {
  @Input() book: Book;

  constructor(private modalCtrl: ModalController) {
  }


  ngOnInit() {
  }

  selectBook() {

  }

  dismiss() {
    this.modalCtrl.dismiss();
  }
}
