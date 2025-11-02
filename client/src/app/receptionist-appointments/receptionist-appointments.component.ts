import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../services/http.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { Appointment } from '../../types/Appointment';

@Component({
  selector: 'app-receptionist-appointments',
  templateUrl: './receptionist-appointments.component.html',
  styleUrls: ['./receptionist-appointments.component.scss'],
  providers: [DatePipe] 
})
export class ReceptionistAppointmentsComponent implements OnInit
{
  itemForm: FormGroup;

  appointments: Appointment[]=[];
  selectedAppointment: any;
  showAppointment: boolean=false;
  errorMessage:string|null=null;

  constructor(private fb: FormBuilder, private http: HttpService, private datePipe: DatePipe)
  {
    this.itemForm= fb.group({
      id:['', Validators.required],
      time:['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadAppointments();
  }

  loadAppointments()
  {
    this.http.getAllAppointments().subscribe(data=>{
      this.appointments=data;
    });
  }

  editAppointments(appointment: any)
  {
    this.selectedAppointment=appointment;
    this.itemForm.patchValue({
      id: appointment.id,
      time: this.datePipe.transform(appointment.appointmentTime, 'yyyy-MM-dd HH:mm')
    });
    this.showAppointment=true;
  }

  onSubmit()
  {
    //const formattedTime= this.datePipe.transform(this.itemForm.value.time, 'yyyy-MM-dd HH:mm:ss');
    // const formattedTime = this.itemForm.value.time;
    // console.log(formattedTime);
    // const { appointmentDate, time } = this.itemForm.value;
    //   const now = new Date();
    //   const selectedDateTime = new Date(`${appointmentDate}T${time}`);
      const formattedTime= this.datePipe.transform(this.itemForm.value.time, 'yyyy-MM-dd HH:mm:ss');

      // if (selectedDateTime < now) {
      //   this.errorMessage = 'You cannot select a past date or time!';
      //   return;
      // }
    this.http.reScheduleAppointment(this.itemForm.value.id, {time: formattedTime || ''}).subscribe({
      next:()=>
        {
          alert("Appointment updated successfully");
          this.resetForm();
          this.loadAppointments();
        },
        error:(err)=>{
          alert("Failed to reschedule appointment");
        }
    })
  }

  resetForm()
  {
    this.showAppointment=false;
    this.selectedAppointment=null;
    this.itemForm.reset();
  }

}