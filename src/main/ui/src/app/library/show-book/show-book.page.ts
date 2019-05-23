import {Component, OnInit} from '@angular/core';
import {Book} from '../book.model';
import {BooksService} from '../services/books.service';
import {ActivatedRoute} from '@angular/router';
import {Friend} from '../../friends/friend.model';
import {FriendsService} from '../../friends/services/friends.service';
import {ModalController} from '@ionic/angular';
import {DeleteBookModalComponent} from './delete-book-modal/delete-book-modal.component';


@Component({
  selector: 'app-show-book',
  templateUrl: './show-book.page.html',
  styleUrls: ['./show-book.page.scss'],
})
export class ShowBookPage implements OnInit {
  private _book: Book;
  private _editMode: boolean = false;
  private _defaultCover = '../../../assets/default_cover.png';
  private _friend: Friend;

  constructor(private booksService: BooksService,
              private friendsService: FriendsService,
              private route: ActivatedRoute,
              private modalCtrl: ModalController) {
  }


  get defaultCover(): string {
    return this._defaultCover;
  }

  get book(): Book {
    return this._book;
  }

  get editMode(): boolean {
    return this._editMode;
  }


  get friend(): Friend {
    return this._friend;
  }

  ngOnInit() {
    this.retrieveBook();
  }

  retrieveBook() {
    this.route.data.subscribe(
      data => {
        this._book = data['book'];
        if (this.book.friendId != null && this.book.friendId != 0) {
          this.retrieveFriend(this.book.friendId);
        }
      },
      error => {
        console.log(error);
      }
    );
  }

  retrieveFriend(friendId: number) {
    this.friendsService.getFriendById(friendId).subscribe(
      data => {
        console.log(data);
        this._friend = data;
      },
      error => {
        console.log(error);
      }
    );
  }

  toggleEditMode() {
    this._editMode = !this._editMode;
    console.log(this.editMode);
  }

  saveChanges() {
  }

  deleteBook() {
    this.modalCtrl
      .create({
        component: DeleteBookModalComponent,
        componentProps: {step1: true},
        cssClass: 'delete-book-modal'
      })
      .then(modal => {
        modal.present();
        return modal.onDidDismiss();
      })
      .then(modal => {
        if (modal.role == 'delete') {
          console.log('delete');
          this.booksService.deleteBook(this.book.id).subscribe(
            data => {
              this.presentSuccessModal();
            },
            error => {
              console.log(error);
            }
          );
        }
      })
  }

  private presentSuccessModal() {
    this.modalCtrl
      .create({
        component: DeleteBookModalComponent,
        componentProps: {step1: false},
        cssClass: 'delete-book-modal'
      })
      .then(modal => {
        modal.present();
      })
  }
}
