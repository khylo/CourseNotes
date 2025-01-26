import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-control-panel',
  templateUrl: './control-panel.component.html',
  styleUrls: ['./control-panel.component.css'],
  imports: [CommonModule]
})
export class ControlPanelComponent {
  @Output() startGame = new EventEmitter<void>();
  @Output() resetGame = new EventEmitter<void>();

  start() {
    this.startGame.emit();
  }

  reset() {
    this.resetGame.emit();
  }
}
