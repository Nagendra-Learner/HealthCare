import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpService } from '../../services/http.service';
import { AuthService } from '../../services/auth.service';
import { catchError, of, tap } from 'rxjs';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {
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
      this.httpService.Login(this.itemForm.value).subscribe({
        next: (response) => {
          console.log(response);
          // this.authService.saveToken(response.token);
          // this.authService.saveUserId(response.userId);
          // this.authService.saveRole(response.roles[0]);
          localStorage.setItem("token", response.token);
          localStorage.setItem("role", response.role);
          localStorage.setItem("user_id", response.userId);
          console.log(localStorage.getItem("role"));
          this.router.navigate(["/dashboard"]);
        },
        error: (error: string) => {
          this.errorMessage = 'Invalid username or password';
          console.error("Login error:", error);
          return of(null);
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

}
