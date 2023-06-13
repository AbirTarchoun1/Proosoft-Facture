import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminLayoutsComponent } from './administrative-content/admin-layouts/admin-layouts.component';
import { ClientLayoutsComponent } from './Client-content/client-layouts/client-layouts.component';
import { NavbarComponent } from './administrative-content/navbar/navbar.component';
import { SidebarComponent } from './administrative-content/sidebar/sidebar.component';
import { FooterComponent } from './administrative-content/footer/footer.component';
import { RouterModule } from '@angular/router';
import { MaterialModule } from 'src/app/material/material.module';
import { AuthLayoutComponent } from './auth-layout/auth-layout.component';
import { NavbarClientComponent } from './Client-content/navbar-client/navbar-client.component';
import { SidebarClientComponent } from './Client-content/sidebar-client/sidebar-client.component';
import { FooterClientComponent } from './Client-content/footer-client/footer-client.component';


@NgModule({
  declarations: [
    AdminLayoutsComponent,
    ClientLayoutsComponent,
    SidebarComponent,
    NavbarComponent,
    FooterComponent,
    AuthLayoutComponent,
    NavbarClientComponent,
    SidebarClientComponent,
    FooterClientComponent,
    
    
  ],
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule
  ]
 ,
	exports: [ 
    MaterialModule,
    
	],
})
export class LayoutsModule { }
