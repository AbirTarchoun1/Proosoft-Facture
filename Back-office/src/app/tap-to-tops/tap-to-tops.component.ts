import { ViewportScroller } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';

@Component({
  selector: 'app-tap-to-tops',
  templateUrl: './tap-to-tops.component.html',
  styleUrls: ['./tap-to-tops.component.css']
})
export class TapToTopsComponent implements OnInit {

  public show: boolean = false;

  constructor(private viewScroller: ViewportScroller) { }

  ngOnInit(): void {
  }
  

  // @HostListener Decorator
  @HostListener("window:scroll", [])
  onWindowScroll() {
    let number = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    if (number > 700) { 
      this.show = true;
    } else {
      this.show = false;
    }
  }

  tapToTop() {
    this.viewScroller.scrollToPosition([0, 0]);
  }

}