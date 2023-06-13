import { Module } from "./module";

export class Product {
    productId !: number;
    userId !: number;
    productName !:string;
    productVersion !: string;
    publishDate !: Date;
    description !: string;
    logo !: string;
    productStatus! : boolean;
    createdDate !: Date;
    lastModificatedDate !: Date;
    accessId !: number;
    fileName!:string;
    codProd!: string;
    isDeleted !: boolean;
   // productModules!: Module[]; // define productModules property as an array of Module objects
}