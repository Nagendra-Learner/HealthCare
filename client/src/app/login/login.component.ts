import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpService } from '../../services/http.service';
import { AuthService } from '../../services/auth.service';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit//todo: complete missing code..\
{
  loginForm!:FormGroup;
  submitted=false;
  constructor(private fb:FormBuilder){}

  ngOnInit(): void {
    this.loginForm=this.fb.group({
      username:['',[Validators.required,Validators.email]],
      password:['',Validators.required,Validators.minLength(8)]
    })
  }

  get f(){
    return this.loginForm.controls;
  }

  onSubmit(){
    this.submitted=true;
    if(this.loginForm.invalid) return;
    alert('Login successful!');
  }

}

