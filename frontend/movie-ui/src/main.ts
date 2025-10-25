import { bootstrapApplication } from '@angular/platform-browser';
import { App } from './app/app';
import { provideRouter } from '@angular/router';
import { importProvidersFrom } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { MovieOuterComponent } from './app/components/movie-outer.component';

bootstrapApplication(App, {
  providers: [
    importProvidersFrom(HttpClientModule), // provide HttpClient
    provideRouter([
      { path: '', redirectTo: 'movies', pathMatch: 'full' },
      { path: 'movies', component: MovieOuterComponent },
    ]),
  ],
}).catch((err) => console.error(err));
