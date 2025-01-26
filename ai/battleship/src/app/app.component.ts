import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameBoardComponent } from './components/game-board/game-board.component';
import { ControlPanelComponent } from './components/control-panel/control-panel.component';

@Component({
  standalone: true,
  selector: 'app-root',
  templateUrl: `app.component.html`,
  styles: `
    .container {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      height: 100vh;
    }
    
  `,
  imports: [GameBoardComponent, ControlPanelComponent]
})
export class AppComponent {
  startGame() {
    // Logic to start the game
    console.log('Game Started');
  }

  resetGame() {
    // Logic to reset the game
    console.log('Game Reset');
  }
}
