import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import * as _ from 'lodash';
import { Contact } from '../models/entity/contact';

@Injectable({
  providedIn: 'root'
})

export class ContactService {


  //api backend
  private base_url = environment.publicApi + '/Contact';

  constructor(private http: HttpClient) { }

  /****************************
   * 
   * @param item 
   * @returns new contact form
   */

  createContact(contact: any): Observable<Contact> {
  return this.http.post<Contact>(`${this.base_url}` + '/addNewContact', contact);
  }

  /***************************
   * 
   * @returns all contact
   */

  getContact(): Observable<Contact[]> {
    return this.http.get<Contact[]>(`${this.base_url}` + '/GetAllContact')
  }

  //validation form
  form: FormGroup = new FormGroup({
    object: new FormControl('', [Validators.required]),
    text: new FormControl('',[Validators.required]),
    email: new FormControl('', [Validators.required,Validators.email]),
    companyName: new FormControl('', [Validators.required]),
    
  });

  // inialisation form
  initializeFormGroup() {
    this.form.setValue({
      object: '',
      email: '',
      text:'',
      companyName:''
    });
  }

}
