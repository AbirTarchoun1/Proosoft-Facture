import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsersListComponent } from './users-list/users-list.component';
import { MaterialModule } from 'src/app/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Ng2TelInputModule } from 'ng2-tel-input';
const routes: Routes = [
  { path : '', component :UsersListComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes),MaterialModule,FormsModule,ReactiveFormsModule,
    Ng2TelInputModule],
  exports: [RouterModule, MaterialModule,FormsModule,ReactiveFormsModule,  
      Ng2TelInputModule]
})
export class UsersRoutingModule { }
