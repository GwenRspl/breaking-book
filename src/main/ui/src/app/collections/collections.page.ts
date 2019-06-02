import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Collection} from './collection.model';
import {Book} from '../library/book.model';
import {CollectionsService} from './services/collections.service';
import {BooksService} from '../library/services/books.service';
import {TokenStorageService} from '../authentication/services/token-storage.service';

@Component({
  selector: 'app-collections',
  templateUrl: './collections.page.html',
  styleUrls: ['./collections.page.scss'],
})
export class CollectionsPage implements OnInit {
  userInput: string = '';
  private _collections: Collection[] = [];
  private _booksCollectionsMap: Map<number, Book[]> = new Map<number, Book[]>();

  constructor(private collectionsService: CollectionsService,
              private booksService: BooksService,
              private router: Router,
              private tokenStorage: TokenStorageService) {
  }


  get collections(): Collection[] {
    return this._collections;
  }

  get booksCollectionsMap(): Map<number, Book[]> {
    return this._booksCollectionsMap;
  }

  ngOnInit() {
    this.retrieveCollections();
  }

  retrieveCollections() {
    this.collectionsService.getCollections().subscribe(
      data => {
        console.log(data);
        this._collections = data;
        this.booksService.getBooks().subscribe(
          data => {
            this.initMap(data);

          }
        )
      },
      error => console.log(error)
    );
  }

  initMap(books: Book[]) {
    this._booksCollectionsMap = new Map<number, Book[]>();
    this.collections.forEach(collection => {
      if (collection.booksIds != null) {
        let bookList: Book[] = [];
        books.forEach(book => {
          if (collection.booksIds.includes(book.id)) {
            bookList.push(book);
          }
        });
        this._booksCollectionsMap.set(collection.id, bookList);
      }
    });
  }

  createNewCollection() {
    if (this.userInput == '') {
      return;
    }
    let collection = new Collection(this.userInput, +this.tokenStorage.getUserId());
    this.collectionsService.saveCollection(collection).subscribe(
      () => this.retrieveCollections(),
      error => console.log(error)
    );
  }

}
