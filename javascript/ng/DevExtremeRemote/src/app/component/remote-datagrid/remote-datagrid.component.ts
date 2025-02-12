  import { Component, OnInit } from '@angular/core';
  import { HttpClient, HttpParams } from '@angular/common/http';
  import CustomStore from 'devextreme/data/custom_store';
  import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http'; 
  import { DxDataGridModule } from 'devextreme-angular';  
  import { FormsModule } from '@angular/forms'; // For ngModel

  @Component({
    selector: 'app-remote-datagrid',
    standalone: true, // Mark as standalone
    imports: [DxDataGridModule, FormsModule],
    //providers: [provideHttpClient(withInterceptorsFromDi())],
    templateUrl: './remote-datagrid.component.html',
    styleUrl: './remote-datagrid.component.scss'
  })
  export class RemoteDatagridComponent implements OnInit {
    // Angular component (e.g., data-grid.component.ts)

    dataSource: any;
    searchQuery: string = '';

    constructor(private http: HttpClient) { }

    ngOnInit(): void {
      this.dataSource = new CustomStore({
        load: (loadOptions: any) => {
          let params = new HttpParams();

          params = params.append('page', loadOptions.skip ? loadOptions.skip / loadOptions.take : 0); // Calculate page number
          params = params.append('size', loadOptions.take || 10); // Page size
          if (loadOptions.filter) {
            // Implement your filter logic and add it to the params
            // Example (adapt as needed for your filtering):
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

          return this.http.get('http://localhost:8080/api/data', { params }) // Your Spring Boot endpoint
            .toPromise()
            .then((data: any) => {
              return {
                data: data.content, // Assuming your Spring Boot API returns data in a 'content' array
                totalCount: data.totalElements // Important for paging in DevExtreme
              };
            })
            .catch(error => {
              console.error("Error fetching data:", error);
              throw error; // Re-throw the error to be handled by DevExtreme
            });
        }
      });
    }
      
  onSearch(): void { // Called when search button is clicked or Enter is pressed
    this.dataSource.load(); // Reload the grid to apply the search.. not working.. datat comes back but not rendered
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