import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ScriptLoaderService } from 'src/app/services/loader-script.service';
import { NotificationService } from 'src/app/services/notification.service';
import { ProductService } from 'src/app/services/product.service';
import { UploadFileService } from 'src/app/services/upload-file.service';
declare var $: any;
@Component({
  selector: 'app-add-products',
  templateUrl: './add-products.component.html',
  styleUrls: ['./add-products.component.css']
})
export class AddProductsComponent implements OnInit {
 
  listPrducts: any;
  UserProduct: any
  AccessProduct: any;
  imageFile: any;
  typeImage: any;
  isEditeMode = false;
  //selected items 
  userSelected: any;
  accessSelected: any;
  productId: any;
  logo!: File;
  logoName:string='';
  errorMessage = '';
  newlyAddedProductId!: number;
  //newProductId= this.productService.form.value.productId;
  //newlyAddedProducts: number[] = [];
  constructor(private _script: ScriptLoaderService,private _snackBar: MatSnackBar, public productService: ProductService, public dialogRef: MatDialogRef<AddProductsComponent>, private _Snackbar: MatSnackBar, private notificationService: NotificationService, private uploadFileService: UploadFileService) { }

  ngOnInit(): void {
    this.getProducts();
    this.productService.populateForm(this.productId)
  }

 
  onSelectFile(file: any) {

    this.imageFile = file.target.files[0];
    this.typeImage = file.target.files[0].name.split('.').pop();
  }


  getProducts() {
    this.productService.getallProducts().subscribe((res: any) => {
      this.listPrducts = res;
    })
  }

  
  isNewlyAddedProduct(productId: number): boolean {
    return this.newlyAddedProductId === productId;
  }
  
// cerate New Product 
createNewProduct(){
  const formData = new FormData();  //this.productService.form.value

  formData.append('ProductName',this.productService.form.value.productName)
  formData.append('ProductVersion',this.productService.form.value.productVersion) 
  formData.append('ProductStatus',this.productService.form.value.productStatus) 
  formData.append('Description',this.productService.form.value.description)
  formData.append('PublishDate' ,new Date(Date.now()).toDateString())
  formData.append('LastModificatedDate',new Date(Date.now()).toDateString())
  formData.append('FileName',this.logoName)
  formData.append('File',this.logo)

  this.productService.createNewProduct(formData).subscribe({
    next: res=>{
     window.location.href="/admin/products";
     
      this.newlyAddedProductId=this.productService.form.value.productId;

    },

    error: err=>{
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
    }
    
  });
 
}

onFileSelected = (event:any) => {
  if (event.target.files.length > 0) {
    this.logo = event.target.files[0];
    this.logoName = event.target.files[0].name;
  }
}

  
  onClear() {
    this.productService.form.reset();
    this.productService.initializeFormGroup();
  }
  // dialogue close 
  onClose() {
    this.dialogRef.close();
  }


  //CKeditor

  config = {
    height: 250,

    image: {
      styles: [
        'alignLeft', 'alignCenter', 'alignRight'
      ],

      // Configure the available image resize options.
      resizeOptions: [
        {
          name: 'resizeImage:original',
          label: 'Original',
          value: null
        },
        {
          name: 'resizeImage:5',
          label: '5%',
          value: '5'
        },
        {
          name: 'resizeImage:10',
          label: '10%',
          value: '10'
        },
        {
          name: 'resizeImage:25',
          label: '25%',
          value: '25'
        },
        {
          name: 'resizeImage:50',
          label: '50%',
          value: '50'
        },
        {
          name: 'resizeImage:75',
          label: '75%',
          value: '75'
        }
      ],

      // You need to configure the image toolbar, too, so it shows the new style
      // buttons as well as the resize buttons.
      toolbar: [
        'imageStyle:alignLeft', 'imageStyle:alignCenter', 'imageStyle:alignRight',
        '|',
        'ImageResize',
        '|',
        'imageTextAlternative'
      ]
    },
    language: 'en'
  };


}
