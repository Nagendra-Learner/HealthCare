import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../../types/User';
import { HttpService } from '../../services/http.service';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
})
export class RegistrationComponent implements OnInit
{

  user: User = new User(0, '', '', '', "");
  itemForm!: FormGroup;
  successMessage: string = ''; 
  errorMessage: string = '';

  constructor(private fb:FormBuilder, private httpService: HttpService,private router:Router)
  {

  }

  ngOnInit(): void 
  {
    this.itemForm = this.fb.group({
      username:['',[Validators.required, Validators.pattern(/^[a-zA-Z0-9]+$/)]],
      email:['',[Validators.required, Validators.email]],
      password:['',[Validators.required, Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/)]],
      //password:['',[Validators.required, Validators.pattern(/^[A-za-z0-9]+$/)]],
      role:['',Validators.required],
      specialty: [''],
      availability: ['']
    });

    this.itemForm.get('role')?.valueChanges.subscribe((roleValue) => {
      const specialtyControl = this.itemForm.get('specialty');
      const availabilityControl = this.itemForm.get('availability');

      if(roleValue == "DOCTOR")
      {
        specialtyControl?.setValidators([Validators.required]);
        availabilityControl?.setValidators([Validators.required]);
      }
      else
      {
        specialtyControl?.clearValidators();
        availabilityControl?.clearValidators();
      }

      specialtyControl?.updateValueAndValidity;
      availabilityControl?.updateValueAndValidity;
    });
  }

  onSubmit()
  {
    this.successMessage = '';
    this.errorMessage = '';
    
    if(this.itemForm.valid)
    {

      Object.values(this.itemForm.controls).forEach((control) => {
        control.markAsTouched();
      });
      
      console.log("itemForm.value = \n" + this.itemForm.value);

      let registerObservable;

      switch(this.itemForm.value.role)
      {
        case "PATIENT":
          registerObservable = this.httpService.registerPatient(this.itemForm.value);
          break;
        case "DOCTOR":
          registerObservable = this.httpService.registerDoctors(this.itemForm.value);
          break;
        case "RECEPTIONIST":
          registerObservable = this.httpService.registerReceptionist(this.itemForm.value);
          break;
        default:
          this.errorMessage = "Invalis Role Selected!";
          return;
      }

      registerObservable.subscribe({
        next: (registeredUser) => {
          this.user = registeredUser;
          this.successMessage = "Registration Successful.";
     
          console.log("User value = \n" + this.user);
          setTimeout(() => {this.successMessage = ""; this.itemForm.reset(); this.router.navigate(['/login'])}, 2000);
        },
        error: (errorObj) => {
          this.successMessage = '';
          if(errorObj instanceof Error)
          {
            this.errorMessage = errorObj.message + "!";
          }
          else
          {
            this.errorMessage = "An unexpected error occurred during registration!";
          }
        } });
    }
    else
    {
      this.errorMessage = "Please fill out all required fields!";
    }
   
  }

  get f()
  {
    return this.itemForm.controls;
  }

}
