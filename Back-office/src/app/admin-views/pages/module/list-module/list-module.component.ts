import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Module } from 'src/app/models/entity/module';
import { AddModuleComponent } from '../add-module/add-module.component';
import { ModuleService } from 'src/app/services/module.service';
import { EditModuleComponent } from '../edit-module/edit-module.component';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-list-module',
  templateUrl: './list-module.component.html',
  styleUrls: ['./list-module.component.css']
})
export class ListModuleComponent implements OnInit {
  moduleForm!: FormGroup;
  moduleData!: Module;
  searchKey!: string;
  showspinner = false;
  ModuleList = [];
  data: any;
  productName!: string;
  moduleName!: string;
  noDataFound !: boolean ;
  module !:Module  [];
  listModule !:Module  [];
  datasource = new MatTableDataSource(this.module);
  displayedColumns: string[] = [
    'codMod',
    'moduleName',
    'productNames',
    'moduleStatut',
    'lastModificatedDate',
    'actions'
  ];


  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort, {}) sort!: MatSort;
  mybreakpoint!: number;
  isSearching !: boolean;
 

  constructor(
    private dialog: MatDialog,
    public _Snackbar: MatSnackBar,
    private moduleService: ModuleService,
    public router: Router,
    public dialogRef: MatDialogRef<AddModuleComponent>, private _snackBar: MatSnackBar) { }


  ngOnInit(): void {
    this.mybreakpoint = window.innerWidth <= 600 ? 1 : 6;
    this.datasource.sort = this.sort;
    this.datasource.paginator = this.paginator;
    this.getAllModules();
  }


  getModuleByName() {
    // check if search criteria is not empty
    if (this.moduleName.trim() !== '') {
      this.moduleService.getModulesByName(this.moduleName).subscribe((data: any) => {
        const dataArray = Array.isArray(data) ? data : [data];
        this.module = dataArray;
        this.datasource.data = data;
        this.datasource = new MatTableDataSource(this.module);
        this.datasource.paginator = this.paginator;
        this.datasource.sort = this.sort;
        if (dataArray.length === 0) {
          this.noDataFound = true;
        } else {
          this.noDataFound = false;
        }
      });
    } else {
      // if search criteria is empty, retrieve all data
      this.moduleService.getallModule().subscribe((data) => {
        this.module = data;
        this.datasource.data = data;
        this.datasource = new MatTableDataSource(this.module);
        this.datasource.paginator = this.paginator;
        this.datasource.sort = this.sort;
        this.noDataFound = false;
      });
    }
  }
  getProductByModule() {
    // Check if search criteria is not empty
    if (this.productName && this.productName.trim() !== '') {
      this.isSearching = true; // Set search flag to true
  
      this.moduleService.getModulesByProduct(this.productName).subscribe(
        (data: any) => {
          const dataArray = Array.isArray(data) ? data : [data];
          this.module = dataArray;
          this.datasource.data = data;
          this.datasource = new MatTableDataSource(this.module);
          this.datasource.paginator = this.paginator;
          this.datasource.sort = this.sort;
          if (dataArray.length === 0) {
            this.noDataFound = true;
          } else {
            this.noDataFound = false;
          }
          this.isSearching = false; // Set search flag to false after data retrieval
        },
        /*(err: any) => {
          // Handle error here
          console.log('An error occurred:', err);
          this.isSearching = false; // Set search flag to false after error handling
  
          if (err.status === 404 && err.error?.message === 'No module with this product name.') {
            // Product not found error
            console.log(err.error.message, "******************");
            this._snackBar.open(err.error.message, '', {
              duration: 4000,
              horizontalPosition: 'center',
              verticalPosition: 'bottom',
              panelClass: ['mat-toolbar', 'mat-warn'],
            });
          } else {
            // Other errors
            console.error('An error occurred:', err);
          }
        }*/
      );
    } else {
      // If search criteria is empty, retrieve all data
      this.moduleService.getallModule().subscribe(
        (data) => {
          this.module = data;
          this.datasource.data = data;
          this.datasource = new MatTableDataSource(this.module);
          this.datasource.paginator = this.paginator;
          this.datasource.sort = this.sort;
          this.noDataFound = false;
        },
        (err: any) => {
          // Handle error here
          console.error('An error occurred:', err);
        }
      );
    }
  }
  // clear data after search by product name
  onProductSearchClear() {
    this.productName = '';
    this.getProductByModule();
  }


  // clear data after search by module name
  onModuleSearchClear() {
    this.moduleName = '';
    this.getModuleByName();
  }


  //pagination and sorting of data 
  ngAfterViewInit() {
    this.datasource.paginator = this.paginator;
    this.datasource.sort = this.sort;
  }


  // get all modules
  getAllModules() {
      this.moduleService.getallModule().subscribe((module) => {
     
      this.datasource.paginator = this.paginator;
      this.datasource.sort = this.sort;
        });

        
      }
      

      //filter by status
      filterByStatuS(moduleStatut: boolean | null) {
        // Use the filterPredicate function of MatTableDataSource to filter the table
        this.datasource.filterPredicate = (data: Module) => {
          const showAll = moduleStatut === null;
          return showAll || (moduleStatut === true ? data.moduleStatut : !data.moduleStatut);
        };
      
        // Set the filter value to 'true', 'false', or ''
        this.datasource.filter = moduleStatut === null ? '' : moduleStatut.toString();
      }

      
  //search 
  onSearchClear() {
    this.searchKey = '';
    this.applyFilter();
  }

  //apply filter
  applyFilter() {
    this.datasource.filter = this.searchKey.trim().toLowerCase();
  }




  /************************
    * OnEdite PoP UP
    * 
    ***/

  onEdit(row: any) {
    this.moduleService.populateForm(row);
    console.log('the row ===>', JSON.stringify(row));
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "50%";
    dialogConfig.height = "50%";
    this.dialog.open(EditModuleComponent, dialogConfig);
  }


  /************************
   * On clear Form
   * 
   ***/

  onClear() {
    
    this.moduleService.initializeFormGroup();
  }



  /***************************
  *  Dialog Config For create 
  * 
  ****/

  onCreate() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "50%";
    dialogConfig.height = "50%";
    this.dialog.open(AddModuleComponent, dialogConfig);
    this.onClear()

  }



}
