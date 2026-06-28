import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NodeConfigPanel } from './node-config-panel';

describe('NodeConfigPanel', () => {
  let component: NodeConfigPanel;
  let fixture: ComponentFixture<NodeConfigPanel>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NodeConfigPanel],
    }).compileComponents();

    fixture = TestBed.createComponent(NodeConfigPanel);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
