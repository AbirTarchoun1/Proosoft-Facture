import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileRoutingModule } from './profile-routing.module';
import { ProfileUsersComponent } from './profile-users/profile-users.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/material/material.module';

@NgModule({
  declarations: [
  
    ProfileUsersComponent
  ],
  imports: [
    CommonModule,
    ProfileRoutingModule,
    FormsModule, 
    MaterialModule,
    ReactiveFormsModule
  ],
  exports:[
    MaterialModule,
    FormsModule,
    ReactiveFormsModule

  ]
})
export class ProfileModule { }
