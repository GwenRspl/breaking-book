import {Component, OnInit, ViewChild} from '@angular/core';
import {Book} from './book.model';
import {Friend} from './friend.model';
import {User} from './user.model';
import {IonSlides} from '@ionic/angular';
import {BooksService} from './services/books.service';

@Component({
  selector: 'app-library',
  templateUrl: './library.page.html',
  styleUrls: ['./library.page.scss'],
})
export class LibraryPage implements OnInit {
  @ViewChild('slideWithButtons') slideWithNav: IonSlides;

  private _userId: number;
  private _books: Book[] = [];
  private _currentlyReading: Book[] = [];
  private _genreList: string[] =[];
  private _currentSelection: Book[] = [];
  private _sortOptions: string[] = ['Author', 'Title', 'Rating'];

  slideOpts = {
    initialSlide: 1,
    slidesPerView: 4,
    speed: 400
  };

  constructor(private booksService: BooksService) { }

  ngOnInit() {
    this.retrieveUserId()
    this.retrieveGenreList();
    this.retrieveBooks();
    this.retrieveCurrentlyReading()
  }

  retrieveUserId(){
    this._userId = 1;
  }

  retrieveGenreList(){
    this._genreList = ['Fantasy', 'SF', 'Fiction', 'Non-fiction', 'Short stories'];
  }

  retrieveBooks(){
    this.booksService.getBooks(this.userId).subscribe(
      data => {
        this._books = data;
        console.log(data);

      },
      error => {
        console.log(error);
      }
    );
    // this._books = [
    //   new Book(1, 'Royal Assassin', ['Hobb'], '', 'https://www.babelio.com/couv/CVT_LAssassin-royal-Tome-1--Lapprenti-assassin_4514.jpeg', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
    //   new Book(1, 'Aladdin', ['Disney'], '', 'https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
    //   new Book(1, 'The lord of the rings', ['Bidule'], '', 'https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
    //   new Book(1, 'Bilbo', ['retret'], '', 'https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
    //   new Book(1, 'Babar', ['retert'], '', 'https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
    //   new Book(1, 'Addams', ['ertret'], '', 'https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User())
    // ];
  }

  retrieveCurrentlyReading(){
    this._currentlyReading = [
      new Book(1, 'Royal Assassin', ['Hobb'], '', 'https://www.babelio.com/couv/CVT_LAssassin-royal-Tome-1--Lapprenti-assassin_4514.jpeg', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
      new Book(1, 'Royal Assassin', ['Hobb'], '', 'https://www.babelio.com/couv/CVT_LAssassin-royal-Tome-1--Lapprenti-assassin_4514.jpeg', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
      new Book(1, 'Royal Assassin', ['Hobb'], '', 'https://www.babelio.com/couv/CVT_LAssassin-royal-Tome-1--Lapprenti-assassin_4514.jpeg', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
      new Book(1, 'Royal Assassin', ['Hobb'], '', 'https://www.babelio.com/couv/CVT_LAssassin-royal-Tome-1--Lapprenti-assassin_4514.jpeg', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
      new Book(1, 'Royal Assassin', ['Hobb'], '', 'https://www.babelio.com/couv/CVT_LAssassin-royal-Tome-1--Lapprenti-assassin_4514.jpeg', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
      new Book(1, 'Aladdin', ['Disney'], '', 'https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
      new Book(1, 'Aladdin', ['Disney'], '', 'https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
      new Book(1, 'Aladdin', ['Disney'], '', 'https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User()),
      new Book(1, 'Aladdin', ['Disney'], '', 'https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png', '', '', new Date("February 4, 2016 10:13:00"), 123, '', true, true, 5, '', new Friend(), new User())
    ];
  }


  get userId(): number {
    return this._userId;
  }

  get currentSelection(): Book[] {
    return this._currentSelection;
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

  get sortOptions(): string[] {
    return this._sortOptions;
  }

  nextSlide(slideView) {
    slideView.slideNext(500);
  }

  previousSlide(slideView) {
    slideView.slidePrev(500);
  }


}
