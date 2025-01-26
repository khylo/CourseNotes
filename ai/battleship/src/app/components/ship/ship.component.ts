import { Component, Input } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-ship',
  template: '<div class="ship"></div>',
  styles: ['.ship { width: 30px; height: 30px; background-color: blue; }']
})
export class ShipComponent {
  @Input() x!: number;
  @Input() y!: number;
}
