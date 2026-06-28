import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CredentialDeleteDialog } from './credential-delete-dialog';

describe('CredentialDeleteDialog', () => {
  let component: CredentialDeleteDialog;
  let fixture: ComponentFixture<CredentialDeleteDialog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CredentialDeleteDialog],
    }).compileComponents();

    fixture = TestBed.createComponent(CredentialDeleteDialog);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
