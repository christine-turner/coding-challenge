import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { CommonModule } from '@angular/common';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { AuthService } from './auth.service';

export interface Movie {
  title: string;
  year: number;
  genres: string[];
}

@Component({
  selector: 'app-movie-table',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatPaginatorModule, MatSortModule],
  template: `
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
        <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
      </table>

      <mat-paginator [pageSizeOptions]="[5, 10, 25]" showFirstLastButtons></mat-paginator>
    </div>
  `,
  styles: [`
    .movie-table-container {
      margin: 2rem;
      overflow: auto;
    }
    table {
      width: 100%;
    }
  `]
})
export class MovieTableComponent implements AfterViewInit {
  displayedColumns: string[] = ['title', 'year', 'genres'];
  dataSource = new MatTableDataSource<Movie>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private auth: AuthService) {}

  ngOnInit() {
    if (this.auth.isLoggedIn()) {
      this.auth.getMovies().subscribe({
        next: movies => {
          this.dataSource.data = movies;
        },
        error: err => console.error('Failed to load movies', err)
      });
    } else {
      console.warn('User not logged in, skipping movie load.');
    }
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
}
