import { AfterViewInit, Component, Input, ViewChild,  } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { CommonModule } from '@angular/common';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';

// Define an interface for your movie data
export interface Movie {
  title: string;
  year: number;
  genres: string[];
}

@Component({
  selector: 'app-movie-table',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatPaginatorModule, MatSortModule],
  templateUrl: './movie-table.component.html',
  styleUrls: ['./movie-table.component.scss']
})

export class MovieTableComponent implements AfterViewInit {
  @Input() movies: Movie[] = [];

  displayedColumns: string[] = ['title', 'year', 'genres']; 
  
  dataSource = new MatTableDataSource<Movie>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.data = this.movies;
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
  ngOnChanges() {
  this.dataSource.data = this.movies;
}
}