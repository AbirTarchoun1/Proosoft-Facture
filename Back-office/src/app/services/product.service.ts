import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import * as _ from 'lodash';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { Product } from '../models/entity/product';
import { map } from 'rxjs/internal/operators/map';
import { tap } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})



export class ProductService {

  //api backend
  private base_url = environment.publicApi + '/Products';
  private base_url2 = environment.publicApi + '/Module';
  idIfEdit: any = null;
  products: any;
  constructor(private http: HttpClient) { }

  //http opttion
  httpOptions = {
    headers: new HttpHeaders({
      'content-type': 'application/json'

    })
  }
  //handel api  errors 
  handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      //a client-side or a neetwork error occurend .Handel it accordingly
      console.error('An Error occurend', error.error.message)

    }
    else {
      // the backend may returned an successfully response code 
      // the response body may contain clues as to what went wrong 
      console.error(`backend returned code ${error.status}, ` +
        `body was : ${error.error}`
      );
    }
    // return an observabel with a user-facing error message 
    return throwError('something bad happined , please try again later .');
  };


  /**
   * 
   * @returns all Products 
   */

  getallProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.base_url + '/GetAllProducts');
  }

  
  /**
   * for update  a product
   * @param item 
   * @returns 
   */

  updateProduct(item: Product) {
    return this.http.put<Product>(this.base_url + '/UpdateProduct/' + item.productId, item, this.httpOptions);

  }

  /**
   * to  get product by id
   * @param id 
   * @returns 
   */

  getProductByid(id: number) {
    return this.http.get<Product>(this.base_url + '/' + id);

  }


  /**
   * to add product
   * @param bodyReques 
   * @returns 
   */

  addProduct(bodyReques: any): any {

    return this.http.post<any>(this.base_url + '/addNewProduct', bodyReques);
  }

  // create new product intervention

   createNewProduct=(data: any)=>{ return this.http.post(this.base_url +"/createNewProduct", data)}
  
   private newlyAddedProductIdSubject: BehaviorSubject<number | null> = new BehaviorSubject<number | null>(null)
   newlyAddedProductId$ = this.newlyAddedProductIdSubject.asObservable();
 
   setNewlyAddedProductId(productId: number | null): void {
    this.newlyAddedProductIdSubject.next(productId);
  }

    /**
   * get module by product
   * @param productName
   * @returns 
   */
  
    getModulesByProduct(productName: string) {
      return this.http.get(this.base_url2 + `/GetModuleByProduct?productName=${productName}`);
    }

    
 // delete products 
 
  deleteProduct(id: number): Observable<Product> {
    const url = `${this.base_url}/DeleteProduct/${id}`;
    const updatedProduct = { isDeleted: true }; // set IsDeleted flag to true
    return this.http.put<Product>(url , updatedProduct, this.httpOptions).pipe(
      tap(_ => console.log(`deleted product id=${id}`)),
    );
  }


  /**
   * upload file product
   * @param file 
   * @returns 
   */

  uploadProductImage(/*nameImage:any*/file: any): any {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.http.post<any>(environment.publicApi + '/FileUpload', formData);
  }


  // get Access 
  getAccess(): any {
    return this.http.get<any>(environment.publicApi + '/Access/GetAllAccess');
  }


  // Get All users 
  getUser(): any {
    return this.http.get<any>(environment.publicApi + '/Users/GetAllUser');
  }

   // product by name 
  searchProductByName(productName: string): Observable<Product[]> {
    const url = this.base_url + `/GetProductByName?productName=${productName}`;
    return this.http.get<Product[]>(url);
  }

  /**
   * patchValue for update 
   * @param product 
   */

  populateForm(product: any) {
    this.form.patchValue(product);

  }

  //validation form
  form: FormGroup = new FormGroup({
    productId: new FormControl(),
    description: new FormControl(),
    productStatus: new FormControl(),
    publishDate: new FormControl(),
    productName: new FormControl('',[Validators.required],this.checkProductName.bind(this)),
    productVersion: new FormControl(),
    createdDate: new FormControl(''),
    lastModificatedDate: new FormControl(''),
    LogoFilePath: new FormControl(),});

  // inialisation form
  initializeFormGroup() {
    this.form.setValue({
      productId: '',
      description: '',
      productName: '',
      productVersion: '',
      productStatus: false,
      publishDate: '',
      createdDate: '',
      lastModificatedDate: new Date(),
      LogoFilePath: '',

    });
  
  }


  checkProductName(control: AbstractControl): Observable<ValidationErrors | null> {
    const productName = control.value;
    return this.http.get<any[]>(this.base_url + `/GetProductByName?productName=${productName}`).pipe(map((products) => {
        if (products.length > 0) {
          return { productNameExists: true };
        } else {
          return null;
        }
      })
    );
  }

}



