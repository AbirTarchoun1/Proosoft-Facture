import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { FormsModule } from '@angular/forms';
import { ResetpasswordComponent } from './resetpassword/resetpassword.component';

/***
 * 
 * @author Tarchoun Abir 
 * 
 ***/

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    ResetpasswordComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
   AuthRoutingModule,
  ],
exports:[
  FormsModule,

]
})
export class AuthModule { }
