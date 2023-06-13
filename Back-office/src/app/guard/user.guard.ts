import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { TokenStorageService } from '../services/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserGuard implements CanActivate {
  constructor(
    private router: Router,
    private tokenStorageService: TokenStorageService
  ) {}

  isUser(): boolean {
    const userRole = this.tokenStorageService.getUser().role; //  get the user's role from token or storage
    return userRole === 'user'; // Modify the condition 
  }
  canActivate(
    _next: ActivatedRouteSnapshot,
    _state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    const isLoggedIn = !!this.tokenStorageService.getToken();
    const isUser = this.isUser(); // Replace with user role check logic

    if (isLoggedIn && isUser) {
      return true;
    } else {
      this.router.navigate(['/auth']);
      return false;
    }
  }
}