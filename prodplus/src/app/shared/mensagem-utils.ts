import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { DialogComponent } from '../components/dialog/dialog.component';

export function openDialog(
  matDialog: MatDialog,
  descricao: string,
  value: number
): MatDialogRef<DialogComponent, any> {
  return matDialog.open(DialogComponent, {
    width: '350px',
    data: { desc: descricao, value: value },
  });
}

export function openErrorDialog(
  matDialog: MatDialog,
  err: any
): MatDialogRef<DialogComponent, string> {
  console.log(err);
  return matDialog.open(DialogComponent, {
    width: '350px',
    data: { desc: err.error.message, value: 0 },
  });
}
