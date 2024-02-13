import { Component } from '@angular/core';
import { FileUploadService } from './file-upload.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { LoginService } from '../login/login.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css'],
  standalone: true,
  imports: [FormsModule, HttpClientModule, CommonModule],
  providers: [HttpClientModule]
})
export class FileUploadComponent {
  private selectedFiles: { [key: string]: File } = {};
  private loggedInUser: any;
  public uploadProgress: number = 0;
  public isUploading: boolean = false;
  public showProgressBar: boolean = false; // Variable to show/hide progress bar

  constructor(private fileUploadService: FileUploadService, private loginService: LoginService) {
    this.loggedInUser = this.loginService.getLoggedInUser();
  }

  onFileChange(event: any, fileType: string): void {
    const file = event.target.files[0];

    if (file) {
      this.selectedFiles[fileType] = file;
    }
  }

  uploadFiles(): void {
    if (this.loggedInUser) {
      const userId = this.loggedInUser.id;

      // Check if there are selected files
      if (Object.keys(this.selectedFiles).length > 0) {
        this.isUploading = true; // Set uploading status to true
        this.showProgressBar = true; // Set showProgressBar to true

        for (const fileType in this.selectedFiles) {
          if (this.selectedFiles.hasOwnProperty(fileType)) {
            const file = this.selectedFiles[fileType];
            const formData = new FormData();
            formData.append('file', file);

            this.fileUploadService.uploadFiles(formData, userId).subscribe(
              (event: any) => {
                if (event.type === HttpEventType.UploadProgress) {
                  this.uploadProgress = Math.round((event.loaded / event.total) * 100);
                } else if (event.type === HttpEventType.Response) {
                  console.log(`File ${fileType} uploaded successfully`, event.body);
                  this.isUploading = false; // Set uploading status to false after completion
                }
              },
              (error: any) => {
                console.error(`File ${fileType} upload failed`, error);
                this.isUploading = false; // Set uploading status to false on error
              }
            );
          }
        }
      } else {
        alert('No files selected for upload');
      }
    } else {
      alert('User not logged in');
      this.isUploading = false; // Set uploading status to false if user is not logged in
      this.showProgressBar = false; // Set showProgressBar to false
    }
  }
}
