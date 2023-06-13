import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CustomHttpRespone } from '../models/entity/custome-http-response';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';


// api backend
const AUTH_API= environment.publicApi + '/Users' ;

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient ){ }
   

  //login user 

  login(username: string, password: string): Observable<any> {
     
       let logedUser= this.http.post( AUTH_API + '/Login', {
        username,
        password
      });
     return logedUser
     
    }
  

    //register user 

    create(username: string, email: string, password: string,confirmPassword:string ,phoneNumber:string
      ): Observable<any> {
      return this.http.post( AUTH_API + '/register', {
        username,
        email,
        password,
        confirmPassword ,
        phoneNumber,
        role: 'user',
        productId: 1,
      });
    }
 
   

  /**
   * forget Password 
   * @param email 
   * @returns 
   */
  public forgetPassword(email: string): Observable<CustomHttpRespone> {
    return this.http.get<CustomHttpRespone>(`${AUTH_API}/resetpassword/${email}`);
  }
 
 //validation form
 form: FormGroup = new FormGroup({
  userId: new FormControl(null),
  username: new FormControl(''),
  email: new FormControl(''),
  confirmPassword: new FormControl(''),
  userStatus: new FormControl(''),
  phoneNumber: new FormControl(''),
  productId: new FormControl(''),
  password: new FormControl (  [
    Validators.required, 
    Validators.pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{8,}$")
 ]
  )
});

// inialisation form
initializeFormGroup() {
  this.form.setValue({
    userId: null,
    username: null,
    email: null,
    password: null,
    confirmPassword: null,
    userStatus: null,
    phoneNumber:null,
    productId:1,
  });
}


/**
 * get value for update
 * @param accountuser 
 */
populateForm(accountuser: any) {
  this.form.patchValue(accountuser);
}



}