import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModuleRoutingModule } from './module-routing.module';
import { ListModuleComponent } from './list-module/list-module.component';
import { AddModuleComponent } from './add-module/add-module.component';
import { EditModuleComponent } from './edit-module/edit-module.component';


@NgModule({
  declarations: [
    ListModuleComponent,
    AddModuleComponent,
    EditModuleComponent
  ],
  imports: [
    CommonModule,
    ModuleRoutingModule
  ]
})
export class ModuleModule { }
