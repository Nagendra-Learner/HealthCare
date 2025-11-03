import { Component, OnInit } from '@angular/core';
import { HttpService } from '../../services/http.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MedicalRecord } from '../../types/MedicalRecord';

@Component({
  selector: 'app-medicalrecord-view',
  templateUrl: './medicalrecord-view.component.html',
  styleUrls: ['./medicalrecord-view.component.scss']
})
export class MedicalRecordViewComponent
{
    medicalRecordId!: number;
    medicalRecord!: MedicalRecord; 
    errorMessage: string | null = null;

    constructor(private httpService: HttpService, private route: ActivatedRoute)
    {}
    
    ngOnInit(): void
    {
      console.log(this.medicalRecordId);
      this.medicalRecordId = Number(this.route.snapshot.paramMap.get('medicalRecordId'));
      this.getMedicalRecords();
    }

    getMedicalRecords()
    {
      this.httpService.fetchMedicalRecordById(this.medicalRecordId).subscribe({
        next: (data) => {
          this.medicalRecord = data;
        },
        error: (error) => {
          this.handleError(error);
        }
      });
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