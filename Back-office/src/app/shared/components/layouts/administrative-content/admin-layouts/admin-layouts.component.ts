import { AfterViewInit, Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Helpers } from 'src/app/helpers/helpers';



@Component({
  selector: 'app-admin-layouts',
  templateUrl: './admin-layouts.component.html',
  styleUrls: ['./admin-layouts.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class AdminLayoutsComponent implements AfterViewInit {

  constructor() { }

  ngAfterViewInit() {

    // initialize layout: handlers, menu ...
    Helpers.initLayout();

  }

}
