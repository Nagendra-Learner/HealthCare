import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router, NavigationEnd} from '@angular/router';

@Component({
  selector: 'app-dashbaord',
  templateUrl: './dashbaord.component.html',
  styleUrls: ['./dashbaord.component.scss']
})
export class DashbaordComponent 
{
  role!: string | null;
  showDashboardImage = true;
  username = localStorage.getItem('username');

  constructor(private authService: AuthService, private router: Router) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.showDashboardImage = event.url === '/dashboard';
      }
    });
  }

  ngOnInit(): void
  {
    if(!this.authService.getLoginStatus)
    {
      this.router.navigate(['/login']);
    }

    this.role = this.authService.getRole;
    console.log( " Role = " + this.role);
    console.log(this.username);
    
  }

  onLogout()
  {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
