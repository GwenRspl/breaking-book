import {Component, OnInit} from '@angular/core';
import {Book} from '../book.model';
import {BooksService} from '../services/books.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Friend} from '../../friends/friend.model';
import {FriendsService} from '../../friends/services/friends.service';
import {AlertController, ToastController} from '@ionic/angular';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TokenStorageService} from '../../authentication/services/token-storage.service';


@Component({
  selector: 'app-show-book',
  templateUrl: './show-book.page.html',
  styleUrls: ['./show-book.page.scss'],
})
export class ShowBookPage implements OnInit {
  editForm: FormGroup;
  private _book: Book;
  private _friend: Friend;
  private _defaultCover = '../../../assets/default_cover.png';
  private _editMode: boolean = false;
  private _submitted = false;
  private _finishedLoading: boolean = false;

  constructor(private booksService: BooksService,
              private friendsService: FriendsService,
              private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private tokenStorageService: TokenStorageService,
              private alertCtrl: AlertController,
              private toastCtrl: ToastController,
              private router: Router) {
  }


  get finishedLoading(): boolean {
    return this._finishedLoading;
  }

  get book(): Book {
    return this._book;
  }

  get friend(): Friend {
    return this._friend;
  }

  get defaultCover(): string {
    return this._defaultCover;
  }

  get editMode(): boolean {
    return this._editMode;
  }

  get submitted(): boolean {
    return this._submitted;
  }

  get f() {
    return this.editForm.controls;
  }

  ngOnInit() {
    this.retrieveBook();
  }

  retrieveBook() {
    this.route.data.subscribe(
      data => {
        this._book = data['book'];
        this.initEditForm();
        if (this.book.friendId != null && this.book.friendId != 0) {
          this.retrieveFriend(this.book.friendId);
        }
        this._finishedLoading = true;
      },
      error => {
        console.log(error);
      }
    );
  }

  retrieveFriend(friendId: number) {
    this.friendsService.getFriendById(friendId).subscribe(
      data => {
        this._friend = data;
      },
      error => {
        console.log(error);
      }
    );
  }

  /**
   * Edit book methods
   */

  toggleEditMode() {
    this._editMode = !this._editMode;
  }

  initEditForm() {
    this.editForm = this.formBuilder.group({
      title: [this.book.title, [Validators.required, Validators.minLength(3)]],
      authors: [this.book.authors.toString(), [Validators.required, Validators.minLength(3)]],
      isbn: [this.book.isbn, [Validators.minLength(10), Validators.maxLength(13)]],
      image: [this.book.image],
      language: [this.book.language, [Validators.required]],
      publisher: [this.book.publisher],
      datePublished: [this.book.datePublished],
      pages: [this.book.pages],
      synopsis: [this.book.synopsis],
      owned: [this.book.owned.toString(), [Validators.required]],
      status: [this.book.status, [Validators.required]],
      rating: [this.book.rating.toString()],
      comment: [this.book.comment],
    });
  }

  saveChanges() {
    this._submitted = true;
    if (this.editForm.invalid) {
      console.log('invalid form');
      return;
    }
    let friendId = null;
    if (this.book.friendId != null && this.book.friendId != 0) {
      friendId = this.book.friendId;
    }
    const newBook: Book = new Book(
      this.book.id,
      this.editForm.value.title,
      this.editForm.value.authors.split(','),
      this.editForm.value.isbn,
      this.editForm.value.image,
      this.editForm.value.language,
      this.editForm.value.publisher,
      this.editForm.value.datePublished,
      this.editForm.value.pages,
      this.editForm.value.synopsis,
      this.editForm.value.owned === 'true',
      this.editForm.value.status,
      +this.editForm.value.rating,
      this.editForm.value.comment,
      friendId,
      +this.tokenStorageService.getUserId()
    );
    this.booksService.updateBook(newBook).subscribe(
      data => {
        this.presentSuccessToast();
        this._book = newBook;
      },
      error => {
        console.log(error);
        this.presentErrorToast();
      }
    );
  }

  /**
   * Delete book methods
   */

  presentDeleteBookAlert() {
    this.alertCtrl.create({
      message: 'Are you sure you want to delete this book ?',
      buttons: [
        {
          text: 'Yes',
          handler: () => {
            this.deleteBook();
          }
        },
        {
          text: 'No',
          handler: () => {
            this.alertCtrl.dismiss()
          }
        }
      ]
    }).then(alert => {
      alert.present();
    });

  }

  deleteBook() {
    this.booksService.deleteBook(this.book.id).subscribe(
      data => {
        this.presentSuccessAlert();
      },
      error => {
        console.log(error);
      }
    );
  }

  /**
   * Alerts & toasts
   */

  presentSuccessAlert() {
    this.alertCtrl.create({
      message: 'Book successfully deleted!',
      backdropDismiss: false,
      buttons: [
        {
          text: 'Go back to library',
          handler: () => {
            this.router.navigateByUrl('/library');
          }
        }
      ]
    }).then(alert => {
      alert.present();
    });
  }

  presentSuccessToast() {
    this.toastCtrl.create({
      message: 'Book successfully updated',
      color: 'success',
      duration: 2000
    }).then(toast => toast.present());
  }

  presentErrorToast() {
    this.toastCtrl.create({
      message: 'Book not updated, please try again later',
      color: 'danger',
      duration: 2000
    }).then(toast => toast.present());
  }

}
