// signup.component.ts
import { Component } from '@angular/core';
import { SignupService } from './signp.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
  standalone: true,
  imports: [FormsModule, HttpClientModule],
  providers: [HttpClientModule]
})
export class SignupComponent {
  username: string = ''; 
  password: string = '';
  firstName: string = '';
  lastName: string = '';
  email: string = '';
  constructor(private signupService: SignupService, private router: Router) {}

  signup(username: string, password: string, firstName: string, lastName: string, email: string): void {
    this.signupService.signup(this.username, this.password, this.firstName, this.lastName, this.email).subscribe({
      next: (response) => {
        // Handle successful login here (e.g., show a pop-up)
        console.log('Login response:', response);
        alert('Signup successful');
        this.router.navigate(['/login']);
        // Redirect to the file-upload page using Angular Router
        // You need to inject the Angular Router service and navigate to the desired route
      },
      error: (error) => {
        // Handle login error (e.g., show an error message)
        console.error('Signup failed', error);
      },
    });
  }
}
