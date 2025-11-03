import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { HttpService } from '../../services/http.service';
import { DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Doctor } from '../../types/Doctor';
import { Router } from '@angular/router';
import { Patient } from '../../types/Patient';

@Component({
  selector: 'app-receptionist-schedule-appointments',
  templateUrl: './receptionist-schedule-appointments.component.html',
  styleUrls: ['./receptionist-schedule-appointments.component.scss'],
  providers: [DatePipe] 
  
})
export class ReceptionistScheduleAppointmentsComponent implements OnInit {
  
  itemForm: FormGroup;
  formModel:any={};
  successMessage:any;
  errorMessage: any;
  isAdded: boolean=false;
  doctors: Doctor[] = [];
  availableDoctors: Doctor[] = [];
  patients: Patient[] = [];

  today: string = '';
  currentTime: string = '';
  

  constructor(public httpService:HttpService,private formBuilder: FormBuilder,private datePipe: DatePipe, private router: Router) {
    this.itemForm = this.formBuilder.group({
      patientId: ['',[ Validators.required]],
      doctorId: ['',[ Validators.required]],
      appointmentDate: ['', Validators.required],
      time: ['', Validators.required]
  }, {validators: this.futureDateTimeValidator});
   }

  ngOnInit(): void {
    this.loadDoctors();

    const now = new Date();
    this.today = now.toISOString().split('T')[0]; // yyyy-MM-dd
    this.currentTime = now.toTimeString().slice(0, 5); // HH:mm
    this.httpService.fetchAllPatients().subscribe((data) => {
      this.patients = data;
    })
  }

  get f() {
    return this.itemForm.controls;
  }

  onSubmit()   
  {
   
    if(this.itemForm.valid)
    {

    this.httpService.scheduleAppointment( this.itemForm.value).subscribe({
      next:(data)=>{
      this.successMessage="Appointment Save Successfully";
      setTimeout(()=> {this.successMessage=null;  this.itemForm.reset(); this.router.navigate(['/receptionist-appointments'])}, 3000);
      },
      error:(err)=>{
        this.itemForm.reset();
        this.errorMessage="Please enter the correct details.";
        setTimeout(()=> this.errorMessage=null, 3000);
        }
    })
  }
    
  }

  loadDoctors(): void {
    this.httpService.fetchAvailableDoctorsByReceptionist().subscribe({
      next: (data: Doctor[]) => {
        this.doctors = data;
        this.availableDoctors = this.doctors.filter(
          d => d.availability?.toLowerCase() === 'yes'
        );
      },
      error: (error: HttpErrorResponse) => this.errorMessage(error)
    });
  }

  private futureDateTimeValidator(group: AbstractControl) : { [key: string]: any} | null
    {
      const date = group.get('appointmentDate')?.value;
      const time = group.get('time')?.value;
  
      if(date && time)
      {
        const selectedDateTime = new Date(`${date}T${time}:00`);
        if(selectedDateTime < new Date())
        {
          return {pastDateTime: true};
        }
      }
      return (null);
    }

}
