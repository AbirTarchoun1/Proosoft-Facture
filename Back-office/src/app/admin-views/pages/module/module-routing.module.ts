import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { MaterialModule } from 'src/app/material/material.module';
import { ListModuleComponent } from './list-module/list-module.component';
import { CKEditorModule } from 'ckeditor4-angular';
import { NgSelectModule } from '@ng-select/ng-select';

const routes: Routes = [
  {
    path :'', component:ListModuleComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes),MaterialModule,FormsModule, ReactiveFormsModule,CKEditorModule,NgSelectModule ],
  exports:[MaterialModule,ReactiveFormsModule,FormsModule,NgSelectModule]
})
export class ModuleRoutingModule { }
