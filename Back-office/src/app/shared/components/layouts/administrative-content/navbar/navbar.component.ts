import {Component, OnInit } from '@angular/core';
import { TokenStorageService } from 'src/app/services/token-storage.service';


/***
 * 
 * @author Tarchoun Abir 
 * 
 */

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})

export class NavbarComponent implements OnInit {

  isLoggedIn = false;
  currentuser?: string;
  role!: string;

  constructor(private tokenStorageService: TokenStorageService) { }
  showAdminBoard  =false;
  showUserBoard =false;
  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.currentuser = user.username;
      this.role = user.role;
    }
    
    this.showAdminBoard = this.role.includes('admin');
    this.showUserBoard = this.role.includes('user');
  }

  logout(): void {
  this.tokenStorageService.signOut();
  }
  

}
