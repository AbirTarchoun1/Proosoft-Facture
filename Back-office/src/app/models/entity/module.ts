export class Module{
    moduleId !: number;
    moduleName !: string;
    description !:string;
    modulePackage !: string;
    moduleStatut!:boolean;
    createdDate!:Date ;
    lastModificatedDate!:Date;
    productIds  !:string[] ;
    isDeleted !: boolean;
    codeMod !: string;
}