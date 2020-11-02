import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Image } from '../image';
import { ThrowStmt } from '@angular/compiler';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  constructor(private fb: FormBuilder, private httpClient: HttpClient) { }

  selectedFile: File;
  imgURL: any;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string;
  imageName: any;

  productForm: FormGroup;
  imgTypes : {};
  imgSubTypes : {};
  imgdata :Image;

  ngOnInit() {
    this.productForm = this.fb.group({

      name: [''],
      type: [''],
      picByte: [''],
      imgType: [''],
      imgSubType: [''],
      prize: [''],
      desc: ['']

    });

    this.httpClient.get('http://localhost:9090/image/getImgType').subscribe(
      data => this.imgTypes = data
    );
  }

  onChangeProductType(productId: number) {
    if (productId) {
      this.httpClient.get('http://localhost:9090/image/getImgSubType/'+productId).subscribe(
        data => this.imgSubTypes = data
      );
    } else {
      this.imgSubTypes = null;
    }
  }

  public onFileChanged(event) {
    //Select File
    this.selectedFile = event.target.files[0];
    //this.onUpload();
  }

  onSubmit(){    
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);
    let type = this.productForm.value.imgType;
    let subType = this.productForm.value.imgSubType
    let prize = this.productForm.value.prize;
    let desc = this.productForm.value.desc;

    this.httpClient.post('http://localhost:9090/image/create/'+type+'/'+subType+'/'+prize+'/'+desc, uploadImageData,{ observe: 'response' })
      .subscribe((response) => {
        if (response.status === 200) {
          this.message = 'Image uploaded successfully';
        } else {
          this.message = 'Image not uploaded successfully';
        }
      }
      );
  }

}
