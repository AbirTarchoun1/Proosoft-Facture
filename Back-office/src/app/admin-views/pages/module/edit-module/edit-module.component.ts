import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Module } from 'src/app/models/entity/module';
import { Product } from 'src/app/models/entity/product';
import { ModuleService } from 'src/app/services/module.service';
import { ProductService } from 'src/app/services/product.service';


@Component({
  selector: 'app-edit-module',
  templateUrl: './edit-module.component.html',
  styleUrls: ['./edit-module.component.css']
})
export class EditModuleComponent implements OnInit {

  @ViewChild('moduleForm', { static: false })
  moduleForm !: FormGroup;
  moduleData !: Module;
  module: Module[] = [];
  searchKey!: string;
  showspinner = false;
  productServices : Product[]=[]
  datasource = new MatTableDataSource(this.module)
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort, {}) sort!: MatSort;
  productSelected: any;
  selectedProductIds: number[] = [];
  constructor (public moduleService :ModuleService, private productService:ProductService,public dialogRef: MatDialogRef<EditModuleComponent>) { }
  
  ngOnInit(): void {
    this.getAllmodule();
   this.getAllProducts();
   const selectedProducts = this.moduleService.form.get('productIds')?.value;
   this.selectedProductIds = [...selectedProducts];
  }

  
  isProductSelected(productId: number): boolean {
    return this.selectedProductIds.includes(productId);
  }

  //on change products 
  onChangeproduct(event:any){
    this.productSelected=event.target.value;
    console.log('the  selected =>',this.productSelected)
  }
  
  // get all products 
  getAllProducts(){
    this.productService.getallProducts().subscribe(res => {
      console.log(res)
      this.productServices = res;
    })
  }
  
  
  //get all module 
  getAllmodule() {
    this.moduleService.getallModule().subscribe((response: any) => {
      this.datasource.data = response;
    })
  }


    //get the selected value of products 
    onProductSelectionChange(event: any) {
      const selectedProductId = +event.target.value;
      if (event.target.checked) {
        // Add the newly selected product to the list of selected products
        this.selectedProductIds.push(selectedProductId);
      } else {
        // Remove the unselected product from the list of selected products
        const index = this.selectedProductIds.indexOf(selectedProductId);
        if (index !== -1) {
          this.selectedProductIds.splice(index, 1);
        }
      }
    }
  
    onSubmit() {
      // Get the updated module data from the form
      const updatedModule = this.moduleService.form.value;
      
      // Set the selected product IDs in the form value
      updatedModule.productIds = this.selectedProductIds;
      
      // Call the updateModule function to update the module data in the backend
      this.moduleService.updateModule(updatedModule).subscribe((response) => {
        // Find the index of the updated module in the array
        const index = this.module.findIndex(m => m.moduleId=== response.moduleId);
        if (index !== -1) {
          // Update the existing module object in the array
          this.module[index] = response;
          // Update the datasource to reflect the changes
          this.datasource.data = this.module;
        }
      });
    
      window.location.href = '/admin/modules'
    }
    

  // dialogue close 
  onClose() {
    this.dialogRef.close();

  }
  //clear data
  onClear() {
    this.moduleService.initializeFormGroup();
  }

}