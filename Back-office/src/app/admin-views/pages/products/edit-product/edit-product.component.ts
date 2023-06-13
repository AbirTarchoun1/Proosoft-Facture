import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NotificationService } from 'src/app/services/notification.service';
import { ProductService } from 'src/app/services/product.service';
import { UploadFileService } from 'src/app/services/upload-file.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
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

  constructor(public productService: ProductService, public dialogRef: MatDialogRef<EditProductComponent>, private _Snackbar: MatSnackBar, private notificationService: NotificationService, private uploadFileService: UploadFileService) { }

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

  //fo add an produit resize

  async onSubmit() {


        this.productService.updateProduct(this.productService.form.value).subscribe((res) => {
          console.log(res)
          window.location.href = "/admin/products"
        },

          (err: any) => { console.log(' errr :: ===============>', err) });
          
        this.onClose();
        
      }
   
    
    

  onClear() {
    //this.productService.form.reset();
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
  
  
  onFileSelected = (event:any) => {
    if (event.target.files.length > 0) {
      this.logo = event.target.files[0];
      this.logoName = event.target.files[0].name;
    }
  }
  
}