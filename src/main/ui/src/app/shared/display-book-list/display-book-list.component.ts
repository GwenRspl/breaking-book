import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Router} from '@angular/router';
import {Collection} from '../../collections/collection.model';
import {Book} from '../../library/book.model';
import {Wishlist} from '../../wishlists/wishlist.model';
import {CollectionsService} from '../../collections/services/collections.service';
import {ChooseBookModalComponent} from '../modals/choose-book-modal/choose-book-modal.component';
import {AlertController, ModalController} from '@ionic/angular';


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
  private _defaultCover = '../../../assets/default_cover.png';
  private _item: any;
  private _finishedLoading: boolean = false;


  constructor(private router: Router,
              private collectionsService: CollectionsService,
              private modalCtrl: ModalController,
              private alertCtrl: AlertController) {
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
    console.log('init');
    if (this.itemMode == 'collection') {
      this._item = this.collection;
    } else {
      console.log('wish');
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

  renameItemModal() {
    console.log('yo');
    if (this.itemMode == 'collection') {
      this.alertCtrl.create({
        message: 'RENAME',
        inputs: [
          {
            name: 'name',
            type: 'text',
            value: this.collection.name
          }
        ],
        buttons: [
          {
            text: 'Cancel',
            role: 'cancel',
            handler: () => {
              this.alertCtrl.dismiss();
            }
          }, {
            text: 'Rename',
            handler: (data) => {
              if (data.name == '') {
                this.renameItemModal();
              } else {
                this.collection.name = data.name;
                this.renameCollection();
              }
            }
          }
        ]
      }).then(alert => alert.present())

    } else {

    }
  }

  renameCollection() {
    this.collectionsService.renameCollection(this.collection).subscribe(
      () => console.log(),
      error => console.log(error)
    );
  }
}
