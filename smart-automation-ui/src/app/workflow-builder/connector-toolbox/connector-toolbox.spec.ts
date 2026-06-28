import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConnectorToolbox } from './connector-toolbox';

describe('ConnectorToolbox', () => {
  let component: ConnectorToolbox;
  let fixture: ComponentFixture<ConnectorToolbox>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConnectorToolbox],
    }).compileComponents();

    fixture = TestBed.createComponent(ConnectorToolbox);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
