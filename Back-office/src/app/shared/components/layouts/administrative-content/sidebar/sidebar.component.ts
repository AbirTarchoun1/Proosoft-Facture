import { AfterViewInit, Component, OnInit } from '@angular/core';
@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements AfterViewInit ,OnInit {
  showAdminBoard  =false;
  showUserBoard =false;
  private role !: string;
  constructor() { }

  ngAfterViewInit()  {
	}

  ngOnInit(): void {
    this.showAdminBoard = this.role.includes('admin');
    this.showUserBoard = this.role.includes('user');
  }

}
