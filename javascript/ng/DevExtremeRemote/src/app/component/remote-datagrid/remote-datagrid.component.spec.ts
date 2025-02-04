import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoteDatagridComponent } from './remote-datagrid.component';

describe('RemoteDatagridComponent', () => {
  let component: RemoteDatagridComponent;
  let fixture: ComponentFixture<RemoteDatagridComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RemoteDatagridComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RemoteDatagridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
