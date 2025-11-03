import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpService } from '../../services/http.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.scss']
})

export class ForgotPasswordComponent
{
    errorMessage : string | null = null;
    changeForm!: FormGroup;
  message: string | null = null;
  passwordMismatch = false;

  constructor(private fb: FormBuilder, private httpService: HttpService, private router: Router) {}

  ngOnInit(): void {
   this.changeForm = this.fb.group({
  username: ['', [Validators.required, Validators.email]],
  oldPassword: ['', [Validators.required]],
  newPassword: ['', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/)]],
  confirmPassword: ['', [Validators.required]]
});
  }

  get f() {
    return this.changeForm.controls;
  }

  onSubmit()
  {
    if(this.changeForm.valid)
    {
        const { username, oldPassword, newPassword, confirmPassword } = this.changeForm.value;
        if (newPassword !== confirmPassword) {
        this.passwordMismatch = true;
        return;
        }
        this.passwordMismatch = false;

        this.httpService.forgotPassword({ username, oldPassword, newPassword }).subscribe({
        next: (res) => {
            this.message = res.message;
            setTimeout(() => {this.message = null; this.changeForm.reset(); this.router.navigate(['/login']);}, 2000)
        },
        error: (err: HttpErrorResponse) => {
            this.errorMessage = err.error?.message || 'Something went wrong';
        }
        });
    }
}

}
