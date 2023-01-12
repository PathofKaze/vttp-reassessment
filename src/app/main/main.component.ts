import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Order } from '../models';
import { UploadService } from '../upload.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  form!: FormGroup
  
  constructor(private fb: FormBuilder, private router: Router, private uploadSvc: UploadService) {}

  ngOnInit(): void {
    this.form = this.createForm();
  }
  
  private createForm() : FormGroup {
    return this.fb.group({
      name: this.fb.control<string>('', [Validators.required]),
      email: this.fb.control<string>('', [Validators.required, Validators.email]),
      phone: this.fb.control<number>(0),
      title: this.fb.control<string>('', [Validators.required, Validators.min(3), Validators.max(128)]),
      description: this.fb.control<string>('', [Validators.required]),
      image: this.fb.control<string>('')
  })
}


  onSubmitOrder() {
    const order: Order = this.form.value as Order
    this.uploadSvc.createOrder(order)
    .then(result=> {
      this.router.navigate(["/orders", this.form.get('email')?.value])
    })
    .catch(error=>{
      console.error(">>> error: ", error)
    })
  }

}

