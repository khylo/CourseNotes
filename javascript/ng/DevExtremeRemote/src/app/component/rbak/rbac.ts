// remote-datagrid-component.ts
import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { importProvidersFrom } from '@angular/core';
import CustomStore from 'devextreme/data/custom_store';
import { DxDataGridModule } from 'devextreme-angular';
import { FormsModule } from '@angular/forms'; // For ngModel

@Component({
  selector: 'app-remote-datagrid',
  standalone: true,
  imports: [DxDataGridModule, FormsModule], // Import FormsModule
  //providers: [importProvidersFrom([provideHttpClient(withInterceptorsFromDi())])],
  templateUrl: './remote-datagrid-component.html',
  styleUrls: ['./remote-datagrid-component.css']
})
export class RemoteDatagridComponent implements OnInit {
  dataSource: any;
  searchQuery: string = ''; // Search box value

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.dataSource = new CustomStore({
      load: (loadOptions: any) => {
        let params = new HttpParams();

        params = params.append('page', loadOptions.skip ? loadOptions.skip / loadOptions.take : 0);
        params = params.append('size', loadOptions.take || 10);

        if (loadOptions.filter) {
          // DevExtreme filter to Spring Boot query param conversion.
          const filter = JSON.parse(loadOptions.filter);
          this.buildFilterParams(params, filter);
        }

        if (loadOptions.sort) {
          const sort = loadOptions.sort[0];
          params = params.append('sortField', sort.selector);
          params = params.append('sortOrder', sort.desc ? 'desc' : 'asc');
        }

        if (this.searchQuery) { // Add search query to parameters
          params = params.append('search', this.searchQuery); // Or your search parameter name
        }

        return this.http.get('http://localhost:8080/api/data', { params })
          .toPromise()
          .then((data: any) => {
            return {
              data: data.content,
              totalCount: data.totalElements
            };
          })
          .catch(error => {
            console.error("Error fetching data:", error);
            throw error;
          });
      }
    });
  }

  onSearch(): void { // Called when search button is clicked or Enter is pressed
    this.dataSource.reload(); // Reload the grid to apply the search
  }

  buildFilterParams(params: HttpParams, filter: any) {
    if (filter && filter.length > 0) {
      filter.forEach((f:any) => {
        if (f.hasOwnProperty('field') && f.hasOwnProperty('value')) {
          params = params.append(f.field, f.value);
        } else if (f.hasOwnProperty('operation') && f.hasOwnProperty('items')) {
          this.buildFilterParams(params, f.items)
        }
      })
    }
  }
}