import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../services/http.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MedicalRecord } from '../../types/MedicalRecord';

@Component({
  selector: 'app-medicalrecord-edit',
  templateUrl: './medicalrecord-edit.component.html',
  styleUrls: ['./medicalrecord-edit.component.scss']
})
export class MedicalRecordEditComponent
{
  medicalRecordEditForm!: FormGroup;
  successMessage: string | null = null;
  errorMessage: string | null = null;
  medicalRecordId! : number;
  patientId!: number;
  doctorId!: number;
  appointmentId!: number;
  today!: string;
  medicalRecord!: MedicalRecord;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private httpService: HttpService,
    private router: Router
  ) {}

  ngOnInit(): void 
  {
    
    this.medicalRecordId = Number(this.route.snapshot.paramMap.get('medicalRecordId'));
    this.patientId = Number(this.route.snapshot.paramMap.get('patientId'));
    this.doctorId = Number(this.route.snapshot.paramMap.get('doctorId'));
    this.appointmentId = Number(this.route.snapshot.paramMap.get('appointmentId'));
    const now = new Date();
    this.today = now.toISOString().slice(0, 16);
    
    this.medicalRecordEditForm = this.fb.group({
      doctorId: [this.doctorId],
      patientId: [this.patientId],
      appointmentId: [this.appointmentId],
      diagnosis: ['', Validators.required],
      prescription: [''],
      notes: [''],
      recordDate: [this.today, Validators.required]
    });

    this.loadMedicalRecord();
  }

  onSubmit()
  {
    this.successMessage = null;
    this.errorMessage = null;

    if(this.medicalRecordEditForm.valid)
    {
      const updatedMedicalRecord = this.medicalRecordEditForm.value;
      this.httpService.updateMedicalRecord(this.medicalRecordId, updatedMedicalRecord).subscribe({
        next: (medicalRecord) => {
          this.successMessage = "Medical Record updated successfully.";
          setTimeout(() => {this.successMessage = null; this.medicalRecordEditForm.reset(); this.router.navigate([`medicalRecords/view/${this.medicalRecordId}`])}, 2000);
        },
        error: (error) => {
          this.handleError(error);
        }
      })
    }
  }

  get f()
  {
    return this.medicalRecordEditForm.controls;
  }

  loadMedicalRecord()
  {
    this.httpService.fetchMedicalRecordById(this.medicalRecordId).subscribe({
      next: (data) => {
        this.medicalRecord = data;
        console.log(this.medicalRecord);
        this.medicalRecordEditForm.patchValue({
          patientId: this.medicalRecord?.patient.id,
          doctorId: this.medicalRecord?.doctor.id,
          appointmentId: this.medicalRecord?.appointment.id,
          diagnosis: this.medicalRecord.diagnosis,
          prescription: this.medicalRecord.prescription,
          notes: this.medicalRecord.notes,
          recordDate: this.medicalRecord.recordDate
        });
      },
      error: (error) => {
        this.handleError(error);
      }
    });
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
