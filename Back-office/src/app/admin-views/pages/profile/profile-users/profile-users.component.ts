import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NgForm } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/app/models/entity/user';
import { AuthService } from 'src/app/services/auth.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-profile-users',
  templateUrl: './profile-users.component.html',
  styleUrls: ['./profile-users.component.css']
})
export class ProfileUsersComponent implements OnInit {
 
  /***************************
   * variable initialisations 
   * 
   */
  currentUser !: any;
  hide = true;
  panelOpenState = true;
  user !: FormGroup;
  public refreshing: boolean | undefined;



  constructor(private token: TokenStorageService,public router: Router,private userService: UserService
    , public _Snackbar : MatSnackBar,private fb: FormBuilder) {

    //validation Form
    this.user = this.fb.group({
      userId: new FormControl(),
      username: new FormControl(),
      email: new FormControl(),
    });
  }
 

  //memoire permanant 
  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    this.user.patchValue(this.currentUser);
    console.log(this.currentUser)
  }


 /*********************************
   * update current user info
   */
  
 save() {}


  

  }
