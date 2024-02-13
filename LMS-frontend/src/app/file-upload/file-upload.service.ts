// file-upload.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FileUploadService {
  private apiUrl = 'http://localhost:9090/api/files/upload';

  constructor(private http: HttpClient) {}

  uploadFiles(formData: FormData, userId: number): Observable<any> {
    formData.append('userId', userId.toString());

    return this.http.post(this.apiUrl, formData);
  }
}
