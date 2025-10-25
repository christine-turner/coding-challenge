import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableDataSource } from '@angular/material/table';

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
    MatFormFieldModule,
    MatInputModule,
  ],
  template: `
    <div>
      <mat-form-field appearance="outline">
        <mat-label>Filter</mat-label>
        <input matInput (keyup)="applyFilter($event)" placeholder="Filter movies" />
      </mat-form-field>

      <table mat-table [dataSource]="dataSource" matSort class="mat-elevation-z8">
        
        <!-- Title Column -->
        <ng-container matColumnDef="title">
          <th mat-header-cell *matHeaderCellDef mat-sort-header> Title </th>
          <td mat-cell *matCellDef="let movie"> {{ movie.title }} </td>
        </ng-container>

        <!-- Year Column -->
        <ng-container matColumnDef="year">
          <th mat-header-cell *matHeaderCellDef mat-sort-header> Year </th>
          <td mat-cell *matCellDef="let movie"> {{ movie.year }} </td>
        </ng-container>

        <!-- Genres Column -->
        <ng-container matColumnDef="genres">
          <th mat-header-cell *matHeaderCellDef> Genres </th>
          <td mat-cell *matCellDef="let movie"> {{ movie.genres.join(', ') }} </td>
        </ng-container>

        <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
        <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
      </table>

      <mat-paginator
        [pageSizeOptions]="[5, 10, 25]"
        showFirstLastButtons>
      </mat-paginator>
    </div>
  `,
})
export class MovieTableComponent implements OnInit {
  @Input() movies: Movie[] = [];

  displayedColumns: string[] = ['title', 'year', 'genres'];
  dataSource = new MatTableDataSource<Movie>([]);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngOnInit() {
    this.dataSource.data = this.movies;
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.dataSource.filter = filterValue;
  }
}