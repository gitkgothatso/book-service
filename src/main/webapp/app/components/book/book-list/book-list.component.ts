import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { Router, RouterModule } from '@angular/router';
import { Book } from '../../../services/Book';
import { BookService } from '../../../services/book.service';

@Component({
  selector: 'jhi-book-list',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, MatIconModule, RouterModule],
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css'],
})
export class BookListComponent implements OnInit {
  books: Book[] = [];
  displayedColumns: string[] = ['title', 'author', 'actions'];

  constructor(
    private bookService: BookService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.loadBooks();
  }

  loadBooks(): void {
    this.bookService.getAll().subscribe(data => (this.books = data));
  }

  edit(book: Book): void {
    this.router.navigate(['/books/edit', book.id]);
  }

  delete(book: Book): void {
    if (confirm(`Delete "${book.title}"?`)) {
      this.bookService.delete(book.id!).subscribe(() => this.loadBooks());
    }
  }
}
