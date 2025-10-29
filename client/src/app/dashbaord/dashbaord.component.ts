import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Appointment } from '../../types/Appointment';
import { HttpService } from '../../services/http.service';
import { Doctor } from '../../types/Doctor';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashbaord',
  templateUrl: './dashbaord.component.html',
  styleUrls: ['./dashbaord.component.scss']
})
export class DashbaordComponent 
{
  role!: string | null;
  
  constructor(private authService: AuthService, private router: Router)
  {
  }

  ngOnInit(): void
  {
    this.role = this.authService.getRole;
    console.log("Role = " + this.role);
  }

  onLogout()
  {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
