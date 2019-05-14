import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-new-book',
  templateUrl: './new-book.page.html',
  styleUrls: ['./new-book.page.scss'],
})
export class NewBookPage implements OnInit {
  bookForm: FormGroup;
  submitted: boolean = false;

  constructor(private formBuilder: FormBuilder) {
  }

  get f() {
    return this.bookForm.controls;
  }

  ngOnInit() {
    this.initForm();
  }

  initForm() {
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

  addBookToLibrary() {
    this.submitted = true;
    if (this.bookForm.invalid) {
      console.log('invalid form');
      return;
    }

  }
}
