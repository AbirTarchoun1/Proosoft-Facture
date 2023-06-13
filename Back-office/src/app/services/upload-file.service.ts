import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})


export class UploadFileService {

  //api backend
  private base_url = environment.publicApi;

  constructor(private http: HttpClient) { }

  //http opttion
  httpOptions = {
    headers: new HttpHeaders({
      'content-type': 'application/json'

    })
  }
  //handel api  errors 
  handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      //a client-side or a neetwork error occurend .Handel it accordingly
      console.error('An Error occurend', error.error.message)

    }
    else {
      // the backend may returned an successfully response code  
      // the response body may contain clues as to what went wrong 
      console.error(`backend returned code ${error.status}, ` +
        `body was : ${error.error}`
      );
    }
    // return an observabel with a user-facing error message 
    return throwError('something bad happined , please try again later .');
  };



  /**
   * for upload file 
   * @param formData 
   * @returns 
   */
  uploadFile(formData: FormData): any {
   return this.http.post(environment.publicApi + "/FileUpload", formData,{responseType:'text'});
  }

  /**
   * 
   * @param fileName 
   * @returns file 
   */

  getFile(fileName: string) {
    return this.http.get(this.base_url + '/FileUpload/' + fileName);
  }
}