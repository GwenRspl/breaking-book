import {Component, OnInit} from '@angular/core';
import {Collection} from './collection.model';
import {Book} from '../library/book.model';
import {CollectionsService} from './services/collections.service';
import {BooksService} from '../library/services/books.service';

@Component({
  selector: 'app-collections',
  templateUrl: './collections.page.html',
  styleUrls: ['./collections.page.scss'],
})
export class CollectionsPage implements OnInit {
  private _collections: Collection[] = [];
  private _booksCollectionsMap: Map<number, Book[]> = new Map<number, Book[]>();
  private _defaultCover = '../../../assets/default_cover.png';

  constructor(private collectionsService: CollectionsService,
              private booksService: BooksService) {
  }


  get collections(): Collection[] {
    return this._collections;
  }

  get booksCollectionsMap(): Map<number, Book[]> {
    return this._booksCollectionsMap;
  }

  get defaultCover(): string {
    return this._defaultCover;
  }

  ngOnInit() {
    this.retrieveCollections();
  }

  retrieveCollections() {
    this.collectionsService.getCollections().subscribe(
      data => {
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
    this.collections.forEach(collection => {
      let bookList: Book[] = [];
      books.forEach(book => {
        if (collection.booksIds.includes(book.id)) {
          bookList.push(book);
        }
      });
      this._booksCollectionsMap.set(collection.id, bookList);
    });
  }

}
