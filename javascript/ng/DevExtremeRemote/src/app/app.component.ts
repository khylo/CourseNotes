import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { RemoteDatagridComponent } from './component/remote-datagrid/remote-datagrid.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RemoteDatagridComponent ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'DevExtremeRemote';
}
