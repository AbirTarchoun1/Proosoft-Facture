import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { event } from 'jquery';
import { AuthService } from 'src/app/services/auth.service';

/***********************
 * 
 * @author Tarchoun Abir 
 * 
 ***/

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  @Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
  })
  //form initialiaze
  form: any = {
    username: "",
    email: "",
    fiscaleCode: "",
    password: "",
    confirmPassword: "",
    phoneNumber: "", 
    productId:1
  }


  fieldTextType!: boolean;
  fieldTextType2!: boolean;
  isSuccessful = false;
  isSignUpFailed = false;
  message = '';
  defaultValue = '+39' ;
  loading = false ;

  constructor(private authService: AuthService, private _snackBar: MatSnackBar) {
  }
  ngOnInit() {
  
  }
  onSubmit(): void {
   
    const {
      username, email, password,
      confirmPassword,phoneNumber
    } = this.form;

      
     this.loading=true;
     
    this.authService.create(username, email, password, confirmPassword,phoneNumber).subscribe(
      
      (data: any) => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },

      (err) => {
        //get error from backend : password did not match || email exist 
        if (err.error.message){
          console.log(err.error.message,"******************");
          this._snackBar.open(err.error.message, '', {
            duration: 4000,
            horizontalPosition: 'center',
            verticalPosition: 'top',
            panelClass: ['mat-toolbar', 'mat-warn'],
          });
          return;
        }
      //  registred  failed  erreur from the server 
      else if ((this.isSignUpFailed = true)) {
        this._snackBar.open('Server Error ', '', {
          duration: 4000,
          horizontalPosition: 'center',
          verticalPosition: 'top',
          panelClass: ['mat-toolbar', 'mat-warn'],
        });
      }
      this.loading=false;
    }
  );
}




  // show password 
  toggleFieldTextType() {
    this.fieldTextType = !this.fieldTextType;
  }

  // show password 
  toggleFieldTextType2() {
    this.fieldTextType2 = !this.fieldTextType2;
  }


  onCountryChange(event: { dialCode: any; phoneNumber :any })
  {
    console.log(event.dialCode)
    event.dialCode ;
  }


  password(form: any) {
    const { value : password } = form.get('password');
    const { value: confirmPassword } = form.get('confirmpassword');
    return password === confirmPassword ? true : {passwordNotMatch: false };
  }

}
