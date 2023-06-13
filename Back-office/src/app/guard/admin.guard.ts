import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../services/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(
    private router: Router,
    private tokenStorageService: TokenStorageService
  ) {}


  isAdmin(): boolean {
    const adminRole = this.tokenStorageService.getUser().role; //  get the user's role from token or storage
    return adminRole === 'admin'; // Modify the condition 
  }
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    const isLoggedIn = !!this.tokenStorageService.getToken();
    const isAdmin = this.isAdmin(); // Replace with your admin role check logic

    if (isLoggedIn && isAdmin) {
      return true;
    } else {
      this.router.navigate(['/auth']);
      return false;
    }
  }
}