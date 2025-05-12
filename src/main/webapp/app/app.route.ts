import { Routes } from '@angular/router';
import { BookListComponent } from './components/book/book-list/book-list.component';
import { BookFormComponent } from './components/book/book-form/book-form.component';


export const routes: Routes = [
{ path: 'books', component: BookListComponent },
{ path: 'books/new', component: BookFormComponent },
{ path: 'books/edit/:id', component: BookFormComponent },
{ path: '', redirectTo: '/books', pathMatch: 'full' },
];
