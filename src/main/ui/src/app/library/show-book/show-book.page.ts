import {Component, OnInit} from '@angular/core';
import {Book} from '../book.model';
import {BooksService} from '../services/books.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-show-book',
  templateUrl: './show-book.page.html',
  styleUrls: ['./show-book.page.scss'],
})
export class ShowBookPage implements OnInit {
  private _book: Book;
  private _editMode: boolean = false;
  private _defaultCover = '../../../assets/default_cover.png';

  constructor(private booksService: BooksService,
              private route: ActivatedRoute) {
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

  ngOnInit() {
    this.retrieveBook();
  }

  retrieveBook() {
    this.route.data.subscribe(
      data => {
        this._book = data['book'];
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
}
