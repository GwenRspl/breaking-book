import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Book} from '../book.model';
import {TokenStorageService} from '../../authentication/services/token-storage.service';
import {BooksService} from '../services/books.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-new-book',
  templateUrl: './new-book.page.html',
  styleUrls: ['./new-book.page.scss'],
})
export class NewBookPage implements OnInit {
  bookForm: FormGroup;
  submitted: boolean = false;
  book: Book;

  constructor(private formBuilder: FormBuilder,
              private tokenStorageService: TokenStorageService,
              private booksService: BooksService,
              private router: Router) {
  }

  get f() {
    return this.bookForm.controls;
  }

  ngOnInit() {
    this.initForm();
  }

  ionViewWillEnter() {
    this.submitted = false;
    this.initForm();
  }

  initForm() {
    if (this.booksService.isBookReadyToPopulate()) {
      this.book = this.booksService.getSelectedBook();
      this.bookForm = this.formBuilder.group({
        title: [this.book.title, [Validators.required, Validators.minLength(3)]],
        authors: [this.book.authors.toString(), [Validators.required, Validators.minLength(3)]],
        isbn: [this.book.isbn, [Validators.minLength(10), Validators.maxLength(13)]],
        image: [this.book.image],
        language: [this.book.language, [Validators.required]],
        publisher: [this.book.publisher],
        datePublished: [this.book.datePublished.toISOString()],
        pages: [this.book.pages],
        synopsis: [this.book.synopsis],
        owned: ['', [Validators.required]],
        status: ['', [Validators.required]],
        rating: [''],
        comment: [''],
      });
    } else {
      this.bookForm = this.formBuilder.group({
        title: ['', [Validators.required, Validators.minLength(3)]],
        authors: ['', [Validators.required, Validators.minLength(3)]],
        isbn: ['', [Validators.minLength(10), Validators.maxLength(13)]],
        image: [''],
        language: ['', [Validators.required]],
        publisher: [''],
        datePublished: [''],
        pages: [''],
        synopsis: [''],
        owned: ['', [Validators.required]],
        status: ['', [Validators.required]],
        rating: [''],
        comment: [''],
      });
    }
  }

  addBookToLibrary() {
    this.submitted = true;
    if (this.bookForm.invalid) {
      console.log('invalid form');
      return;
    }
    const newBook: Book = new Book(null,
      this.bookForm.value.title,
      this.bookForm.value.authors.split(','),
      this.bookForm.value.isbn,
      this.bookForm.value.image,
      this.bookForm.value.language,
      this.bookForm.value.publisher,
      this.bookForm.value.datePublished,
      this.bookForm.value.pages,
      this.bookForm.value.synopsis,
      this.bookForm.value.owned === 'true',
      this.bookForm.value.status,
      +this.bookForm.value.rating,
      this.bookForm.value.comment,
      null,
      +this.tokenStorageService.getUserId()
    );
    this.booksService.saveBook(newBook).subscribe(
      data => {
        if (this.book != null) {
          this.booksService.setIsBookReadyToPopulate(false);
        }
        this.router.navigate((['/', 'library', 'show', data]));
      },
      error => {
        console.log(error);
      }
    );

  }
}
