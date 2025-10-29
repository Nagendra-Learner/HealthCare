import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpService } from '../../services/http.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
})
export class RegistrationComponent implements OnInit{
  itemForm!:FormGroup;
  constructor(private fb:FormBuilder,private route:Router){
  }
  ngOnInit(): void {
    this.itemForm=this.fb.group({
      username:['',[Validators.required,Validators.pattern(/^[a-zA-Z0-9]+$/)]],
      email:['',[Validators.required,Validators.email]],
      password:['',[Validators.required,Validators.pattern(/^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@$%&*])(?=.*[0-9])[A-Za-z\d!@$%&*]{8, }$/)]],
      role:['',Validators.required]
    })

  }
  onSubmit(){
    if(this.itemForm.valid){
    console.log(this.itemForm.value);
    this.route.navigate(['/client/login']);
    }
  }
  get f(){
    return this.itemForm.controls;
  }


} //todo: complete missing code..
