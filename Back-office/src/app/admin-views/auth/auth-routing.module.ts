import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ResetpasswordComponent } from './resetpassword/resetpassword.component';
import { MaterialModule } from 'src/app/material/material.module';
import {Ng2TelInputModule} from 'ng2-tel-input';

/***
 * 
 * @author Tarchoun Abir 
 * 
 ***/

const routes: Routes = [
  {
    path: '', component : LoginComponent,
  },
  {
    path:'login',component:LoginComponent
  },
  {
    path:'register',component:RegisterComponent
  },
  {
    path:'resetpassword',component:ResetpasswordComponent
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes), MaterialModule,Ng2TelInputModule],
  exports: [RouterModule, MaterialModule,Ng2TelInputModule]
})
export class AuthRoutingModule { }
