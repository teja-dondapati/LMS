// login.component.ts
import { Component } from '@angular/core';
import { LoginService } from './login.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { NotificationService } from '../notification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [FormsModule, HttpClientModule],
  providers: [HttpClientModule, NotificationService]
})

export class LoginComponent {
  username: string = ''; 
  password: string = '';
  isFileUploadPage: boolean = false;
  constructor(private loginService: LoginService, private router: Router, private notificationService: NotificationService) {}

  login(username: string, password: string): void {
    this.loginService.login(this.username, this.password).subscribe({
      next: (response) => {
        // Handle successful login here (e.g., show a pop-up)
        console.log('Login response:', response);
        this.notificationService.showNotification('Login successful');
        // alert('Login successful');
        this.isFileUploadPage = true;
        this.router.navigate(['/file-upload']);
        // Redirect to the file-upload page using Angular Router
        // You need to inject the Angular Router service and navigate to the desired route
      },
      error: (error) => {
        // Handle login error (e.g., show an error message)
        console.error('Login failed', error);
      },
    });
  }
}
