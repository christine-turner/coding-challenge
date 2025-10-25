import { AfterViewInit, Component, Input, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { CommonModule } from '@angular/common';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { DataService } from '../service/data.service';
import { Subscription } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AuthService } from '../service/auth.service';

export interface Movie {
  title: string;
  year: number;
  genres: string[];
}

@Component({
  selector: 'app-movie-table',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  template: `
    <div class="filters">
      <mat-form-field appearance="outline">
        <mat-label>Title</mat-label>
        <input matInput (keyup)="applyFilter()" [(ngModel)]="filterValues.title" />
      </mat-form-field>

      <mat-form-field appearance="outline">
        <mat-label>Year</mat-label>
        <input matInput (keyup)="applyFilter()" [(ngModel)]="filterValues.year" />
      </mat-form-field>

      <mat-form-field appearance="outline">
        <mat-label>Genres</mat-label>
        <input matInput (keyup)="applyFilter()" [(ngModel)]="filterValues.genre" />
      </mat-form-field>
    </div>
    <div class="movie-table-container">
      <table mat-table [dataSource]="dataSource" matSort class="mat-elevation-z8">
        <ng-container matColumnDef="title">
          <th mat-header-cell *matHeaderCellDef mat-sort-header>Title</th>
          <td mat-cell *matCellDef="let movie">{{ movie.title }}</td>
        </ng-container>

        <ng-container matColumnDef="year">
          <th mat-header-cell *matHeaderCellDef mat-sort-header>Year</th>
          <td mat-cell *matCellDef="let movie">{{ movie.year }}</td>
        </ng-container>

        <ng-container matColumnDef="genres">
          <th mat-header-cell *matHeaderCellDef>Genres</th>
          <td mat-cell *matCellDef="let movie">{{ movie.genres.join(', ') }}</td>
        </ng-container>

        <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
        <tr mat-row *matRowDef="let row; columns: displayedColumns"></tr>
      </table>

      <mat-paginator [pageSizeOptions]="[5, 10, 25]" showFirstLastButtons></mat-paginator>
    </div>
  `,
  styles: [
    `
      .movie-table-container {
        margin: 2rem;
        overflow: auto;
      }
      table {
        width: 100%;
      }
    `,
  ],
})
export class MovieTableComponent implements AfterViewInit {
  displayedColumns: string[] = ['title', 'year', 'genres'];
  filterValues = {
    title: '',
    year: '',
    genre: '',
  };

  dataSource = new MatTableDataSource<Movie>();
  private dataSourceSubscription!: Subscription;

  credentials?: { username: string; password: string };
  private credentialsSubscription!: Subscription;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private dataService: DataService, private authService: AuthService) {}
  ngOnInit() {
    this.loadMovies();
    this.dataSourceSubscription = this.dataService.movies$.subscribe((movies) => {
      this.dataSource.data = movies;
    });

    this.credentialsSubscription = this.authService.credentials$.subscribe(
      (creds) => (this.credentials = creds || undefined)
    );
  }

  applyFilter() {
    this.dataService.setFilters(this.filterValues);
  }

  loadMovies() {
    // avoid calling API without credentials
    if (!this.credentials?.username || !this.credentials?.password) return;
    this.dataService.loadMovies(
      this.credentials.username,
      this.credentials.password,
      this.filterValues
    );
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  ngOnDestroy() {
    this.dataSourceSubscription.unsubscribe(); // prevents memory leak
    this.credentialsSubscription.unsubscribe(); 
  }
}
