import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../services/http.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-receptionist-appointments',
  templateUrl: './receptionist-appointments.component.html',
  styleUrls: ['./receptionist-appointments.component.scss'],
  providers: [DatePipe] 
})
export class ReceptionistAppointmentsComponent implements OnInit
{
  itemForm: FormGroup;

  appointments: any[]=[];
  selectedAppointment: any;
  showAppointment: boolean=false;

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
      time: this.datePipe.transform(appointment.appointmentTime, 'yyyy-MM-ddTHH:mm')

    });
    this.showAppointment=true;
  }

  onSubmit()
  {
    const formattedTime= this.datePipe.transform(this.itemForm.value.time, 'yyyy-MM-dd HH:mm:ss');

    this.http.reScheduleAppointment(this.itemForm.value.id, formattedTime).subscribe({
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