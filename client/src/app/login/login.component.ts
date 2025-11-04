import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpService } from '../../services/http.service';
import { AuthService } from '../../services/auth.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit 
{
  itemForm!: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private httpService: HttpService,
    private router: Router
    ) {}

  ngOnInit(): void {
    this.itemForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  onSubmit(): void {
    if (this.itemForm.valid) {
      this.httpService.login(this.itemForm.value).subscribe({
        next: (response) => {
          console.log(response);
          localStorage.setItem("token", response.token);
          localStorage.setItem("role", response.role);
          localStorage.setItem("user_id", response.userId);
          localStorage.setItem("username", response.username);
          console.log(localStorage.getItem("role"));
          this.router.navigate(["/dashboard"]);
        },
        error: (error: HttpErrorResponse) => {
          this.handleError(error);
        },
      });

    } else {
      this.errorMessage = 'Please fill out the form correctly.';
    }
  }

  get f()
  {
    return this.itemForm.controls;
  }

  private handleError(error: HttpErrorResponse): void 
  {
    console.log(typeof error)
    console.log(error)
    if (error.error instanceof ErrorEvent) 
    {
      this.errorMessage = `Client-side error: ${error.error.message}`;
    }
    else 
    {
      if(typeof error.error === "string")
      {
        console.log(error.status);
        this.errorMessage = error.error;
      }
      else if(error.error?.message)
      {
        this.errorMessage = error.error.message;
      }
    }
  }

}
