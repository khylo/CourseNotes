import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; 

@Component({
  standalone: true,
  selector: 'app-game-board',
  templateUrl: './game-board.component.html',
  styleUrls: ['./game-board.component.css']
})
export class GameBoardComponent {
  board: string[][] = Array(10).fill('').map(() => Array(10).fill(''));

  constructor() {}

  placeShip(x: number, y: number) {
    // Logic to place a ship on the board
    if (this.board[x][y] === '') {
      this.board[x][y] = 'S';
    }
  }

  attack(x: number, y: number) {
    // Logic to handle attacks
    if (this.board[x][y] === 'S') {
      this.board[x][y] = 'H'; // Hit
    } else if (this.board[x][y] === '') {
      this.board[x][y] = 'M'; // Miss
    }
  }
}
