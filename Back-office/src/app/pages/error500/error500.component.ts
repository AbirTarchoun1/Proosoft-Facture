import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';

@Component({
  selector: 'app-error500',
  templateUrl: './error500.component.html',
  styleUrls: ['./error500.component.css']
})

/***
 * 
 * @author Tarchoun Abir 
 * 
 */

export class Error500Component implements OnInit , AfterViewInit, OnDestroy {

  constructor() { }

  ngOnInit() {
    $('body').addClass('empty-layout bg-silver-100');
  }

  ngAfterViewInit() { }

  ngOnDestroy() {
    $('body').removeClass('empty-layout bg-silver-100');
  }
}
