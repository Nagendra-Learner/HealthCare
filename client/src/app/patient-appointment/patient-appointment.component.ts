import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../services/http.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Appointment } from '../../types/Appointment';
import { HttpErrorResponse } from '@angular/common/http';
import { MedicalRecord } from '../../types/MedicalRecord';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-patient-appointment',
  templateUrl: './patient-appointment.component.html',
  styleUrls: ['./patient-appointment.component.scss'],
  providers: [DatePipe]
})
export class PatientAppointmentComponent implements OnInit {
  appointmentList: Appointment[] = [];
  errorMessage: string | null = null;
  medicalRecord!: MedicalRecord;

  currentPage = 1;
  pageSize = 5;

  sortColumn: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  filterDoctor: string = '';
  filterTime: string = '';
  constructor(private router: Router, private httpService: HttpService, private datePipe: DatePipe) 
  {}

  ngOnInit(): void 
  {
    this.getAppointments();
  }


  viewMedicalRecord(medicalRecordId: number | undefined)
  {
    console.log(medicalRecordId);
    if(medicalRecordId !== undefined)
    {
      this.router.navigate([`/medicalRecords/view/${medicalRecordId}`]);
    }
    else
    {
      this.errorMessage = "Medical Record with ID: " + medicalRecordId + " not found."; 
    }
  }
  
  getAppointments() {
    const userIdString = localStorage.getItem('user_id');

    const userId = userIdString ? parseInt(userIdString, 10) : null;
    if(userId != null)
    {
      this.httpService.fetchAppointmentsByPatientId(userId).subscribe
      ({
        next: (data) =>{
          this.appointmentList = data;
          console.table(this.appointmentList);
        },
        error: (error: HttpErrorResponse) => {
          this.handleError(error);
        }
      })
    }
  }

  get filteredAppointments(): Appointment[] {
    let filtered = [...this.appointmentList];
    console.table(filtered);
    if (this.filterDoctor.trim()) {
      filtered = filtered.filter(app =>
        app.doctor.username.toLowerCase().startsWith(this.filterDoctor.toLowerCase())
      );
      this.currentPage = 1;
    }

    if (this.filterTime.trim()) {
      filtered = filtered.filter(app => {
        const formattedDate = this.datePipe.transform(app.appointmentTime, 'dd-MMM-yyyy hh:mm a');
        return formattedDate?.toLowerCase().startsWith(this.filterTime.toLowerCase());
      });
      this.currentPage = 1;
    }

    if (this.sortColumn) {
      const getValue = (obj: any, path: string): any =>
        path.split('.').reduce((acc, part) => acc && acc[part], obj);

      const columnMap: { [key: string]: string } = {
        id: 'id',
        appointmentTime: 'appointmentTime',
        status: 'status',
        doctor: 'doctor.username',
        patient: 'patient.username'
      };

      const path = columnMap[this.sortColumn];

      filtered = filtered.sort((a, b) => {
        let aVal = getValue(a, path);
        let bVal = getValue(b, path);

        if (this.sortColumn === 'appointmentTime') {
          aVal = new Date(aVal).getTime();
          bVal = new Date(bVal).getTime();
        }

        if (typeof aVal === 'string') aVal = aVal.toLowerCase();
        if (typeof bVal === 'string') bVal = bVal.toLowerCase();

        if (aVal < bVal) return this.sortDirection === 'asc' ? -1 : 1;
        if (aVal > bVal) return this.sortDirection === 'asc' ? 1 : -1;
        return 0;
      });
    }
    return filtered;
  }

  setSort(column: string) {
    if (this.sortColumn === column) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortColumn = column;
      this.sortDirection = 'asc';
    }
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


  private handleError(error: HttpErrorResponse): void 
  {
    console.log(typeof error)
    console.log(error)
    if (error.error instanceof ErrorEvent) 
    {
      this.errorMessage = `Client-side error: ${error.error.message}`;
    }
    else 
    {
      if(typeof error.error === "string")
      {
        console.log(error.status);
        this.errorMessage = error.error;
      }
      else if(error.error?.message)
      {
        this.errorMessage = error.error.message;
      }
    }
  }
}


