import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../services/http.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-medicalrecord-create',
  templateUrl: './medicalrecord-create.component.html',
  styleUrls: ['./medicalrecord-create.component.scss']
})
export class MedicalRecordCreateComponent 
{
    
medicalRecordForm!: FormGroup;
  successMessage: string | null = null;
  errorMessage: string | null = null;
  patientId!: number;
  doctorId!: number;
  appointmentId!: number;
  today!: string;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private httpService: HttpService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.patientId = Number(this.route.snapshot.paramMap.get('patientId'));
    this.doctorId = Number(this.route.snapshot.paramMap.get('doctorId'));
    this.appointmentId = Number(this.route.snapshot.paramMap.get('appointmentId'));

    const now = new Date();
    this.today = now.toISOString().slice(0, 16);
    console.log(this.today);

    this.medicalRecordForm = this.fb.group({
      doctorId: [this.doctorId],
      patientId: [this.patientId],
      appointmentId: [this.appointmentId],
      diagnosis: ['', Validators.required],
      prescription: [''],
      notes: [''],
      recordDate: [this.today, Validators.required]
    });
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.medicalRecordForm.valid) {
      const medicalRecordData = {
        ...this.medicalRecordForm.value
      };

      this.httpService.createMedicalRecord(this.patientId, this.doctorId, this.appointmentId, medicalRecordData).subscribe({
        next: (response) => {
          this.successMessage = 'Medical record added successfully.';
          setTimeout(() => {
            this.successMessage = null;
            this.medicalRecordForm.reset();
            this.router.navigate(['/dashboard']);
          }, 2000);
        },
        error: (error: HttpErrorResponse) => {
          this.handleError(error);
        }
      });
    } else {
      this.errorMessage = 'Please fill out all required fields.';
    }
  }

  get f()
  {
    return this.medicalRecordForm.controls;
  }

  private handleError(error: HttpErrorResponse): void 
  {
    this.successMessage = null;
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
