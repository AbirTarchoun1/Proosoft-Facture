import { Component, OnInit } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatDialogRef } from '@angular/material/dialog'
import { MatSnackBar } from '@angular/material/snack-bar';
import { Module } from 'src/app/models/entity/module';
import { Product } from 'src/app/models/entity/product';
import { ModuleService } from 'src/app/services/module.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-add-module',
  templateUrl: './add-module.component.html',
  styleUrls: ['./add-module.component.css']
})
export class AddModuleComponent implements OnInit {
  moduleServices: Module[] = [];
  productServices: Product[] = [];
  productSelected: any;
  selectedProductIds: number[] = [];
  constructor(public moduleService: ModuleService, private _snackBar: MatSnackBar,private productService: ProductService, public dialogRef: MatDialogRef<AddModuleComponent>) { }

  ngOnInit(): void {
    this.getAllmodules();
    this.getACtiveProducts();
  }


  //get the selected value of products 
  onProductSelectionChange(event: any) {
    const selectedProductId = +event.target.value;
    if (event.target.checked) {
      this.selectedProductIds.push(selectedProductId);
    } else {
      const index = this.selectedProductIds.indexOf(selectedProductId);
      if (index !== -1) {
        this.selectedProductIds.splice(index, 1);
      }
    }
    this.moduleService.form.patchValue({
      productIds: this.selectedProductIds
    });
  }

  //get active products 
  getACtiveProducts() {
      this.productService.getallProducts().subscribe(res => {
        console.log(res);
        this.productServices = res.filter(product => product.productStatus === true);
      });
    }
  

  //get all module 
  getAllmodules() {
    this.moduleService.getallModule().subscribe(res => {
      console.log(res)
      this.moduleServices = res;
    })
  }

  // submit data with context EDITE : CREATE
  onSubmit() {
    let moduleBody = {
      description: this.moduleService.form.value.description,
      moduleName: this.moduleService.form.value.moduleName,
      productIds: this.moduleService.form.value.productIds,
      moduleStatut: this.moduleService.form.value.moduleStatut,
      modulePackage: this.moduleService.form.value.modulePackage,
      createdDate: new Date(),
      lastModificatedDate: new Date(),
    };

    this.moduleService.createModule(moduleBody).subscribe((res) => { 

      window.location.href = '/admin/modules'
      this.onClose();
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
    })

  }

  // reset the form 
  onClear() {
    this.moduleService.form.reset();
  }

  // dialogue close 
  onClose() {
    this.dialogRef.close();
  }
}
