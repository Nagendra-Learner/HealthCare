import { Component } from '@angular/core';

@Component({
  selector: 'app-dashbaord',
  templateUrl: './dashbaord.component.html',
  styleUrls: ['./dashbaord.component.scss']
})
export class DashbaordComponent {
  title='HAM SYSTEM';
role:string='Doctor';
getMenuItems(){
  switch(this.role){
    case 'Doctor':
      return [
        {name:'Doctor Appointments',icon:'bi-calendar-check',link:'/doctor/appointments'},
        {name:'Doctor Availability',icon:'bi-clock',link:'/doctor/availability'},
        {name:'Logout',icon:'bi-box-arrow-right',link:'/logout'}
      ];
      case 'receptionist':
        return [
          {name:'All Appoinments',icon:'bi-list-ul',link:'/receptionist/all-appointments'},
          {name:'Schedule Appointments',icon:'bi-calendar-plus',link:'/receptionist/schedule'},
          {name:'Logout',icon:'bi-box-arrow-right',link:'/logout'}
        ];
        case 'patient':
          return [
            {name:'Patient Appointment',icon:'bi-calendar-event',link:'/patient/appointments'},
            {name:'Schedule Appointment',icon:'bi-calendar-plus',link:'#'},
            {name:'Logout',link:'/logout'}
          ];
          default:
            return [];
  }
}
}
