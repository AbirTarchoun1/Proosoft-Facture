import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/entity/user';
import { UserService } from 'src/app/services/user.service';
import { AddUserComponent } from '../add-user/add-user.component';
import Swal from 'sweetalert2';
import { EditUsersComponent } from '../edit-users/edit-users.component';
import { MatSnackBar } from '@angular/material/snack-bar';
declare var $: any;

/*****************************
 * 
 * @author Tarchoun Abir 
 * 
 **********/

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {
  accountForm!: FormGroup;
  accountData!: User;
  account!: User[];
  searchKey!: string;
  showspinner = false;
  createdDate !: Date;
  data: any;
  nodata = false;
  role !: string;
  accountlist !: User[];
  email!: string;
  user!: User[];
  message !: string;
  username !: string;
  datasource = new MatTableDataSource(this.account);
  displayedColumns: string[] = [
    'username',
    'email',
    'level',
    'createdDate',
    'lastModificatedDate',
    'actions',
  ];


  // for filter by status 
  statusFilter = new FormControl('');
  filterValues: any = {
    userstatus: '',
  }

  currentUser !: User;
  dataLoading: boolean = true;


  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort, {}) sort!: MatSort;
  id = this.route.snapshot.params['id'];
  mybreakpoint!: number;
  lastModificatedDate: any;

  constructor(
    private dialog: MatDialog,
    public _Snackbar: MatSnackBar,
    private accountservice: UserService,
    private route: ActivatedRoute,
    public router: Router,
    public dialogRef: MatDialogRef<AddUserComponent>
  ) {
    this.accountData = {} as User;
    this.datasource =
      this.datasource = new MatTableDataSource(this.account);
  }

  ngOnInit(): void {
    this.mybreakpoint = window.innerWidth <= 600 ? 1 : 6;
    this.datasource.sort = this.sort;
    this.datasource.paginator = this.paginator;
    this.getAllUsers();
    //for filter by status 
    this.fieldListener();


  }

  ngAfterViewInit() {
    this.datasource.paginator = this.paginator;
    this.datasource.sort = this.sort;
  }

  /*******************
   * Search By Email
   ******************/

  SearchUserByEmail(): void {
    // check if search criteria is not empty
    if (this.email.trim() !== '') {
      this.accountservice.searchUsersByEmail(this.email).subscribe((data) => {
        const dataArray = Array.isArray(data) ? data : [data];
        this.account= dataArray;
        this.datasource.data = data;
        this.datasource = new MatTableDataSource(this.account);
        this.datasource.paginator = this.paginator;
        this.datasource.sort = this.sort;
      });
    } else {
      // if search criteria is empty, retrieve all data
      this.accountservice.GetallUsers().subscribe((data) => {
        this.account = data;
        this.datasource.data = data;
        this.datasource = new MatTableDataSource(this.account);
        this.datasource.paginator = this.paginator;
        this.datasource.sort = this.sort;
      });
    }

    // if no data show no data
    if (this.data == null) {

      this.datasource = new MatTableDataSource(this.account);
    }
  }

  //clear search for email
  onSearchClear() {
    this.email = '';
    this.SearchUserByEmail()
  }


  /*********************
   * Search By Username
   *********************/

  SearchUserByUsername(): void {
        // check if search criteria is not empty
        if (this.username.trim() !== '') {
          this.accountservice.searchUsersByUsername(this.username).subscribe((data) => {
            const dataArray = Array.isArray(data) ? data : [data];
            this.account= dataArray;
            this.datasource.data = data;
            this.datasource = new MatTableDataSource(this.account);
            this.datasource.paginator = this.paginator;
            this.datasource.sort = this.sort;
          });
        } else {
          // if search criteria is empty, retrieve all data
          this.accountservice.GetallUsers().subscribe((data) => {
            this.account = data;
            this.datasource.data = data;
            this.datasource = new MatTableDataSource(this.account);
            this.datasource.paginator = this.paginator;
            this.datasource.sort = this.sort;
          });
        }
    
        // if no data show no data
        if (this.data == null) {
    
          this.datasource = new MatTableDataSource(this.account);
        }
      }

  
 

  //clear search for username 
  onSearchClearuser() {
    this.username = '';
    this.SearchUserByUsername()
  }



  /*******************
   *  Get All Users
   * 
   *******************/

  getAllUsers() {
    this.accountservice.GetallUsers().subscribe((data: any) => {
      if (Array.isArray(data)) {
        // Filter the data based on the user role
        const users = data.filter(user => user.level !== 'admin');
        
        // Set the filtered data as the datasource
        this.datasource = new MatTableDataSource(users);
  
        // Re-render the table
        this.datasource.paginator = this.paginator;
        this.datasource.sort = this.sort;
  
        // for the filter by status 
        this.datasource.filterPredicate = this.createFilter();
      } else {
        console.error('Data is not an array');
      }
    });
  }



  /******************
   * delete a user
   ******************/

  onDeleteUser(userId: number) {
    Swal.fire({
      title: 'Are you sure to delete this User?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes',
      cancelButtonText: 'No',
      cancelButtonColor: 'gray'
    }).then((result) => {
      if (result.value) {
        this.accountservice.deleteUser(userId)
          .subscribe(
            response => {
              console.log(response)
              if (result.dismiss === Swal.DismissReason.cancel) {
              }

              this.getAllUsers()

            });
        //snackBar success 
        this._Snackbar.open(" Deleted Successfully", + '' + "K" + '' + '⚡', {
          duration: 5000,
          horizontalPosition: "right",
          verticalPosition: "bottom",
          panelClass: ["mat-toolbar", "mat-success"],
        });
      }

    });

  }


  /************************
    * OnEdite PoP UP
    * 
    ***********************/

  onEdit(row: any) {
    this.accountservice.populateForm(row);
    console.log('the row ===>', JSON.stringify(row));
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '40%';
    dialogConfig.data = {
      userId: row.userId,
    };

    this.dialog.open(EditUsersComponent, dialogConfig);
  }



  /************************
   * On clear Form
   * 
   ***/

  onClear() {
    this.accountservice.initializeFormGroup();
  }

  /************************
   * Update User Status
   * 
   ***/

  updateaStatusAccount(element: User) {

    Swal.fire({
      title: 'Are you sure to update status  !?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes.',
      cancelButtonText: 'No.',
    }).then((result) => {
      if (result.value) {
        const index = this.datasource.data.indexOf(element);
        this.datasource.data[index].userStatus = this.changeStatut(this.datasource.data[index].userStatus);
        this.accountservice.UpdateUserStatut(this.datasource.data[index]).subscribe((res) => {

          console.log(res);

        });
        Swal.fire('updated!', ' status updated successfully.', 'success');
      } else if (result.dismiss === Swal.DismissReason.cancel) {
      }
    });


  }

  /***************************
  *  Dialog Config For create 
  * 
  ****/

  onCreate() {

    this.accountservice.form.reset();
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "40%";
    this.dialog.open(AddUserComponent, dialogConfig);

  }


  /****************************
   *  Switch Status
   * 
   ****/

  changeStatut(currentStatut: string) {
    if (currentStatut == "ACTIVE") {
      return "BLOCKED";
    }
    else if (currentStatut == "PENDING") {
      return "ACTIVE";
    }
    return "PENDING";
  }


  /**********************************
   * Filter by UserStatus 
   * 
   *****************************/


  private fieldListener() {
    this.statusFilter.valueChanges
      .subscribe(
        userStatus => {
          this.filterValues.userStatus = userStatus;
          this.datasource.filter = JSON.stringify(this.filterValues);
        },

      )


  }

  // for filter by status : return the user status 
  createFilter(): (user: User, filter: string) => boolean {
    let filterFunction = function (this: any, user: any, filter: any): boolean {
      let searchTerms = JSON.parse(filter);
      return user.userStatus.indexOf(searchTerms.userStatus) !== -1;

    }

    return filterFunction;

  }

  // clear filter data 
  clearFilter() {
    this.statusFilter.setValue('');
  }


}
