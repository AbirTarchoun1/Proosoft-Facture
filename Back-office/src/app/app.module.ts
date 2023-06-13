import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ScriptLoaderService } from './services/loader-script.service';
import { SharedModule } from './shared/shared.module';
import { MaterialModule } from './material/material.module';
import { TapToTopsComponent } from './tap-to-tops/tap-to-tops.component';
import { MatDialogRef } from '@angular/material/dialog';
import { DatePipe } from '@angular/common';
import { authInterceptorProviders } from './helpers/auth.interceptor';
import { HttpClientModule } from '@angular/common/http';
import {Ng2TelInputModule} from 'ng2-tel-input';
@NgModule({
  declarations: [
    AppComponent,
    TapToTopsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    SharedModule, 
    MaterialModule,
    HttpClientModule,
    Ng2TelInputModule
  ],
  exports:[
    SharedModule,
    MaterialModule,
    HttpClientModule,
    Ng2TelInputModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    {provide: MatDialogRef, useValue: {close: (_dialogResult: any) => { }} }, DatePipe, ScriptLoaderService,authInterceptorProviders
  ],
  bootstrap: [AppComponent],
  
})
export class AppModule { 


}