import {Component, Input, OnInit} from '@angular/core';
import {ModalController} from '@ionic/angular';
import {Router} from '@angular/router';

@Component({
  selector: 'app-delete-book-modal',
  templateUrl: './delete-book-modal.component.html',
  styleUrls: ['./delete-book-modal.component.scss'],
})
export class DeleteBookModalComponent implements OnInit {
  @Input() step1: boolean;

  constructor(private modalCtrl: ModalController,
              private router: Router) {
  }

  ngOnInit() {
  }

  deleteBook() {
    this.modalCtrl.dismiss(null, 'delete');
  }

  dismissModal() {
    this.modalCtrl.dismiss();
  }

  goToLibrary() {
    this.router.navigateByUrl('/library');
    this.modalCtrl.dismiss();
  }

}
