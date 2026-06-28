import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';

export interface DeleteDialogData {
  credentialName: string
}

@Component({
  selector: 'app-credential-delete-dialog',
  imports: [MatDialogModule, MatButtonModule, MatIconModule],
  templateUrl: './credential-delete-dialog.html',
  styleUrl: './credential-delete-dialog.scss',
})
export class CredentialDeleteDialog {
  constructor(@Inject(MAT_DIALOG_DATA) public data: DeleteDialogData) {}
}
