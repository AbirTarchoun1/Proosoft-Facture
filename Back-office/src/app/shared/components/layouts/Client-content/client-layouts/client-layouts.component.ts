import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Helpers } from 'src/app/helpers/helpers';


@Component({
  selector: 'app-client-layouts',
  templateUrl: './client-layouts.component.html',
  styleUrls: ['./client-layouts.component.css']
})
export class ClientLayoutsComponent implements AfterViewInit  {

  constructor() { }


  ngAfterViewInit() {

    // initialize layout: handlers, menu ...
    Helpers.initLayout();

  }
}
