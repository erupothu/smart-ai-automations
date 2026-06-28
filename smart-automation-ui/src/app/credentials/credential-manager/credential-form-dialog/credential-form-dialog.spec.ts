import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CredentialFormDialog } from './credential-form-dialog';

describe('CredentialFormDialog', () => {
  let component: CredentialFormDialog;
  let fixture: ComponentFixture<CredentialFormDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CredentialFormDialog],
    }).compileComponents();

    fixture = TestBed.createComponent(CredentialFormDialog);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
