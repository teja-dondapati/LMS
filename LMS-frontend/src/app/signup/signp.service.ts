// login.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SignupService {
  private apiUrl = 'http://localhost:9090/api/auth';

  constructor(private http: HttpClient) {}

  signup(username: string, password: string, firstName: string, lastName: string, email: string): Observable<any> {
    const body = { username, firstName, lastName, email, password };
    return this.http.post(`${this.apiUrl}/signup`, body);
  }
}
