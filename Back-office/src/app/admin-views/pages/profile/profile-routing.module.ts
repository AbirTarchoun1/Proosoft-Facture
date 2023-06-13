import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProfileUsersComponent } from './profile-users/profile-users.component';

const routes: Routes = [
  {
    path: '', component : ProfileUsersComponent,
  },
  {
    path:'profile',component:ProfileUsersComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }
