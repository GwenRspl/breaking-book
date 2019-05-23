import {Component, OnInit} from '@angular/core';
import {Book} from '../book.model';
import {BooksService} from '../services/books.service';
import {ActivatedRoute} from '@angular/router';
import {Friend} from '../../friends/friend.model';
import {FriendsService} from '../../friends/services/friends.service';
import {ModalController} from '@ionic/angular';
import {DeleteBookModalComponent} from './delete-book-modal/delete-book-modal.component';
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
  private _editMode: boolean = false;
  private _defaultCover = '../../../assets/default_cover.png';
  private _friend: Friend;
  private _submitted = false;

  constructor(private booksService: BooksService,
              private friendsService: FriendsService,
              private route: ActivatedRoute,
              private modalCtrl: ModalController,
              private formBuilder: FormBuilder,
              private tokenStorageService: TokenStorageService) {
  }


  get defaultCover(): string {
    return this._defaultCover;
  }


  get submitted(): boolean {
    return this._submitted;
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
        console.log(this.book);
        this.initEditForm();
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
        //this.toggleEditMode();
        this._book = newBook;
      },
      error => {
        console.log(error);
      }
    );
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
