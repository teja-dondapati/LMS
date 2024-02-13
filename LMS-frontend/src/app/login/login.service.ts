// login.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { UserEntity } from './user-entity.model';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private apiUrl = 'http://localhost:9090/api/auth';
  private loggedInUser: any; // You can create a User class for better typing

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    console.log(username)
    console.log(password)
    const body = { username, password };
    return this.http.post(`${this.apiUrl}/login`, body).pipe(
      tap((response: any) => {
        // Store the logged-in user information
        this.loggedInUser = response.user as UserEntity; // Assuming the response has a 'user' field
      })
    );
  }

  getLoggedInUser(): any {

    return this.loggedInUser;
  }
}
