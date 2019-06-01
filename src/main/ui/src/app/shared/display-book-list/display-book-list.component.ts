import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Router} from '@angular/router';
import {Collection} from '../../collections/collection.model';
import {Book} from '../../library/book.model';
import {Wishlist} from '../../wishlists/wishlist.model';
import {CollectionsService} from '../../collections/services/collections.service';
import {ChooseBookModalComponent} from '../modals/choose-book-modal/choose-book-modal.component';
import {ModalController} from '@ionic/angular';


@Component({
  selector: 'app-display-book-list',
  templateUrl: './display-book-list.component.html',
  styleUrls: ['./display-book-list.component.scss'],
})
export class DisplayBookListComponent implements OnInit {
  @Output() refreshParent = new EventEmitter();
  @Input() books: Book[];
  @Input() collection: Collection;
  @Input() wishlist: Wishlist;
  @Input() itemMode: string;
  userInput: string = '';
  private _defaultCover = '../../../assets/default_cover.png';
  private _item: any;
  private _finishedLoading: boolean = false;


  constructor(private router: Router,
              private collectionsService: CollectionsService,
              private modalCtrl: ModalController) {
  }


  get finishedLoading(): boolean {
    return this._finishedLoading;
  }

  get defaultCover(): string {
    return this._defaultCover;
  }

  get item(): any {
    return this._item;
  }

  ngOnInit() {
    if (this.itemMode == 'collection') {
      this._item = this.collection;
    } else {
      this._item = this.wishlist;
    }
    this._finishedLoading = true;
  }


  goToBookDetails(bookId: number) {
    this.router.navigate(['library', 'show', bookId])
  }

  deleteBookFromItem(bookId: number) {
    if (this.itemMode == 'collection') {
      this.collectionsService.deleteBookFromCollection(this.collection.id, bookId).subscribe(
        () => this.refreshParent.next(),
        error => console.log(error)
      );

    } else {

    }
  }

  openChooseBookModal() {
    if (this.itemMode == 'collection') {
      this.modalCtrl
        .create({
          component: ChooseBookModalComponent,
          componentProps: {modalMode: 'collection', collection: this.collection}
        })
        .then(modal => {
            modal.present();
            return modal.onDidDismiss();
          }
        )
        .then(modal => {
          if (modal.role == 'book') {
            this.addBookToCollection(modal.data, this.collection.id);
          }
        })

    } else {

    }
  }

  addBookToCollection(bookId: number, collectionId: number) {
    this.collectionsService.addBookToCollection(collectionId, bookId).subscribe(
      () => this.refreshParent.next(),
      error => console.log(error)
    )
  }

  deleteItem(itemId: number) {
    if (this.itemMode == 'collection') {
      this.collectionsService.deleteCollection(itemId).subscribe(
        () => this.refreshParent.next(),
        error => console.log(error)
      );

    } else {

    }
  }

  renameItem(itemId: number) {
    if (this.itemMode == 'collection') {

    } else {

    }
  }
}
