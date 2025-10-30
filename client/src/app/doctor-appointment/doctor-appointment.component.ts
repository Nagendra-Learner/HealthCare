import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../services/http.service';
import { Appointment } from '../../types/Appointment';

@Component({
  selector: 'app-doctor-appointment',
  templateUrl: './doctor-appointment.component.html',
  styleUrls: ['./doctor-appointment.component.scss']
})
export class DoctorAppointmentComponent implements OnInit 
{
  appointments: Appointment[] = [];
  errorMessage = '';

  constructor(private httpService: HttpService) {}

  ngOnInit(): void 
  {
    this.getAppointments();
  }

  getAppointments(): void 
  {
    this.errorMessage = '';
    const userIdString = localStorage.getItem('user_id');
    const doctorId = userIdString ? parseInt(userIdString, 10) : null;
    
    if(doctorId!==null && !isNaN(doctorId))
    {
      this.httpService.getAppointmentByDoctor(doctorId).subscribe({
        next: (data: any[]) => {
          this.appointments = data;
        },
        error: () => {
          console.error('Error fetching appointments!');
          this.errorMessage = "Error fetching appointments!";
        }
      });
    }
    else
    {
      this.errorMessage = 'Invalid Doctor ID!'; 
    }
  }
}