import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class FileApiService {
  constructor(private http: HttpClient) {
  }

  uploadFile(fileType: string, formData: FormData) {
    return this.http.post("http://localhost:8081/api/file/upload/" + fileType, formData);
  }
}
