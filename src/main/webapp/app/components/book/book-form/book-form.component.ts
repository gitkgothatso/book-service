import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Book } from '../../../services/Book';
import { BookService } from '../../../services/book.service';

@Component({
  selector: 'jhi-book-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatCardModule],
  templateUrl: './book-form.component.html',
  styleUrls: ['./book-form.component.css'],
})
export class BookFormComponent implements OnInit {
  form!: FormGroup;
  editing = false;
  id?: number;

  constructor(
    private fb: FormBuilder,
    private bookService: BookService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.form = this.fb.group({
      title: ['', Validators.required],
      author: ['', Validators.required],
      publishedDate: ['', Validators.required],
      isbn: ['', [Validators.required, Validators.pattern(/\d{10}|\d{13}/)]],
    });

    if (this.id) {
      this.editing = true;
      this.bookService.get(this.id).subscribe(book => this.form.patchValue(book));
      this.form.get('isbn')?.disable(); // 👈 disable ISBN field
    }
  }

  submit(): void {
    let book: Book = this.form.getRawValue(); // 👈 includes disabled fields
    const action = this.editing ? this.bookService.update(this.id!, book) : this.bookService.create(book);

    action.subscribe(() => this.router.navigate(['/books']));
  }
}
