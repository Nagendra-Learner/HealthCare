import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { HttpService } from '../../services/http.service';
import { DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Doctor } from '../../types/Doctor';

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

  today: string = '';
  currentTime: string = '';
  

  constructor(public httpService:HttpService,private formBuilder: FormBuilder,private datePipe: DatePipe) {
    this.itemForm = this.formBuilder.group({
      patientId: ['',[ Validators.required]],
      doctorId: ['',[ Validators.required]],
      appointmentDate: ['', Validators.required],
      time: ['', Validators.required]
  });
   }

  ngOnInit(): void {
    this.loadDoctors();

    const now = new Date();
    this.today = now.toISOString().split('T')[0]; // yyyy-MM-dd
    this.currentTime = now.toTimeString().slice(0, 5); // HH:mm
  }

  get f() {
    return this.itemForm.controls;
  }

  onSubmit()   
  {
   
    if(this.itemForm.valid)
    {
    // const formattedTime = this.datePipe.transform(this.itemForm.controls['time'].value, 'yyyy-MM-dd HH:mm:ss');

    // Update the form value with the formatted date
    // this.itemForm.controls['time'].setValue(formattedTime);
    const { appointmentDate, time } = this.itemForm.value;
    const now = new Date();
    const selectedDateTime = new Date(`${appointmentDate}T${time}`);

    if (selectedDateTime < now) {
      this.errorMessage = 'You cannot select a past date or time!';
      return;
    }

    this.httpService.ScheduleAppointmentByReceptionist( this.itemForm.value).subscribe({
      next:(data)=>{
      this.itemForm.reset();
      this.successMessage="Appointment Save Successfully";
      setTimeout(()=> this.successMessage=null, 3000);
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
    this.httpService.getDoctorsForReceptionist().subscribe({
      next: (data: Doctor[]) => {
        this.doctors = data;
        this.availableDoctors = this.doctors.filter(
          d => d.availability?.toLowerCase() === 'yes'
        );
      },
      error: (error: HttpErrorResponse) => this.errorMessage(error)
    });
  }

}
