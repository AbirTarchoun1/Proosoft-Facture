import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NotificationService } from 'src/app/services/notification.service';
import { ProductService } from 'src/app/services/product.service';
import Swal from 'sweetalert2';
import { AddProductsComponent } from '../add-products/add-products.component';
import { Product } from 'src/app/models/entity/product';
import { environment } from 'src/environments/environment';
import { EditProductComponent } from '../edit-product/edit-product.component';
import { animate, style, transition, trigger } from '@angular/animations';
import { Module } from 'src/app/models/entity/module';

@Component({
  selector: 'app-list-products',
  templateUrl: './list-products.component.html',
  styleUrls: ['./list-products.component.css'],
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('0.3s', style({ opacity: 1 }))
      ])
    ])
  ]
})
export class ListProductsComponent implements OnInit {
  listPrducts: Product[]=[];
  filteredProducts: Product[] =[];
  modules: Module[] =[];
  UserProduct: any
  productStatus = 'All Products';
  PathImage = environment.logoPath ;
  showNoModulesMessage!: boolean;
  selectedProductModules: Module[] = [];
  products: Product[] = [];
  selectedProduct !: Product ;
  newlyAddedProductId!: number | null;
  constructor(private productService: ProductService, private dialog: MatDialog,private _Snackbar: MatSnackBar, private notificationService: NotificationService) { }

  ngOnInit(): void {
    this.productService.newlyAddedProductId$.subscribe(productId => {
    this.newlyAddedProductId = productId;});
    this.getProducts();
  }

  isNewlyAddedProduct(productId: number): boolean {
    return this.newlyAddedProductId !== null && productId === this.newlyAddedProductId;
  }


 


  // get all products 
  getProducts() {
    this.productService.getallProducts().subscribe((res: any) => {
      this.listPrducts = res;
      this.filteredProducts = this.listPrducts;
    
    });
  }

 // get products - module childrens 



  //search by products status 
  filterByStatus(status: boolean) {
    this.filteredProducts = this.listPrducts.filter(product => product.productStatus === status);
  }


/*************************
* delete product
*/
onDeleteProduct(productId: number) {
  Swal.fire({
    title: 'Are you sure to delete this product?',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Yes',
    cancelButtonText: 'No',
    cancelButtonColor: 'gray'
  }).then((result) => {
    if (result.value) {
      this.productService.deleteProduct(productId).subscribe(
        () => {
          const productIndex = this.listPrducts.findIndex(p => p.productId === productId);
          if (productIndex !== -1) {
            this.listPrducts.splice(productIndex, 1);
          }
          
          //snackBar success 
          this._Snackbar.open(" Deleted Successfully", + '' + "K" + '' + '⚡', {
            duration: 8000,
            horizontalPosition: "right",
            verticalPosition: "bottom",
            panelClass: ["mat-toolbar", "mat-success"],
          });
        },
        error => {
          console.log(error);
        }
      );
    } else if (result.dismiss === Swal.DismissReason.cancel) {
      // Do nothing
    }
  });
}

  


  //dialog edite product 

  onEditproduct(row: any) {
    console.log('======================id===>',this.productService.form.value.id);
    
    this.productService.populateForm(row);
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "40%";
    dialogConfig.height= "80%";
    this.dialog.open(EditProductComponent, dialogConfig); }

    // create dialog config
    onCreateproduct() { 
      this.productService.initializeFormGroup();
      const dialogConfig = new MatDialogConfig();
      dialogConfig.disableClose = true;
      dialogConfig.autoFocus = true;
      dialogConfig.width = "40%";
      dialogConfig.height= "80%";
      this.dialog.open(AddProductsComponent, dialogConfig);
  
    }


}
