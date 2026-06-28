import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CredentialManager } from './credential-manager';

describe('CredentialManager', () => {
  let component: CredentialManager;
  let fixture: ComponentFixture<CredentialManager>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CredentialManager],
    }).compileComponents();

    fixture = TestBed.createComponent(CredentialManager);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
