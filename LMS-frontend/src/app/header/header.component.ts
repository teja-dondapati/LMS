import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
  standalone: true,
  imports: [CommonModule]
})
export class HeaderComponent {
  @Input() isFileUploadPage: boolean = false;

  constructor(private router: Router) {}

  // Function to handle logout
  logout(): void {
    alert('Logged Out');
    // Redirect to the login page
    this.router.navigate(['/login']);
  }

}
