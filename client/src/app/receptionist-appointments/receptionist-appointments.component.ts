import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../services/http.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AbstractControl, ValidationErrors } from '@angular/forms';
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
  successMessage: string | null = null;
  filteredAppointments: Appointment[] = [];
  doctorFilter: string = '';
  patientFilter: string = '';
  dateFilter: string = '';

  filterText: string = '';
  sortColumn: string = '';
  sortDirections: {[key: string]: 'asc' | 'desc'}= {};
  currentSortColumn: string= '';

  currentPage = 1;
  pageSize = 5;

  constructor(private fb: FormBuilder, private http: HttpService, private datePipe: DatePipe)
  {
    this.itemForm= fb.group({
      id:['', Validators.required],
      time:['', [Validators.required, this.futureDateValidator]]
    });
  }

  ngOnInit(): void {
    this.loadAppointments();
     this.sortColumn= 'id';
  }

  loadAppointments()
  {
    this.http.fetchAllAppointments().subscribe(data=>{
      this.appointments=data;
      this.filteredAppointments = this.appointments;
    });
  }

  applyFilter() {
  const doctor = this.doctorFilter.toLowerCase();
  const patient = this.patientFilter.toLowerCase();
  const date = this.dateFilter.toLowerCase();

  this.filteredAppointments = this.appointments.filter(app => {
    const doctorMatch = app.doctor.username.toLowerCase().startsWith(doctor);
    const patientMatch = app.patient.username.toLowerCase().startsWith(patient);
    const dateStr = this.datePipe.transform(app.appointmentTime, 'dd-MMM-yyyy hh:mm a')?.toLowerCase() || '';
    const dateMatch = dateStr.startsWith(date);

    return doctorMatch && patientMatch && dateMatch;
  });
   this.currentPage = 1;
}

  
  sortData(column: string) {

    const currentDirection= this.sortDirections[column] || 'desc';
    const newDirection= currentDirection === 'asc' ? 'desc' : 'asc';

    this.sortDirections={};
    this.sortDirections[column]= newDirection;
    this.currentSortColumn= column;

  const getValue = (obj: any, path: string) =>
    path.split('.').reduce((acc, part) => acc && acc[part], obj);


    this.filteredAppointments.sort((a, b) => {
      const valA = getValue(a, column);
      const valB = getValue(b, column);

      if (typeof valA === 'string') {
        return newDirection === 'asc'
          ? valA.localeCompare(valB)
          : valB.localeCompare(valA);
      } else {
        return newDirection === 'asc'
          ? valA - valB
          : valB - valA;
      }
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
    if(this.itemForm.valid)
    {
      const formattedTime= this.datePipe.transform(this.itemForm.value.time, 'yyyy-MM-dd HH:mm:ss');

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
}

  resetForm()
  {
    this.showAppointment=false;
    this.selectedAppointment=null;
    this.itemForm.reset();
  }

  get totalPages() {
    return Math.ceil(this.filteredAppointments.length / this.pageSize);
  }

  get totalPagesArray() {
    return Array.from({ length: this.totalPages }, (_, i) => i + 1);
  }

  get paginatedAppointments() {
    const start = (this.currentPage - 1) * this.pageSize;
    return this.filteredAppointments.slice(start, start + this.pageSize);
  }

  changePage(page: number) {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  markAsCompleted(appointment: Appointment) {
  if (confirm(`Mark appointment #${appointment.id} as completed?`)) {
    this.http.completedAppointment(appointment.id).subscribe({
      next: () => {
        this.successMessage = "Appointment marked as completed.";
        this.errorMessage = null;
        setTimeout(() => {
          this.loadAppointments();
          this.successMessage = null; // Clear after showing
        }, 2000);

      },
      error: () => {
        this.successMessage = null;
        this.errorMessage = "Failed to mark appointment as completed.";
      }
    });
  }
}

  futureDateValidator(control: AbstractControl): ValidationErrors | null {
  if (!control.value) return null;

  const selectedDateTime = new Date(control.value);
  const now = new Date();

  return selectedDateTime < now ? { pastDate: true } : null;
}

}