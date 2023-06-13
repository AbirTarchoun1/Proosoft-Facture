import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Error404Component } from './pages/error404/error404.component';
import { Error500Component } from './pages/error500/error500.component';
import { AdminLayoutsComponent } from './shared/components/layouts/administrative-content/admin-layouts/admin-layouts.component';
import { ClientLayoutsComponent } from './shared/components/layouts/Client-content/client-layouts/client-layouts.component';
import { AuthLayoutComponent } from './shared/components/layouts/auth-layout/auth-layout.component';
import { AuthGuard } from './guard/auth.guard';
import { MaterialModule } from './material/material.module';

const routes: Routes = [

  { path: '', redirectTo: '/auth', pathMatch: 'full' },

  {
    path: 'auth', component: AuthLayoutComponent,

    children: [
      {
        path: '', loadChildren: () => import('./admin-views/auth/auth.module').then(m => m.AuthModule)
      }
    ]
  },

  {

    path: "admin", component: AdminLayoutsComponent,

    children: [

    
      {
        path: 'users', loadChildren: () => import('./admin-views/pages/users/users.module').then(m => m.UsersModule)
      },

      {
        path: 'profile', loadChildren: () => import('./admin-views/pages/profile/profile.module').then(m => m.ProfileModule)
      },
      { path: 'modules', loadChildren: () => import('./admin-views/pages/module/module.module').then(m => m.ModuleModule) /*,canActivate: [AuthGuard,AdminGuard]*/
    },
     
      {
        path: 'products', loadChildren: () => import('./admin-views/pages/products/products.module').then(m => m.ProductsModule)
      },
      
    ]

  },
  
  { path: "user", component: ClientLayoutsComponent ,
  
 
 
}

  , {
    "path": "error404",
    "component": Error404Component
  },
  {
    "path": "error500",
    "component": Error500Component
  },
  {
    "path": "**",
    "redirectTo": "error404",
    "pathMatch": "full"
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes),MaterialModule],
  exports: [RouterModule,MaterialModule]
})
export class AppRoutingModule { }
