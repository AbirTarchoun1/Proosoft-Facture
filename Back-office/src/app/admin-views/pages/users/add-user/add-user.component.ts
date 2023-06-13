import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { User } from 'src/app/models/entity/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {
 
  fieldTextType!: boolean;
  fieldTextType2!: boolean;
  accountData!: User;
  account: User[] = [];
  message = ''; 
  submitted = false;
  isSignUpFailed = false;
  isSuccessful = true
  datasource = new MatTableDataSource(this.account);
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort, {}) sort!: MatSort
  
  constructor(public userService: UserService, private _snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<AddUserComponent>, @Inject(MAT_DIALOG_DATA)
    public data:  {
      userId: Number;
    }) { this.accountData = {} as User;
    
  }
  ngOnInit(): void {
    this.datasource.sort = this.sort;
    this.datasource.paginator = this.paginator;
    }

    onSubmit(): void {
     let userBody={
      username:this.userService.form.value.username,
      confirmPassword:this.userService.form.value.confirmPassword,
      password:this.userService.form.value.password,
      email:this.userService.form.value.email,
      createdDate:new Date(),
      lastModifiedDate:new Date(),
    };
    if (this.userService.form.valid) {
      this.userService.createNewUser(userBody).subscribe((response: any) => {
      this.account.push(response);
      window.location.href = '/admin/users'
      this.onClose()
      },
      
      (err) => {
        //get error from backend  
        if (err.error.message){
          console.log(err.error.message,"******************");
          this._snackBar.open(err.error.message, '', {
            duration: 4000,
            horizontalPosition: 'center',
            verticalPosition: 'bottom',
            panelClass: ['mat-toolbar', 'mat-warn'],
          });
          return;
        }
      });
    }
  }

  // show password 
  toggleFieldTextType() {
  this.fieldTextType = !this.fieldTextType;
  }

  // show ConfirmPassword 
  toggleFieldTextType2() {
  this.fieldTextType2 = !this.fieldTextType2;
  }

  onClose() {
    this.dialogRef.close();
  }
  

  //clear data
  onClear() {
    this.userService.initializeFormGroup();
  }
}




