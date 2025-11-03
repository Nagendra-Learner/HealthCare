import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { AppComponent } from './app.component';
import { DashbaordComponent } from './dashbaord/dashbaord.component';
import { PatientAppointmentComponent } from './patient-appointment/patient-appointment.component';
import { ScheduleAppointmentComponent } from './schedule-appointment/schedule-appointment.component';
import { DoctorAppointmentComponent } from './doctor-appointment/doctor-appointment.component';
import { DoctorAvailabilityComponent } from './doctor-availability/doctor-availability.component';
import { ReceptionistAppointmentsComponent } from './receptionist-appointments/receptionist-appointments.component';
import { ReceptionistScheduleAppointmentsComponent } from './receptionist-schedule-appointments/receptionist-schedule-appointments.component';
import { MedicalRecordCreateComponent } from './medicalrecord-create/medicalrecord-create.component';
import { MedicalRecordEditComponent } from './medicalrecord-edit/medicalrecord-edit.component';
import { MedicalRecordViewComponent } from './medicalrecord-view/medicalrecord-view.component';
import { ChangePasswordComponent } from './change-password/change-password.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'dashboard', component: DashbaordComponent }, 
  { path: 'patient-appointment', component: PatientAppointmentComponent }, 
  { path: 'schedule-appointment', component: ScheduleAppointmentComponent }, 
  { path: 'doctor-appointment', component: DoctorAppointmentComponent }, 
  { path: 'doctor-availability', component: DoctorAvailabilityComponent },
  { path: 'receptionist-appointments', component: ReceptionistAppointmentsComponent },
  { path: 'receptionist-schedule-appointments', component: ReceptionistScheduleAppointmentsComponent },
  {path: 'medicalRecords/create/:patientId/:doctorId/:appointmentId', component: MedicalRecordCreateComponent},
  {path: 'medicalRecords/edit/:medicalRecordId/:patientId/:doctorId/:appointmentId', component: MedicalRecordEditComponent},
  {path: 'medicalRecords/view/:medicalRecordId', component: MedicalRecordViewComponent},
  {path: 'change-password', component: ChangePasswordComponent},

  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
