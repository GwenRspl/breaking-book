import { Component, OnInit } from '@angular/core';
import {Book} from './book.model';
import {Friend} from './friend.model';
import {User} from './user.model';

@Component({
  selector: 'app-library',
  templateUrl: './library.page.html',
  styleUrls: ['./library.page.scss'],
})
export class LibraryPage implements OnInit {
  private _genreList: string[] =[];
  private _books: Book[] = [];
  private _currentlyReading: Book[] = [];

  constructor() { }

  ngOnInit() {
    this.retrieveGenreList();
    this.retrieveBooks();
    this.retrieveCurrentlyReading()
  }

  retrieveGenreList(){
    this._genreList = ['Fantasy', 'SF', 'Fiction', 'Non-fiction', 'Short stories'];
  }

  retrieveBooks(){
    this._books = [
      new Book(1, 'Royal Assassin', ['Hobb'], '', 'https://www.babelio.com/couv/CVT_LAssassin-royal-Tome-1--Lapprenti-assassin_4514.jpeg', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
      new Book(1, 'Aladdin', ['Disney'], '', 'https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
      new Book(1, 'The lord of the rings', ['Bidule'], '', 'https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
      new Book(1, 'Bilbo', ['retret'], '', 'https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
      new Book(1, 'Babar', ['retert'], '', 'https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
      new Book(1, 'Addams', ['ertret'], '', 'https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User())
    ];
  }

  retrieveCurrentlyReading(){
    this._currentlyReading = [
      new Book(1, 'Royal Assassin', ['Hobb'], '', 'https://www.babelio.com/couv/CVT_LAssassin-royal-Tome-1--Lapprenti-assassin_4514.jpeg', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
      new Book(1, 'Aladdin', ['Disney'], '', 'https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User())
    ];
  }


  get genreList(): string[] {
    return this._genreList;
  }

  get books(): Book[] {
    return this._books;
  }

  get currentlyReading(): Book[] {
    return this._currentlyReading;
  }
}
